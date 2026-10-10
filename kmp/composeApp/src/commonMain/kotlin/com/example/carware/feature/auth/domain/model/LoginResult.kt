package com.example.carware.feature.auth.domain.model

data class LoginResult(
    val accessToken: String,
    val refreshToken: String,
    val refreshTokenExpiration: String,
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val roles: List<String>
)