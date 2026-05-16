package com.example.carware.repository.auth

import com.example.carware.network.api.forgotPasswordUser
import com.example.carware.network.api.googleSignIn
import com.example.carware.network.api.loginUser
import com.example.carware.network.api.otpVerificationUser
import com.example.carware.network.api.resetPasswordUser
import com.example.carware.network.api.signupUser
import com.example.carware.network.api.verifyEmailUser
import com.example.carware.network.apiRequests.auth.EmailVerificationRequest
import com.example.carware.network.apiRequests.auth.ForgotPasswordRequest
import com.example.carware.network.apiRequests.auth.GoogleSignInRequest
import com.example.carware.network.apiRequests.auth.LoginRequest
import com.example.carware.network.apiRequests.auth.OTPRequest
import com.example.carware.network.apiRequests.auth.ResetPasswordRequest
import com.example.carware.network.apiRequests.auth.SignUpRequest
import com.example.carware.network.apiResponse.auth.GoogleSignInResponse
import com.example.carware.network.apiResponse.auth.AuthResponse
import com.example.carware.network.apiResponse.auth.EmailVerificationResponse
import com.example.carware.network.apiResponse.auth.ForgotPasswordResponse
import com.example.carware.network.apiResponse.auth.OTPResponse
import com.example.carware.network.apiResponse.auth.ResetPasswordResponse
import com.example.carware.network.apiResponse.auth.SignUpResponse
import com.example.carware.network.core.ApiResult
import com.example.carware.util.storage.PreferencesManager
import io.ktor.client.HttpClient

class AuthRepository(
    private val client: HttpClient
) {

    suspend fun signUpRepo(request: SignUpRequest): SignUpResponse {
        return when (val result = signupUser(request, client)) {
            is ApiResult.Success -> {
                result.data
            }

            is ApiResult.Error -> {
                throw Exception("Sign up failed: ${result.message} (${result.code})")
            }

            is ApiResult.Exception -> {
                throw Exception("Sign up error: ${result.throwable.message}")
            }
        }
    }

    suspend fun logInRepo(request: LoginRequest): AuthResponse {

        return when (val result = loginUser(request, client)) {
            is ApiResult.Success -> {
                result.data
            }

            is ApiResult.Error -> {
                throw Exception("Log in failed: ${result.message} (${result.code})")
            }

            is ApiResult.Exception -> {
                throw Exception("Log in error: ${result.throwable.message}")
            }
        }

    }

    suspend fun forgotPasswordRepo(request: ForgotPasswordRequest): ForgotPasswordResponse {
        return when (val result = forgotPasswordUser(request, client)) {
            is ApiResult.Success -> {
                result.data
            }

            is ApiResult.Error -> {
                throw Exception("forgot Password failed: ${result.message} (${result.code})")
            }

            is ApiResult.Exception -> {
                throw Exception("forgot Password error: ${result.throwable.message}")
            }

        }
    }

    suspend fun otpVerificationRepo(request: OTPRequest): OTPResponse {
        return when (val result = otpVerificationUser(request, client)) {
            is ApiResult.Success -> {
                result.data
            }

            is ApiResult.Error -> {
                throw Exception("otp Verification failed: ${result.message} (${result.code})")
            }

            is ApiResult.Exception -> {
                throw Exception("otp Verification error: ${result.throwable.message}")
            }

        }
    }

    suspend fun resetPasswordRepo(request: ResetPasswordRequest): ResetPasswordResponse {
        return when (val result = resetPasswordUser(request, client)) {
            is ApiResult.Success -> {
                result.data
            }

            is ApiResult.Error -> {
                throw Exception("reset Password failed: ${result.message} (${result.code})")
            }

            is ApiResult.Exception -> {
                throw Exception("reset Password error: ${result.throwable.message}")
            }

        }
    }

    suspend fun verifyEmailRepo(request: EmailVerificationRequest): EmailVerificationResponse {
        return when (val result = verifyEmailUser(request, client)) {
            is ApiResult.Success -> {
                result.data
            }

            is ApiResult.Error -> {
                throw Exception("verify Email failed: ${result.message} (${result.code})")
            }

            is ApiResult.Exception -> {
                throw Exception("verify Email error: ${result.throwable.message}")
            }

        }
    }

    suspend fun googleSignInRepo(request: GoogleSignInRequest): GoogleSignInResponse {
        return when (val result = googleSignIn(request, client)) {
            is ApiResult.Success -> {
                result.data
            }

            is ApiResult.Error -> {
                throw Exception("googleSign failed: ${result.message} (${result.code})")
            }

            is ApiResult.Exception -> {
                throw Exception("googleSign error: ${result.throwable.message}")
            }

        }
    }
}