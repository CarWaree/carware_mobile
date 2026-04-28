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
import kotlinx.serialization.json.Json
//const val baseUrl = "https://63nw61z7-7136.euw.devtunnels.ms" //abdo
const val baseUrl = "https://qd5sjv6c-7136.uks1.devtunnels.ms"
suspend fun signupUser(request: SignUpRequest, client: HttpClient): SignUpResponse {
    val response: HttpResponse = client.post("$baseUrl/api/Auth/register") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }
    val body = response.bodyAsText()
    val parsed = Json.decodeFromString<SignUpResponse>(body)

    if (!response.status.isSuccess()) {
        throw Exception(parsed.message)
    }
    return parsed
} //done

suspend fun loginUser(request: LoginRequest, client: HttpClient): AuthResponse {
    val response: HttpResponse = client.post("$baseUrl/api/Auth/login") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }

    val body = response.bodyAsText()
    val parsed = Json.decodeFromString<AuthResponse>(body)
    if (!response.status.isSuccess()) {
        throw Exception(parsed.message)
    }
    return parsed
} //DONE

suspend fun forgotPasswordUser(
    request: ForgotPasswordRequest,
    client: HttpClient
): ForgotPasswordResponse {
    println("➡️ forgotPassword request started")
    println("📤 Request body: $request")

    val response: HttpResponse = client.post("$baseUrl/api/Auth/forgot-password") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }
    val body = response.bodyAsText()
    val parsed = Json.decodeFromString<ForgotPasswordResponse>(body)
    if (!response.status.isSuccess()) {
        throw Exception(parsed.message)
    }
    return parsed
}

suspend fun otpVerificationUser(request: OTPRequest, client: HttpClient): OTPResponse {
    val response: HttpResponse = client.post("$baseUrl/api/Auth/Verify-Otp") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }
    val body = response.bodyAsText()
    val parsed = Json.decodeFromString<OTPResponse>(body)
    if (!response.status.isSuccess()) {
        throw Exception(parsed.message)
    }
    return parsed
}

suspend fun resetPasswordUser(
    request: ResetPasswordRequest,
    client: HttpClient
): ResetPasswordResponse {
    val response: HttpResponse = client.post("$baseUrl/api/Auth/reset-password") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }
    val body = response.bodyAsText()
    val parsed = Json.decodeFromString<ResetPasswordResponse>(body)
    if (!response.status.isSuccess()) {
        throw Exception(parsed.message)
    }
    return parsed
}

suspend fun verifyEmailUser(
    request: EmailVerificationRequest,
    client: HttpClient
): EmailVerificationResponse {
    val response: HttpResponse = client.post("$baseUrl/api/Auth/verify-email-otp") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }
    val body = response.bodyAsText()
    val parsed = Json.decodeFromString<EmailVerificationResponse>(body)
    if (!response.status.isSuccess()) {
        throw Exception(parsed.message)
    }
    return parsed
}

suspend fun googleSignIn(request: GoogleSignInRequest, client: HttpClient): GoogleSignInResponse {

    println("➡️ googleSignIn request started")
    println("📤 googleSignIn Request body: $request")

    val response: HttpResponse = client.post("$baseUrl/api/Auth/google-mobile") {
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
        response.body<GoogleSignInResponse>()

    } catch (e: Exception) {
        println("❌ googleSignIn failed: ${e.message}")
        println("Raw response googleSignIn: $rawBody")
        throw Exception(e.message ?: "googleSignIn failed")
    }
} //DONE
