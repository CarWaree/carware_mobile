package com.example.carware.network.apiRequests.auth

import kotlinx.serialization.Serializable

@Serializable
data class OTPRequest(
    val email: String,
    val otp : String
)
