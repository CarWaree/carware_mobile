package com.example.carware.viewModel.reminder.reminderScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carware.cache.reminderStore
import com.example.carware.cache.servicesStore
import com.example.carware.cache.vehiclesStore
import com.example.carware.network.apiRequests.reminder.ReminderRequest
import com.example.carware.network.cache.ReminderCacheData
import com.example.carware.repository.ReminderRepository
import com.example.carware.repository.ServiceRepository
import com.example.carware.repository.VehicleRepository
import com.example.carware.util.CalendarLauncher
import com.example.carware.viewModel.defaultSlots
import com.example.carware.viewModel.schedule.screen.TimeSlot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class ReminderScreenViewModel(
    private val reminderRepo: ReminderRepository,
    private val vehicleRepo: VehicleRepository,
    private val serviceRepo: ServiceRepository,
    private val calendarLauncher: CalendarLauncher,

    ) : ViewModel() {


    private val _state = MutableStateFlow(ReminderScreenState())
    val state: StateFlow<ReminderScreenState> = _state.asStateFlow()

    init {
        loadNextReminder()
        loadInitialData()
    }

    private fun loadInitialData() {
        _state.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            // 1. Load from cache first — show UI immediately
            val cachedVehicles = vehiclesStore.get()?.vehicles ?: emptyList()
            val cachedServices = servicesStore.get()?.services ?: emptyList()

            if (cachedVehicles.isNotEmpty() || cachedServices.isNotEmpty()) {
                _state.update {
                    it.copy(
                        availableCars = cachedVehicles,
                        availableServicesTypes = cachedServices,
                        isLoading = false
                    )
                }
            }

            // 2. Try to fetch fresh data from server
            try {
                val services = serviceRepo.getServiceTypeRepo()
                val cars = vehicleRepo.getVehiclesRepo()

                // 3. Update cache
                servicesStore.update { it?.copy(services = services) }
                vehiclesStore.update { it?.copy(vehicles = cars) }

                // 4. Update UI with fresh data
                _state.update {
                    it.copy(
                        availableServicesTypes = services,
                        availableCars = cars,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                // 5. If fetch fails, cache is already shown — just show error if cache was empty
                if (cachedVehicles.isEmpty() && cachedServices.isEmpty()) {
                    _state.update {
                        it.copy(isLoading = false, error = "Failed to load data: ${e.message}")
                    }
                } else {
                    _state.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun selectRepeatInterval(interval: Int) {
        _state.update { it.copy(repeatInterval = interval) }
    }

    fun updateNote(note: String) {
        _state.update { it.copy(note = note) }
    }

    fun selectServiceType(serviceId: Int, serviceName: String) {
        _state.update {
            it.copy(selectedServiceId = serviceId, selectedServiceName = serviceName, error = null)
        }
    }

    fun selectVehicle(carId: Int) {
        val car = _state.value.availableCars.find { it.id == carId }
        if (car != null) {
            _state.update { it.copy(selectedCarId = car.id, error = null) }
        } else {
            _state.update { it.copy(error = "Car not found") }
        }
    }

    val availableSlots: List<TimeSlot> = emptyList()

    // Initialize slots on selectDay
    fun selectDay(day: Int) {
        _state.update {
            it.copy(
                selectedDay = day,
                selectedTime = null,
                availableSlots = defaultSlots().map { it.copy(isAvailable = true) },
                isTimePickerVisible = true,
                error = null
            )
        }
    }

    // Update isSelected on click
    fun selectTimeSlot(time: String) {
        _state.update { current ->
            current.copy(
                selectedTime = time,
                availableSlots = current.availableSlots.map {
                    it.copy(isSelected = it.time == time)
                },
                error = null
            )
        }
    }

    fun confirmTimeSelection() {
        if (_state.value.selectedDay != null && _state.value.selectedTime != null) {
            _state.update { it.copy(isTimePickerVisible = false) }
        } else {
            _state.update { it.copy(error = "Please select both date and time") }
        }
    }

    fun changeMonth(forward: Boolean) {
        _state.update { current ->
            var month = current.currentMonthIndex
            var year = current.currentYear

            if (forward) {
                if (month < 11) month++ else {
                    month = 0; year++
                }
            } else {
                if (month > 0) month-- else {
                    month = 11; year--
                }
            }

            current.copy(
                currentMonthIndex = month,
                currentYear = year,
                selectedDay = null,
                selectedTime = null
            )
        }
    }

    fun changeYear(forward: Boolean) {
        _state.update { current ->
            val newYear = if (forward) current.currentYear + 1 else current.currentYear - 1
            current.copy(currentYear = newYear, selectedDay = null, selectedTime = null)
        }
    }


    private fun isValid(state: ReminderScreenState): Boolean =
        state.selectedServiceId != null &&
                state.selectedCarId != null &&
                state.selectedDay != null &&
                state.selectedTime != null

    @OptIn(ExperimentalTime::class)
    private fun formatToISO8601(year: Int, month: Int, day: Int, time: String): String {
        return try {
            val timeParts = time.split(" ")
            val hourMinute = timeParts[0].split(":")
            var hour = hourMinute[0].toInt()
            val minute = hourMinute[1].toInt()

            if (timeParts[1] == "PM" && hour != 12) hour += 12
            else if (timeParts[1] == "AM" && hour == 12) hour = 0

            val localDateTime = LocalDateTime(year, month, day, hour, minute, 0)
            localDateTime.toInstant(TimeZone.currentSystemDefault()).toString()
        } catch (e: Exception) {
            Clock.System.now().toString()
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun parseISO8601ToMillis(year: Int, month: Int, day: Int, time: String): Long {
        return try {
            val iso = formatToISO8601(year, month, day, time)
            Instant.parse(iso).toEpochMilliseconds()
        } catch (e: Exception) {
            Clock.System.now().toEpochMilliseconds()
        }
    }

    fun setReminder() {
        val current = _state.value

        if (!isValid(current)) {
            _state.update { it.copy(error = "Please complete all required fields") }
            return
        }

        val startMillis = parseISO8601ToMillis(
            current.currentYear,
            current.currentMonthIndex + 1,
            current.selectedDay!!,
            current.selectedTime!!
        )

        val title = "Vehicle: ${
            current.availableCars.find { it.id == current.selectedCarId }
                ?.let { "${it.brandName} ${it.modelName}" } ?: ""
        }: ${current.selectedServiceName ?: "Service"}"

        val description = "\n${current.note}"

        // 1. Always open calendar — no network needed
        calendarLauncher.openCalendarWithEvent(
            title = title,
            description = description,
            startTimeMillis = startMillis
        )

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val request = ReminderRequest(
                    notificationDate = formatToISO8601(
                        current.currentYear,
                        current.currentMonthIndex + 1,
                        current.selectedDay!!,
                        current.selectedTime!!
                    ),
                    repeatInterval = current.repeatInterval,
                    repeatUnit = current.repeatUnit,
                    repeatCount = current.repeatCount,
                    note = current.note,
                    typeId = current.selectedServiceId!!,
                    vehicleId = current.selectedCarId!!
                )
                val current_cache = reminderStore.get()
                val response = reminderRepo.setReminderRepo(request)

                val updatedReminders = (current_cache?.reminders ?: emptyList()) + response.data
                reminderStore.set(
                    ReminderCacheData(
                        reminders = updatedReminders,
                        userId = current_cache?.userId ?: 0
                    )
                )
                _state.update { it.copy(isLoading = false, isBookingSuccess = true) }
            } catch (e: Exception) {
                // Silent fail — calendar was already opened
                _state.update { it.copy(isLoading = false, isBookingSuccess = true) }
            }
        }
    }
    @OptIn(ExperimentalTime::class)
    fun loadNextReminder() {
        viewModelScope.launch {
            val now = Clock.System.now().toEpochMilliseconds()
            val next = reminderStore.get()?.reminders
                ?.mapNotNull { runCatching { Instant.parse(it.notificationDate).toEpochMilliseconds() }.getOrNull() }
                ?.filter { it > now }
                ?.minOrNull()
            _state.update { it.copy(nextReminderMillis = next) }
        }
    }



}