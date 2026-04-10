package com.example.carware.network.api

import com.example.carware.network.apiRequests.auth.EmailVerificationRequest
import com.example.carware.network.apiRequests.auth.ForgotPasswordRequest
import com.example.carware.network.apiRequests.auth.GoogleSignInRequest
import com.example.carware.network.apiRequests.auth.LoginRequest
import com.example.carware.network.apiRequests.auth.OTPRequest
import com.example.carware.network.apiRequests.auth.ResetPasswordRequest
import com.example.carware.network.apiRequests.auth.SignUpRequest
import com.example.carware.network.apiResponse.auth.AuthResponse
import com.example.carware.network.apiResponse.auth.EmailVerificationResponse
import com.example.carware.network.apiResponse.auth.ForgotPasswordResponse
import com.example.carware.network.apiResponse.auth.GoogleSignInResponse
import com.example.carware.network.apiResponse.auth.OTPResponse
import com.example.carware.network.apiResponse.auth.ResetPasswordResponse
import com.example.carware.network.apiResponse.auth.SignUpResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

const val baseUrl = "https://mbr73kbz-7136.uks1.devtunnels.ms"

suspend fun signupUser(request: SignUpRequest,client: HttpClient): SignUpResponse {

    println("➡️ Signup request started")
    println("📤 Request body: $request")

    val response: HttpResponse = client.post("$baseUrl/api/Auth/register") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }

    val rawBody = response.bodyAsText()

    return try {
        // Check HTTP status code first
        if (!response.status.isSuccess()) {
            // 400, 401, 500, etc. - treat as error
            val errorResponse = response.body<SignUpResponse>()
            throw Exception(errorResponse.message ?: "Request failed")
        }

        // HTTP 200 - parse and return
        response.body<SignUpResponse>()

    } catch (e: Exception) {
        println("❌ Signup failed: ${e.message}")
        throw Exception(e.message ?: "Signup failed")
    }
}
suspend fun loginUser(request: LoginRequest,client: HttpClient): AuthResponse {

    println("➡️ login request started")
    println("📤 Request body: $request")

    val response: HttpResponse = client.post("$baseUrl/api/Auth/login") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }

    val rawBody = response.bodyAsText()

    return try {
        // Check HTTP status code first
        if (!response.status.isSuccess()) {
            val errorResponse = response.body<AuthResponse>()
            throw Exception(errorResponse.message ?: "Request failed")
        }

        // HTTP 200 - parse and return
        response.body<AuthResponse>()

    } catch (e: Exception) {
        println("❌ login failed: ${e.message}")
        println("Raw response: $rawBody")
        throw Exception(e.message ?: "login failed")
    }
}

suspend fun forgotPasswordUser(request: ForgotPasswordRequest,client: HttpClient): ForgotPasswordResponse {
    return client.post("$baseUrl/api/Auth/forgot-password") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }.body()
}

suspend fun otpVerificationUser(request: OTPRequest,client: HttpClient): OTPResponse {
    return client.post("$baseUrl/api/Auth/Verify-Otp") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }.body()
}

    suspend fun resetPasswordUser(request: ResetPasswordRequest,client: HttpClient): ResetPasswordResponse {
    return client.post("$baseUrl/api/Auth/reset-password") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }.body()
}

suspend fun verifyEmailUser(request: EmailVerificationRequest,client: HttpClient): EmailVerificationResponse{
    return client.post("$baseUrl/api/Auth/verify-email-otp") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }.body()
}
suspend fun googleSignIn(request: GoogleSignInRequest,client: HttpClient): GoogleSignInResponse {
    return client.post("$baseUrl/api/Auth/google-mobile") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }.body()
}