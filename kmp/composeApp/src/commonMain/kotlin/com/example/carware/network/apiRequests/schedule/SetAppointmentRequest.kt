package com.example.carware.network.apiRequests.schedule

import kotlinx.serialization.Serializable

@Serializable
data class SetAppointmentRequest(
    val date: String,
    val timeSlot: String,
    val vehicleId: Int,
    val serviceId: Int,
    val serviceCenterId: Int
)
