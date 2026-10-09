package com.example.carware.feature.auth.data.remote

import kotlinx.serialization.Serializable

@Serializable
data   class GoogleSignInRequest (
    val idToken: String
)