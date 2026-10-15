package com.example.carware.feature.auth.data.remote.emailVerification

import kotlinx.serialization.Serializable


@Serializable
data class EmailVerificationResponse(
val data:VerificationData,
val statusCode: Int,
val message: String
)
@Serializable
data class VerificationData(
    val accessToken: String,
    val refreshToken: String
)