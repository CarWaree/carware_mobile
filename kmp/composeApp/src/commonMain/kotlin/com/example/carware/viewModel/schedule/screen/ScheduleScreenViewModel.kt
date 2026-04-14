package com.example.carware.viewModel.schedule.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carware.network.apiRequests.schedule.SetAppointmentRequest
import com.example.carware.network.apiResponse.schedule.Centers
import com.example.carware.repository.ServiceRepository
import com.example.carware.repository.VehicleRepository
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

class ScheduleScreenViewModel(
    private val repository: ServiceRepository,
    private val vehicleRepository: VehicleRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ScheduleScreenState())
    val state: StateFlow<ScheduleScreenState> = _state.asStateFlow()

    // --- Time Slot State ---
    val morningSlots = mutableStateListOf<TimeSlot>()
    val eveningSlots = mutableStateListOf<TimeSlot>()

    val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    init {
        _state.update { it.copy(isInitialLoading = true) }
        loadInitialTimeSlots()
        loadInitialData()

    }

    // ============ DATA LOADING ============

     fun loadInitialData() {
        _state.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            try {
                val services = repository.getServiceTypeRepo()
                val centers = repository.getServiceCentersRepo()
                val cars = vehicleRepository.getVehiclesRepo()

                _state.update {
                    it.copy(
                        availableServicesTypes = services,
                        availableCenters = centers,
                        availableCars = cars,
                        isLoading = false,
                        isInitialLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isInitialLoading = false,
                        error = "Failed to load data: ${e.message}"
                    )
                }
            }
        }
    }

    fun loadInitialTimeSlots() {
        _state.update { it.copy(isLoading = true, error = null) }

        morningSlots.clear()
        morningSlots.addAll(
            listOf(
                TimeSlot("10:00 AM", isAvailable = false  ,),
                TimeSlot("10:30 AM"),
                TimeSlot("11:00 AM"),
                TimeSlot("11:30 AM", isAvailable = false ),
                TimeSlot("12:00 PM"),
                TimeSlot("12:30 PM"),
                TimeSlot("1:30 PM"),
                TimeSlot("2:00 PM", isAvailable = false ),
                TimeSlot("3:00 PM"),
                TimeSlot("3:30 PM"),
                TimeSlot("4:00 PM"),
                TimeSlot("4:30 PM"),
                TimeSlot("5:00 PM", isAvailable = false ),
                TimeSlot("5:30 PM"),
            )
        )

        eveningSlots.clear()
        eveningSlots.addAll(
            listOf(
                TimeSlot("6:00 PM", isAvailable = false ),
                TimeSlot("6:30 PM"),
                TimeSlot("7:00 PM"),
                TimeSlot("7:30 PM"),
                TimeSlot("8:00 PM"),
                TimeSlot("8:30 PM", isAvailable = false ),
                TimeSlot("9:00 PM"),
                TimeSlot("9:30 PM"),
                TimeSlot("10:00 PM", isAvailable = false ),
                TimeSlot("10:30 PM"),
                TimeSlot("11:00 PM"),
                TimeSlot("11:30 PM", isAvailable = false ),
                TimeSlot("12:00 AM"),
            )
        )
    }

    // ============ SERVICE & CENTER SELECTION ============

    fun selectServiceType(serviceName: String, serviceId: Int) {
        _state.update {
            it.copy(
                selectedService = serviceName,
                selectedServiceId = serviceId,
                error = null
            )
        }
    }

    fun selectCenter(center: Centers) {
        _state.update {
            it.copy(
                selectedCenter = center.name,
                selectedCenterId = center.id,   // ============ Store Int ID (if Centers has id field) ============
                error = null
            )
        }
    }

    // ============ VEHICLE SELECTION ============

    fun selectVehicle(carId: Int) {
        val currentCars = _state.value.availableCars
        val car = currentCars.find { it.id == carId }

        if (car != null) {
            _state.update { currentState ->
                currentState.copy(
                    selectedCarId = car.id,
                    selectedCarUserId = car.userName,
                    error = null
                )
            }
        } else {
            _state.update {
                it.copy(error = "Could not find car with ID $carId")
            }
        }
    }

    // ============ DATE & TIME SELECTION ============

    fun onDayClick(day: String) {
        _state.update {
            it.copy(
                selectedDay = day,
                isTimePickerVisible = true,
                error = null
            )
        }
    }

    fun onTimeClick(time: String) {
        // Check if time is available
        val isAvailable = morningSlots.find { it.time == time }?.isAvailable == true ||
                eveningSlots.find { it.time == time }?.isAvailable == true

        if (isAvailable) {
            updateSelection(morningSlots, time)
            updateSelection(eveningSlots, time)

            _state.update {
                it.copy(
                    selectedTime = time,
                    error = null
                )
            }
        } else {
            _state.update {
                it.copy(error = "Selected time slot is not available")
            }
        }
    }

    private fun updateSelection(list: MutableList<TimeSlot>, selectedTime: String) {
        for (i in list.indices) {
            list[i] = list[i].copy(isSelected = list[i].time == selectedTime)
        }
    }

    fun changeMonth(forward: Boolean) {
        _state.update { currentState ->
            var newMonth = currentState.currentMonthIndex
            var newYear = currentState.currentYear

            if (forward) {
                if (newMonth < 11) newMonth++ else {
                    newMonth = 0; newYear++
                }
            } else {
                if (newMonth > 0) newMonth-- else {
                    newMonth = 11; newYear--
                }
            }

            currentState.copy(
                currentMonthIndex = newMonth,
                currentYear = newYear,
                selectedDay = null,
                selectedTime = null
            )
        }
    }
    fun changeYear(forward: Boolean) {
        _state.update { currentState ->
            val newYear =
                if (forward) currentState.currentYear + 1
                else currentState.currentYear - 1

            currentState.copy(
                currentYear = newYear,
                selectedDay = null,
                selectedTime = null
            )
        }
    }
    fun getSelectedTime(): String? {
        return _state.value.selectedTime
    }

    fun getFinalSelectionString(): String? {
        val state = _state.value
        return if (state.selectedDay != null && state.selectedTime != null) {
            "${months[state.currentMonthIndex]} ${state.selectedDay}, ${state.currentYear} at ${state.selectedTime}"
        } else {
            null
        }
    }

    fun confirmTimeSelection() {
        val state = _state.value
        if (state.selectedDay != null && state.selectedTime != null) {
            _state.update {
                it.copy(isTimePickerVisible = false)
            }
        } else {
            _state.update {
                it.copy(error = "Please select both date and time")
            }
        }
    }

    fun closeTimePicker() {
        _state.update {
            it.copy(isTimePickerVisible = false)
        }
    }

    // ============ APPOINTMENT CONFIRMATION ============

    fun confirmAppointment() {

        val currentState = _state.value



        if (!isAppointmentValid()) {
            _state.update {
                it.copy(error = "Please complete all required fields")
            }
            return
        }

        val isoDateTime = formatDateTimeToISO8601(

            day = currentState.selectedDay.orEmpty(),
            time = currentState.selectedTime.orEmpty()
        )

        val request = SetAppointmentRequest(
            date = isoDateTime,
            timeSlot = currentState.selectedTime.orEmpty(),
            vehicleId = currentState.selectedCarId ,           // Already Int
            serviceId = currentState.selectedServiceId ,       // Use Int directly
            serviceCenterId = currentState.selectedCenterId   // Use Int directly
        )

        println("Request: vehicleId=${request.vehicleId}, serviceId=${request.serviceId}, centerId=${request.serviceCenterId}")

        _state.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            try {
                val response = repository.setAppointmentRepo(request)

                if (response.statusCode == 200 || response.statusCode == 201) {
                    println("Appointment confirmed: ${response.message}")
                    _state.update { it.copy(isLoading = false) }
                } else {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = response.message
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed: ${e.message}"
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun formatDateTimeToISO8601(day: String, time: String): String {
        return try {
            val state = _state.value
            val year = state.currentYear
            val month = state.currentMonthIndex + 1 // Month is 0-indexed

            // Parse the time (e.g., "10:30 AM")
            val timeParts = time.split(" ")
            val hourMinute = timeParts[0].split(":")
            val hour = hourMinute[0].toInt().let {
                if (timeParts[1] == "PM" && it != 12) it + 12
                else if (timeParts[1] == "AM" && it == 12) 0
                else it
            }
            val minute = hourMinute[1].toInt()

            // Create LocalDateTime using kotlinx.datetime
            val localDateTime = LocalDateTime(
                year = year,
                monthNumber = month,
                dayOfMonth = day.toInt(),
                hour = hour,
                minute = minute,
                second = 0
            )

            // Convert to ISO 8601 instant string
            localDateTime.toInstant(TimeZone.currentSystemDefault()).toString()
        } catch (e: Exception) {
            // Fallback if parsing fails
            println("Date formatting error: ${e.message}")
            Clock.System.now().toString()
        }
    }
//

    fun isAppointmentValid(): Boolean {
        val state = _state.value
        return state.selectedService != null &&
                state.selectedCenter != null &&
                state.selectedCarId != null &&
                state.selectedDay != null &&
                state.selectedTime != null
    }


}
