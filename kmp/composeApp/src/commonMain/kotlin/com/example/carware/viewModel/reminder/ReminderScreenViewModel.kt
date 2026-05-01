package com.example.carware.viewModel.reminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carware.network.apiRequests.reminder.ReminderRequest
import com.example.carware.repository.ReminderRepository
import com.example.carware.repository.ServiceRepository
import com.example.carware.repository.VehicleRepository
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

class ReminderScreenViewModel(
    private val reminderRepo: ReminderRepository,
    private val vehicleRepo: VehicleRepository,
    private val serviceRepo: ServiceRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ReminderScreenState())
    val state: StateFlow<ReminderScreenState> = _state.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        _state.update { it.copy(isLoading = true, error = null) }


        viewModelScope.launch {
            try {
                val services = serviceRepo.getServiceTypeRepo()
                val cars = vehicleRepo.getVehiclesRepo()

                _state.update {
                    it.copy(
                        availableServicesTypes = services,
                        availableCars = cars,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(isLoading = false, error = "Failed to load data: ${e.message}")
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
                if (month < 11) month++ else { month = 0; year++ }
            } else {
                if (month > 0) month-- else { month = 11; year-- }
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

    private fun defaultSlots() = listOf(
        TimeSlot("10:00 AM", isAvailable = false),
        TimeSlot("10:30 AM"),
        TimeSlot("11:00 AM"),
        TimeSlot("11:30 AM", isAvailable = false),
        TimeSlot("12:00 PM"),
        TimeSlot("12:30 PM"),
        TimeSlot("1:30 PM"),
        TimeSlot("2:00 PM", isAvailable = false),
        TimeSlot("3:00 PM"),
        TimeSlot("3:30 PM"),
        TimeSlot("4:00 PM"),
        TimeSlot("4:30 PM"),
        TimeSlot("5:00 PM", isAvailable = false),
        TimeSlot("5:30 PM"),
        TimeSlot("6:00 PM", isAvailable = false),
        TimeSlot("6:30 PM"),
        TimeSlot("7:00 PM"),
        TimeSlot("7:30 PM"),
        TimeSlot("8:00 PM"),
        TimeSlot("8:30 PM", isAvailable = false),
        TimeSlot("9:00 PM"),
        TimeSlot("9:30 PM"),
        TimeSlot("10:00 PM", isAvailable = false),
        TimeSlot("10:30 PM"),
        TimeSlot("11:00 PM"),
        TimeSlot("11:30 PM", isAvailable = false),
        TimeSlot("12:00 AM"),
    )

    fun setReminder() {
        val current = _state.value

        if (!isValid(current)) {
            _state.update { it.copy(error = "Please complete all required fields") }
            return
        }

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

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                reminderRepo.setReminderRepo(request)
                _state.update { it.copy(isLoading = false, isBookingSuccess = true) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

}