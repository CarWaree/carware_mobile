package com.example.carware.feature.auth.data.repository

import com.example.carware.core.network.ApiResult
import com.example.carware.core.network.ApiResult.*
import com.example.carware.feature.auth.data.mapper.toDomain
import com.example.carware.feature.auth.data.mapper.toLoginResult
import com.example.carware.feature.auth.data.remote.AuthApi
import com.example.carware.feature.auth.data.remote.changePassword.ChangePassRequest
import com.example.carware.feature.auth.data.remote.emailVerification.EmailVerificationRequest
import com.example.carware.feature.auth.data.remote.forgotPassword.ForgotPasswordRequest
import com.example.carware.feature.auth.data.remote.googleSignIn.GoogleSignInRequest
import com.example.carware.feature.auth.data.remote.login.LoginRequest
import com.example.carware.feature.auth.data.remote.otp.OTPRequest
import com.example.carware.feature.auth.data.remote.resetPassword.ResetPasswordRequest
import com.example.carware.feature.auth.data.remote.signup.SignUpRequest
import com.example.carware.feature.auth.domain.model.ChangePassResult
import com.example.carware.feature.auth.domain.model.EmailVerificationResult
import com.example.carware.feature.auth.domain.model.ForgotPasswordResult
import com.example.carware.feature.auth.domain.model.GoogleSignInResult
import com.example.carware.feature.auth.domain.model.LoginResult
import com.example.carware.feature.auth.domain.model.OTPResult
import com.example.carware.feature.auth.domain.model.ResetPasswordResult
import com.example.carware.feature.auth.domain.model.SignUpResult
import com.example.carware.feature.auth.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val api: AuthApi
) : AuthRepository {

    override suspend fun signUp(
        firstName: String,
        lastName: String,
        userName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): ApiResult<SignUpResult> {
        val request = SignUpRequest(
            firstName = firstName,
            lastName = lastName,
            userName = userName,
            email = email,
            password = password,
            confirmPassword = confirmPassword
        )
        return when (val result = api.signUp(request)) {
            is Success -> Success(result.data.toDomain())
            is Error -> Error(result.code, result.message, result.body)
            is Exception -> Exception(result.throwable)
        }
    }

    override suspend fun googleSignIn(idToken: String): ApiResult<GoogleSignInResult> {
        val request = GoogleSignInRequest(idToken)
        return when (val result = api.googleSignIn(request)) {
            is Success -> Success(result.data.toDomain())
            is Error -> Error(result.code, result.message, result.body)
            is Exception -> Exception(result.throwable)
        }
    }

    override suspend fun login(
        emailOrUsername: String,
        password: String
    ): ApiResult<LoginResult> {
        return when (val result = api.loginUser(LoginRequest(emailOrUsername, password))) {
            is Success -> {
                val mapped = result.data.toLoginResult()
                if (mapped != null) Success(mapped)
                else Error(0, "Empty response data", null)
            }

            is Error -> Error(result.code, result.message, result.body)
            is Exception -> Exception(result.throwable)
        }
    }

    override suspend fun forgotPassword(email: String): ApiResult<ForgotPasswordResult> {
        return when (val result = api.forgotPassword(ForgotPasswordRequest(email))) {
            is Success -> Success(result.data.toDomain())
            is Error -> Error(result.code, result.message, result.body)
            is Exception -> Exception(result.throwable)
        }
    }

    override suspend fun otpVerification(email: String, otp: String): ApiResult<OTPResult> {
        return when (val result = api.otpVerification(OTPRequest(email, otp))) {
            is Success -> Success(result.data.toDomain())
            is Error -> Error(result.code, result.message, result.body)
            is Exception -> Exception(result.throwable)
        }
    }
    // forgot password unavailable rn need to be tested thoroughly

    override suspend fun resetPassword(
        token: String,
        newPassword: String,
        confirmPassword: String
    ): ApiResult<ResetPasswordResult> {
        return when (val result = api.resetPassword(
            ResetPasswordRequest(
                token,
                newPassword,
                confirmPassword = confirmPassword
            )
        )) {
            is Success -> Success(result.data.toDomain())
            is Error -> Error(result.code, result.message, result.body)
            is Exception -> Exception(result.throwable)
        }
    }
    // not tested yet depends on forgot password

    override suspend fun verifyEmail(
        email: String,
        otp: String
    ): ApiResult<EmailVerificationResult> {
        return when (val result = api.verifyEmail(EmailVerificationRequest(email, otp))) {
            is Success -> Success(result.data.toDomain())
            is Error -> Error(result.code, result.message, result.body)
            is Exception -> Exception(result.throwable)

        }
    }

    override suspend fun changePassword(
        oldPassword: String,
        newPassword: String,
        confirmPassword: String
    ): ApiResult<ChangePassResult> {
        return when (val result =
            api.changePassword(ChangePassRequest(oldPassword, newPassword, confirmPassword))) {
            is Success -> Success(result.data.toDomain())
            is Error -> Error(result.code, result.message, result.body)
            is Exception -> Exception(result.throwable)
        }
    }
}