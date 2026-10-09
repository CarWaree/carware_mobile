package com.example.carware.feature.auth.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class GoogleSignInResponse(
    val data: GoogleSignInData,
    val statusCode: Int,
    val message: String
)

@Serializable
data class GoogleSignInData(

    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiration: String,
    val refreshTokenExpiration: String,
    val isProfileCompleted: Boolean

)