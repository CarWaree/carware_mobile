package com.example.carware.network.apiResponse.auth

import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordResponse(
    val statusCode: Int,
    val message: String
)
