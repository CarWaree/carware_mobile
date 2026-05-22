package com.example.carware.network.apiResponse.appointment

import kotlinx.serialization.Serializable

@Serializable
data class AppointmentResponse(
    val data: Appointment,
    val statusCode: Int,
    val message: String
)

@Serializable
data class Appointment(
    val id: Int,
    val date: String,
    val status: String,
    val vehicleName: String,
    val serviceName: String,
    val providerName: String,
)