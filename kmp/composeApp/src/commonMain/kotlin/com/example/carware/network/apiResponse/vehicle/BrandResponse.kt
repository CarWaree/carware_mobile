package com.example.carware.network.apiResponse.vehicle

import kotlinx.serialization.Serializable

@Serializable
data class BrandResponse(
    val data: List<Brand>
)

@Serializable
data class Brand (
    val id: Int,
    val name : String
)