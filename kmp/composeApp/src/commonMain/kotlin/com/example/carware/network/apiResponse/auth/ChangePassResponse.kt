package com.example.carware.network.apiResponse.auth

import kotlinx.serialization.Serializable

@Serializable
data class ChangePassResponse(
    val statusCode: Int,
    val message: String
)
