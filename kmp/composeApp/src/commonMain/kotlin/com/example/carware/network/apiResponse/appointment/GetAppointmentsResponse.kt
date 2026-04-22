package com.example.carware.network.apiResponse.appointment

import kotlinx.serialization.Serializable

@Serializable
data class GetAppointmentResponse(
    val data: List<Appointments>,
    val statusCode: Int,
    val message: String
)

@Serializable
data class Appointments(
    val id: Int,
    val date: String,
    val status: String,
    val vehicleName: String,
    val serviceName: String,
    val providerName: String,
)