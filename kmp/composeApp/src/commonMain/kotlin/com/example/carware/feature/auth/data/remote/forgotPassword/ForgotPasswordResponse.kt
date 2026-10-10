package com.example.carware.feature.auth.data.remote.forgotPassword

import kotlinx.serialization.Serializable

@Serializable
data class ForgotPasswordResponse(
    val statusCode: Int,
    val message: String
)