package com.example.carware.viewModel.schedule

import com.example.carware.network.apiResponse.schedule.Centers
import com.example.carware.network.apiResponse.schedule.Service
import com.example.carware.network.apiResponse.vehicle.Vehicles

data class ScheduleScreenState(
    val selectedService: String? = null,
    val selectedCenter: String? = null,
    val selectedCarId: Int? = null,
    val selectedCarUserId: String? = null,

    val availableCenters: List<Centers> = emptyList(),
    val availableCars: List<Vehicles> = emptyList(),
    val availableServicesTypes: List<Service> = emptyList()
)
