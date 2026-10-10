package com.example.carware.feature.auth.data.mapper

import com.example.carware.feature.auth.data.remote.forgotPassword.ForgotPasswordResponse
import com.example.carware.feature.auth.domain.model.ForgotPasswordResult

fun ForgotPasswordResponse.toDomain(): ForgotPasswordResult =
    ForgotPasswordResult(message = message)