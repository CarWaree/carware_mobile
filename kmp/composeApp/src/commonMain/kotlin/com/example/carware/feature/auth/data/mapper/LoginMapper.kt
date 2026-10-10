package com.example.carware.feature.auth.data.mapper

import com.example.carware.feature.auth.data.remote.login.AuthResponse
import com.example.carware.feature.auth.domain.model.LoginResult

fun AuthResponse.toLoginResult(): LoginResult? =
    data?.let {
        LoginResult(
            accessToken = it.accessToken,
            refreshToken = it.refreshToken,
            refreshTokenExpiration = it.refreshTokenExpiration,
            firstName = it.firstName,
            lastName = it.lastName,
            username = it.username,
            email = it.email,
            roles = it.roles
        )
    }