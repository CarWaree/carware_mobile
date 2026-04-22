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

    init {
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
                        availableSlots = defaultSlots(), // hardcoded until endpoint exists
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

    // ============ SELECTION ============

    fun selectServiceType(serviceId: Int, serviceName: String) {
        _state.update {
            it.copy(selectedServiceId = serviceId, selectedServiceName = serviceName, error = null)
        }
    }

    fun selectCenter(center: Centers) {
        _state.update {
            it.copy(selectedCenterId = center.id, selectedCenterName = center.name, error = null)
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

    fun selectDay(day: Int) {
        _state.update {
            it.copy(
                selectedDay = day,
                selectedTime = null,
                availableSlots = defaultSlots(), // reset slots per day until real endpoint exists
                isTimePickerVisible = true,
                error = null
            )
        }
    }

    fun selectTimeSlot(time: String) {
        val slot = _state.value.availableSlots.find { it.time == time }

        if (slot == null || !slot.isAvailable) {
            _state.update { it.copy(error = "This time slot is not available") }
            return
        }

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

    fun closeTimePicker() {
        _state.update { it.copy(isTimePickerVisible = false) }
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

    // ============ BOOKING ============
    fun changeYear(forward: Boolean) {
        _state.update { current ->
            val newYear = if (forward) current.currentYear + 1 else current.currentYear - 1
            current.copy(currentYear = newYear, selectedDay = null, selectedTime = null)
        }
    }
    fun confirmAppointment() {
        val current = _state.value

        if (!isValid(current)) {
            _state.update { it.copy(error = "Please complete all required fields") }
            return
        }

        val request = SetAppointmentRequest(
            date = formatToISO8601(current.currentYear, current.currentMonthIndex + 1, current.selectedDay!!, current.selectedTime!!),
            timeSlot = current.selectedTime,
            vehicleId = current.selectedCarId!!,
            serviceId = current.selectedServiceId!!,
            serviceCenterId = current.selectedCenterId!!
        )

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            println("=== REQUEST ===")
            println("date: ${request.date}")
            println("timeSlot: ${request.timeSlot}")
            println("vehicleId: ${request.vehicleId}")
            println("serviceId: ${request.serviceId}")
            println("centerId: ${request.serviceCenterId}")

            try {
                repository.setAppointmentRepo(request)
                _state.update { it.copy(isLoading = false, isBookingSuccess = true) }
            } catch (e: Exception) {
                println("=== BOOKING ERROR: ${e.message}")
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }    }

    fun onBookingSuccessConsumed() {
        _state.update { it.copy(isBookingSuccess = false) }
    }

    // ============ PRIVATE ============

    private fun isValid(state: ScheduleScreenState): Boolean =
        state.selectedServiceId != null &&
                state.selectedCenterId != null &&
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
}