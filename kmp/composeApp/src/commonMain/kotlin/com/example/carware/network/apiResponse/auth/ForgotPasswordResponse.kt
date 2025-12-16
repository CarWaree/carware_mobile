package com.example.carware.network.apiResponse.auth

import kotlinx.serialization.Serializable

@Serializable
data class ForgotPasswordResponse(
    val statusCode: Int,
    val message: String
)
