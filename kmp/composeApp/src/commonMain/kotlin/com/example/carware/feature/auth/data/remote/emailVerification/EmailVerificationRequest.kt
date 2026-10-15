package com.example.carware.feature.auth.data.remote.emailVerification

import kotlinx.serialization.Serializable


@Serializable
data class EmailVerificationRequest(
    val email: String,
    val otp: String

)
