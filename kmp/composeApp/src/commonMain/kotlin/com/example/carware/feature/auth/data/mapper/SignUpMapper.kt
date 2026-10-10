package com.example.carware.feature.auth.data.mapper

import com.example.carware.feature.auth.data.remote.signup.SignUpResponse
import com.example.carware.feature.auth.domain.model.SignUpResult

fun SignUpResponse.toDomain(): SignUpResult {
    return SignUpResult(
        firstName = data.firstName,
        lastName = data.lastName,
        username = data.username,
        email = data.email,
        isEmailVerified = data.isEmailVerified
    )
}