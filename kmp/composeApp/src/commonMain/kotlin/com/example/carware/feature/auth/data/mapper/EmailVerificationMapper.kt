package com.example.carware.feature.auth.data.mapper

import com.example.carware.feature.auth.data.remote.emailVerification.EmailVerificationResponse
import com.example.carware.feature.auth.domain.model.EmailVerificationResult

fun EmailVerificationResponse.toDomain() = EmailVerificationResult(
    accessToken = data.accessToken,
    refreshToken = data.refreshToken
)