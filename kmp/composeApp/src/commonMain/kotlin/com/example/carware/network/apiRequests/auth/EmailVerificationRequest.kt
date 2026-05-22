package com.example.carware.network.apiRequests.auth

import kotlinx.serialization.Serializable


@Serializable
data class EmailVerificationRequest(
    val email: String,
    val otp: String

)
