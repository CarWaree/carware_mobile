package com.example.carware.network.cache

import com.example.carware.network.apiResponse.vehicle.Vehicles
import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Serializable
data class VehiclesCacheData (
    val vehicles: List<Vehicles> = emptyList(),
    val lastUpdated: String=" ",
    val userId: Int = 0
)