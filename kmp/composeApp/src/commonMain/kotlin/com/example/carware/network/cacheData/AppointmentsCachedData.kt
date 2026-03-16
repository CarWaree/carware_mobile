package com.example.carware.network.cacheData

import com.example.carware.network.apiResponse.appointment.Appointments
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Serializable
data class AppointmentsCachedData @OptIn(ExperimentalTime::class) constructor(
    val appointments: List<Appointments> =emptyList(),
    val lastUpdated: Long = Clock.System.now().toEpochMilliseconds(),
    val userId: Int = 0
)