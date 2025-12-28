package com.example.carware.network.api

import com.example.carware.network.apiRequests.auth.LoginRequest
import com.example.carware.network.apiRequests.auth.SignUpRequest
import com.example.carware.network.apiRequests.auth.ForgotPasswordRequest
import com.example.carware.network.apiRequests.auth.OTPRequest
import com.example.carware.network.apiRequests.auth.ResetPasswordRequest
import com.example.carware.network.apiResponse.auth.AuthResponse
import com.example.carware.network.apiResponse.auth.ForgotPasswordResponse
import com.example.carware.network.apiResponse.auth.OTPResponse
import com.example.carware.network.apiResponse.auth.ResetPasswordResponse
import com.example.carware.network.createHttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.*
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import io.ktor.http.contentType

const val baseUrl = "https://lj7pt7bd-7136.uks1.devtunnels.ms"

suspend fun signupUser(request: SignUpRequest): AuthResponse {
    val client = createHttpClient()

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
            val errorResponse = response.body<AuthResponse>()
            throw Exception(errorResponse.message ?: "Request failed")
        }

        // HTTP 200 - parse and return
        response.body<AuthResponse>()

    } catch (e: Exception) {
        println("❌ Signup failed: ${e.message}")
        throw Exception(e.message ?: "Signup failed")
    }
}
suspend fun loginUser(request: LoginRequest): AuthResponse {
    val client = createHttpClient()

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

suspend fun forgotPasswordUser(request: ForgotPasswordRequest): ForgotPasswordResponse {
    val client = createHttpClient()
    return client.post("$baseUrl/api/Auth/forgot-password") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }.body()
}

suspend fun otpVerificationUser(request: OTPRequest): OTPResponse {
    val client = createHttpClient()
    return client.post("$baseUrl/api/Auth/Verify-Otp") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }.body()
}

    suspend fun resetPasswordUser(request: ResetPasswordRequest): ResetPasswordResponse {
    val client = createHttpClient()
    return client.post("$baseUrl/api/Auth/reset-password") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }.body()
}