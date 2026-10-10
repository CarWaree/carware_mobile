package com.example.carware.feature.auth.data.remote.otp
import kotlinx.serialization.Serializable

@Serializable
data class OTPResponse(
    val data: OTP,
    val statusCode: Int,
    val message:String

)

@Serializable
data class OTP(
    val resetPasswordToken : String

)
