package com.example.carware.network.apiResponse.auth

import kotlinx.serialization.Serializable

@Serializable
data class GoogleSignInResponse(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiration: String,
    val refreshTokenExpiration: String,
    val isProfileCompleted: Boolean
)