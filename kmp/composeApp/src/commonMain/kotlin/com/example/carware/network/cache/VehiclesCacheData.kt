package com.example.carware.network.cache

import com.example.carware.network.apiResponse.vehicle.Vehicles
import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Serializable
data class VehiclesCacheData @OptIn(ExperimentalTime::class) constructor(
    val vehicles: List<Vehicles> = emptyList(),
    val lastUpdated: Long = Clock.System.now().toEpochMilliseconds(),
    val userId: Int = 0
)