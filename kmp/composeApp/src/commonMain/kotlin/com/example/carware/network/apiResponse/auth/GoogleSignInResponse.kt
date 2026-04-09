package com.example.carware.network.apiResponse.auth

import kotlinx.serialization.Serializable

@Serializable
data class GoogleSignInResponse(
    val message: String,
    val token: String
)