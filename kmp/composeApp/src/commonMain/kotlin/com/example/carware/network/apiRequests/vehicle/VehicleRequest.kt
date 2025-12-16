package com.example.carware.network.apiRequests.vehicle

import kotlinx.serialization.Serializable


@Serializable
data class VehicleRequest (
    val brandId: Int,
    val modelId: Int,
    val year: Int,
    val color: String,
)