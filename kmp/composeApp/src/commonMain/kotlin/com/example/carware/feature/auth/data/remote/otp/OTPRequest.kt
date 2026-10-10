package com.example.carware.feature.auth.data.remote.otp

import kotlinx.serialization.Serializable

@Serializable
data class OTPRequest(
    val email: String,
    val otp : String
)