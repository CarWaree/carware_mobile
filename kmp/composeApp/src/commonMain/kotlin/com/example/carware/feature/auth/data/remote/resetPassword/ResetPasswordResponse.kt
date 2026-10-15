package com.example.carware.feature.auth.data.remote.resetPassword

import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordResponse(
    val statusCode: Int,
    val message: String
)
