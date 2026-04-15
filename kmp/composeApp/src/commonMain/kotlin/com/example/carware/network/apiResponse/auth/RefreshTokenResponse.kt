package com.example.carware.network.apiResponse.auth

import kotlinx.serialization.Serializable


@kotlinx.serialization.Serializable
data class RefreshTokenResponse(
    val data: RefreshTokenData,
    val statusCode: Int,
    val message: String
)

@Serializable
data class RefreshTokenData(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiration: String,
    val refreshTokenExpiration: String,
    val isProfileCompleted: Boolean
)