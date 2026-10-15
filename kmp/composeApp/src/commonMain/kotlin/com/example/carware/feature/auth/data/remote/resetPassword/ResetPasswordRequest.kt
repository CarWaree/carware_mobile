package com.example.carware.feature.auth.data.remote.resetPassword

import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordRequest(
    val resetPasswordToken: String?,
    val newPassword: String,
    val confirmPassword: String
)
