package com.example.carware.viewModel.reminder

import com.example.carware.network.apiResponse.reminder.ReminderResponse
import com.example.carware.network.apiResponse.schedule.Service
import com.example.carware.network.apiResponse.vehicle.Vehicles
import com.example.carware.viewModel.schedule.screen.TimeSlot
import kotlin.time.ExperimentalTime

data class ReminderScreenState @OptIn(ExperimentalTime::class) constructor(
    // 1. Form Selections (User input)
    val selectedVehicleId: Int? = null,
    val selectedServiceId: Int? = null, // typeId from your ReminderRequest
    val selectedServiceName: String? = null,
    val notificationDate: String? = null,
    val selectedCarId: Int? = null,


    val repeatInterval: Int = 1,
    val repeatUnit: String = "MONTH",
    val repeatCount: Int = 1,
    val note: String = "",

    // 2. Data from API (The "Success" data)
    val availableCars: List<Vehicles> = emptyList(),
    val availableServicesTypes: List<Service> = emptyList(),
    val lastCreatedReminder: ReminderResponse? = null, // Success object from API

    // 3. UI/Interaction State
    val isTimePickerVisible: Boolean = false,
    val currentMonthIndex: Int = 3,
    val currentYear: Int = 2026,
    val isLoading: Boolean = false,
    val isBookingSuccess: Boolean = false,
    val error: String? = null,

    // Date & time
    val selectedDay: Int? = null,
    val selectedTime: String? = null,
    val availableSlots: List<TimeSlot> = emptyList()

    )