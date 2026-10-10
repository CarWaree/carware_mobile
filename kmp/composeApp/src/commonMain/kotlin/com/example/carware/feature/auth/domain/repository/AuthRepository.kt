package com.example.carware.feature.auth.domain.repository

import com.example.carware.core.network.ApiResult
import com.example.carware.feature.auth.domain.model.ForgotPasswordResult
import com.example.carware.feature.auth.domain.model.GoogleSignInResult
import com.example.carware.feature.auth.domain.model.LoginResult
import com.example.carware.feature.auth.domain.model.OTPResult
import com.example.carware.feature.auth.domain.model.SignUpResult

interface AuthRepository {
    suspend fun signUp(
        firstName: String,
        lastName: String,
        userName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): ApiResult<SignUpResult>

    suspend fun googleSignIn(idToken: String): ApiResult<GoogleSignInResult>
    suspend fun login(emailOrUsername: String, password: String): ApiResult<LoginResult>

    suspend fun forgotPassword(email: String): ApiResult<ForgotPasswordResult>

    suspend fun otpVerification(email: String, otp: String): ApiResult<OTPResult>}