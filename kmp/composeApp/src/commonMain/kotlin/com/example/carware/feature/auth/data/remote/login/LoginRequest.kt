package com.example.carware.feature.auth.data.remote.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val emailOrUsername: String,
    val password: String
)