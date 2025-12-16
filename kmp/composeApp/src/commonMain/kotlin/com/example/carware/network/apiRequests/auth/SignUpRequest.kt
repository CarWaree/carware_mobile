package com.example.carware.network.apiRequests.auth

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val firstName: String,
    val lastName: String,
    val userName:String,
    val email:String,
    val password:String,
    val confirmPassword:String,
    )