package com.example.carware.network.apiResponse.auth

import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponse(
    val data: SignUpUserData,
    val statusCode: Int,
    val message: String
)

@Serializable
data class SignUpUserData(
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val isEmailVerified: Boolean
)



