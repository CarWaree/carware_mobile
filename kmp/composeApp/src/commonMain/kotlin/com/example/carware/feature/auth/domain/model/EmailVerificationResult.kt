package com.example.carware.feature.auth.domain.model

data class EmailVerificationResult (
    val accessToken: String,
    val refreshToken: String
)