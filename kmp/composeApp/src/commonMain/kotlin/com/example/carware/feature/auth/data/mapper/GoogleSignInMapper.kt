package com.example.carware.feature.auth.data.mapper

import com.example.carware.feature.auth.data.remote.googleSignIn.GoogleSignInResponse
import com.example.carware.feature.auth.domain.model.GoogleSignInResult

fun GoogleSignInResponse.toDomain(): GoogleSignInResult {
    return GoogleSignInResult(
        accessToken = data.accessToken,
        refreshToken = data.refreshToken,
        accessTokenExpiration = data.accessTokenExpiration,
        refreshTokenExpiration = data.refreshTokenExpiration,
        isProfileCompleted = data.isProfileCompleted
    )
}