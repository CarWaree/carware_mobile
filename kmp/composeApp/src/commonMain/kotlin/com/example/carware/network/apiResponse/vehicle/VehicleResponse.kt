package com.example.carware.network.apiResponse.vehicle

import kotlinx.serialization.Serializable

@Serializable
data class VehicleResponse(

    val data: Vehicle,
    val statusCode: Int,
    val message: String
)

@Serializable
data class Vehicle(
    val id: Int,
    val brandName: String,
    val modelName: String,
    val year: Int,
    val color: String,
    val userName: String
)