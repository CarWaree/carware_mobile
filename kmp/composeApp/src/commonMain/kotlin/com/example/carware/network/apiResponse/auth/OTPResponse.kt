package com.example.carware.network.apiResponse.auth

import kotlinx.serialization.Serializable

@Serializable
data class OTPResponse(
    val data: OTP,
    val statusCode: Int,
    val message:String

)

@Serializable
data class OTP(
    val token : String
)
