package com.example.carware.feature.auth.domain.model

data class GoogleSignInResult(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiration: String,
    val refreshTokenExpiration: String,
    val isProfileCompleted: Boolean
)