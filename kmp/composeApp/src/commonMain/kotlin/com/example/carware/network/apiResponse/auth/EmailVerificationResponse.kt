package com.example.carware.network.apiResponse.auth

import kotlinx.serialization.Serializable


@Serializable
data class EmailVerificationResponse(
val data:VerificationData,
val statusCode: Int,
val message: String
)
@Serializable
data class VerificationData(
    val token: String,
    val expiresOn: String
)