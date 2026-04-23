package com.example.carware.network.apiResponse.vehicle

import kotlinx.serialization.Serializable

@Serializable
data class DeleteVehicleResponse (
    val statusCode: Int,
    val message:String
)