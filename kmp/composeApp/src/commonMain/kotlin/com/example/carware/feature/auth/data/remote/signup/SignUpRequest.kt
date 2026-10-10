package com.example.carware.feature.auth.data.remote.signup

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