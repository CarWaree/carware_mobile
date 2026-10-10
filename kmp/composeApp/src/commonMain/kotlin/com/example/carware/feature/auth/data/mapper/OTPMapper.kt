package com.example.carware.feature.auth.data.mapper

import com.example.carware.feature.auth.data.remote.otp.OTPResponse
import com.example.carware.feature.auth.domain.model.OTPResult

fun OTPResponse.toDomain() = OTPResult(
    resetPasswordToken = data.resetPasswordToken
)