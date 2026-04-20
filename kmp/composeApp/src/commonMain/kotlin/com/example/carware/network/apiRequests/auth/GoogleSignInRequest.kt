package com.example.carware.network.apiRequests.auth

import kotlinx.serialization.Serializable

@Serializable
data   class GoogleSignInRequest (
    val idToken: String
)
