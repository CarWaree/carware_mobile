package com.example.carware.viewModel.schedule.screen

import com.example.carware.network.apiResponse.schedule.Centers
import com.example.carware.network.apiResponse.schedule.Service
import com.example.carware.network.apiResponse.vehicle.Vehicles

// Separate data class for time slots
data class TimeSlot(
    val time: String,
    val isAvailable: Boolean = true,
    val isSelected: Boolean = false,
)

data class ScheduleScreenState(
// --- Service Selection ---
    val selectedService: String?=" ",
    val selectedServiceId: Int? = null,

    val selectedCenter: String? = " ",
    val selectedCenterId: Int? = null,

    val selectedCarId: Int? = null,
    val selectedCarUserId: String? = null,

    // --- Available Data ---
    val availableCenters: List<Centers> = emptyList(),
    val availableCars: List<Vehicles> = emptyList(),
    val availableServicesTypes: List<Service> = emptyList(),

    // --- Calendar & Time Selection ---
    val selectedDay: String? = null,
    val selectedTime: String? = null,
    val currentMonthIndex: Int = 1,
    var currentYear: Int = 2026,
    var isTimePickerVisible: Boolean = false,

    // --- Loading & Error States ---
    val isInitialLoading: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
)
