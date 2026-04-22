package com.example.carware.viewModel.schedule.screen

import com.example.carware.network.apiResponse.schedule.Centers
import com.example.carware.network.apiResponse.schedule.Service
import com.example.carware.network.apiResponse.vehicle.Vehicles
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

// Separate data class for time slots
data class TimeSlot(
    val time: String,
    val isAvailable: Boolean = true,
    val isSelected: Boolean = false,
)
data class ScheduleScreenState @OptIn(ExperimentalTime::class) constructor(
    // Selection
    val selectedServiceId: Int? = null,
    val selectedServiceName: String? = null,
    val selectedCenterId: Int? = null,
    val selectedCenterName: String? = null,
    val selectedCarId: Int? = null,

    // Available data
    val availableCenters: List<Centers> = emptyList(),
    val availableCars: List<Vehicles> = emptyList(),
    val availableServicesTypes: List<Service> = emptyList(),
    val availableSlots: List<TimeSlot> = emptyList(),

    // Date & time
    val selectedDay: Int? = null,
    val selectedTime: String? = null,
    val currentMonthIndex: Int = 3, // April (0-indexed), hardcode until Clock API is resolved
    val currentYear: Int = 2026,
    // UI
    val isTimePickerVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isBookingSuccess: Boolean = false,
    val error: String? = null
)