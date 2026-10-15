package com.example.carware.feature.auth.data.mapper

import com.example.carware.feature.auth.data.remote.resetPassword.ResetPasswordResponse
import com.example.carware.feature.auth.domain.model.ResetPasswordResult


fun ResetPasswordResponse.toDomain() = ResetPasswordResult(
    message = message
)