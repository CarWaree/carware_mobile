package com.example.carware.network.apiResponse.vehicle

import kotlinx.serialization.Serializable

@Serializable
data class GetVehicleResponse(
    val data: List<Vehicles>,
    val statusCode: Int?=null,
    val message: String?=null
)

@Serializable
data class Vehicles(
    val id: Int,
    val brandName: String,
    val modelName: String,
    val year: Int,
    val color: String,
    val userName: String
)