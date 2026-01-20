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
    val selectedService: String?=null,
    val selectedServiceId: Int? = null,      // ============ NEW: Add Int ID ============

    val selectedCenter: String? = null,
    val selectedCenterId: Int? = null,       // ============ NEW: Add Int ID ============

    val selectedCarId: Int? = null,          // Already Int - good!
    val selectedCarUserId: String? = null,

    // --- Available Data ---
    val availableCenters: List<Centers> = emptyList(),
    val availableCars: List<Vehicles> = emptyList(),
    val availableServicesTypes: List<Service> = emptyList(),

    // --- Calendar & Time Selection ---
    val selectedDay: String? = null,
    val selectedTime: String? = null,
    val currentMonthIndex: Int = 10,
    var currentYear: Int = 2025,
    var isTimePickerVisible: Boolean = false,

    // --- Loading & Error States ---
    val isLoading: Boolean = false,
    val error: String? = null,
)
