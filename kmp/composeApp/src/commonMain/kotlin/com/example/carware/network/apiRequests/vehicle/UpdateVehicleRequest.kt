package com.example.carware.network.apiRequests.vehicle

import kotlinx.serialization.Serializable

@Serializable

data class UpdateVehicleRequest(
    val id: Int,
    val brandName: String,
    val modelName: String,
    val year: Int,
    val color: String
)
