package com.example.carware.network.apiRequests.auth

import kotlinx.serialization.Serializable

@Serializable
data class ChangePassRequest(
    val oldPassword: String,
    val newPassword: String,
    val confirmPassword: String
)
