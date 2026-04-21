package com.example.carware.network.apiRequests.auth

import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordRequest(
    val accessToken: String?,
    val newPassword: String,
    val confirmPassword: String
)
