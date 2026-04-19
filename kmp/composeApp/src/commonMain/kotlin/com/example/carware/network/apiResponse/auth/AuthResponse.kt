package com.example.carware.network.apiResponse.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val data: UserData? = null,
    val statusCode: Int,
    val message: String
)
@Serializable
data class UserData(
    val isAuthenticated: Boolean,
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val roles: List<String>,
    val accessToken: String,
    val refreshToken: String,
    val refreshTokenExpiration: String
)
