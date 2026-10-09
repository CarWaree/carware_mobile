package com.example.carware.feature.auth.domain.model

data class SignUpResult(
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val isEmailVerified: Boolean
)