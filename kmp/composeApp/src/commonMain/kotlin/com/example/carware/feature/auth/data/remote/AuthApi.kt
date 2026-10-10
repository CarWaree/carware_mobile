package com.example.carware.feature.auth.data.remote

import com.example.carware.core.network.ApiResult
import com.example.carware.core.network.safeApiCall
import com.example.carware.feature.auth.data.remote.forgotPassword.ForgotPasswordRequest
import com.example.carware.feature.auth.data.remote.forgotPassword.ForgotPasswordResponse
import com.example.carware.feature.auth.data.remote.googleSignIn.GoogleSignInRequest
import com.example.carware.feature.auth.data.remote.googleSignIn.GoogleSignInResponse
import com.example.carware.feature.auth.data.remote.login.AuthResponse
import com.example.carware.feature.auth.data.remote.login.LoginRequest
import com.example.carware.feature.auth.data.remote.otp.OTPRequest
import com.example.carware.feature.auth.data.remote.otp.OTPResponse
import com.example.carware.feature.auth.data.remote.signup.SignUpRequest
import com.example.carware.feature.auth.data.remote.signup.SignUpResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthApi(private val client: HttpClient) {

    suspend fun signUp(request: SignUpRequest): ApiResult<SignUpResponse> =
        safeApiCall {
            client.post("$BASE_URL/api/Auth/register") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
        }

    suspend fun googleSignIn(request: GoogleSignInRequest): ApiResult<GoogleSignInResponse> =
        safeApiCall {
            client.post("$BASE_URL/api/Auth/google-mobile") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
        }

    suspend fun loginUser(request: LoginRequest): ApiResult<AuthResponse> =
        safeApiCall {
            client.post("$BASE_URL/api/Auth/login") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
        }

    suspend fun forgotPasswordUser(request: ForgotPasswordRequest):
            ApiResult<ForgotPasswordResponse> =
        safeApiCall {
            client.post("$BASE_URL/api/Auth/forgot-password") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
        }

    suspend fun otpVerification(request: OTPRequest): ApiResult<OTPResponse> =
        safeApiCall {
            client.post("$BASE_URL/api/Auth/Verify-Otp") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
        }


    companion object {
        private const val BASE_URL = "https://tbvzs781-8081.uks1.devtunnels.ms"
    }
}