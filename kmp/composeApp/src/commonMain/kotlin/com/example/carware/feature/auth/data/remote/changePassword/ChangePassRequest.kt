package com.example.carware.feature.auth.data.remote.changePassword

import kotlinx.serialization.Serializable

@Serializable
data class ChangePassRequest(
    val oldPassword: String,
    val newPassword: String,
    val confirmPassword: String
)
