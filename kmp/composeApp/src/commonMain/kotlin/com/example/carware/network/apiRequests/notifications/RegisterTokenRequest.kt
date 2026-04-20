package com.example.carware.network.apiRequests.notifications

import kotlinx.serialization.Serializable

@Serializable
data class RegisterTokenRequest(

    val token: String,
    val platform: Int
)

