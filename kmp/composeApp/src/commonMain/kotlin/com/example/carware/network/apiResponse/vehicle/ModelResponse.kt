package com.example.carware.network.apiResponse.vehicle

import kotlinx.serialization.Serializable

@Serializable
data class ModelResponse(
    val data: List<Model>
)

@Serializable
data class Model (
    val id: Int,
    val name : String
)
