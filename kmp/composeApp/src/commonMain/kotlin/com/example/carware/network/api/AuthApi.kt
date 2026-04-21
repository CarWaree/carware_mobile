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

const val baseUrl = "https://01k6q7hb-7136.uks1.devtunnels.ms"

suspend fun signupUser(request: SignUpRequest,client: HttpClient): SignUpResponse {

    println("➡️ Signup request started")
    println("📤 Request body: $request")

    val response: HttpResponse = client.post("$baseUrl/api/Auth/register") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }

    val rawBody = response.bodyAsText()
    println("📥 Raw responseSignup: $rawBody") // ADD THIS


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
} //done
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
        println("Raw response login: $rawBody")
        throw Exception(e.message ?: "login failed")
    }
} //DONE

suspend fun forgotPasswordUser(request: ForgotPasswordRequest, client: HttpClient): ForgotPasswordResponse {
    println("➡️ forgotPassword request started")
    println("📤 Request body: $request")

    val response: HttpResponse = client.post("$baseUrl/api/Auth/forgot-password") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }

    val rawBody = response.bodyAsText()
    println("📥 Raw response forgotPassword: $rawBody")

    return try {
        if (!response.status.isSuccess()) {
            val errorResponse = response.body<ForgotPasswordResponse>()
            throw Exception(errorResponse.message ?: "Request failed")
        }
        response.body<ForgotPasswordResponse>()
    } catch (e: Exception) {
        println("❌ forgotPassword failed: ${e.message}")
        throw Exception(e.message ?: "forgotPassword failed")
    }
}

suspend fun otpVerificationUser(request: OTPRequest, client: HttpClient): OTPResponse {
    println("➡️ otpVerification request started")
    println("📤 Request body: $request")

    val response: HttpResponse = client.post("$baseUrl/api/Auth/Verify-Otp") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }

    val rawBody = response.bodyAsText()
    println("📥 Raw response otpVerification: $rawBody")

    return try {
        if (!response.status.isSuccess()) {
            val errorResponse = response.body<OTPResponse>()
            throw Exception(errorResponse.message ?: "Request failed")
        }
        response.body<OTPResponse>()
    } catch (e: Exception) {
        println("❌ otpVerification failed: ${e.message}")
        throw Exception(e.message ?: "otpVerification failed")
    }
}

suspend fun resetPasswordUser(request: ResetPasswordRequest, client: HttpClient): ResetPasswordResponse {
    println("➡️ resetPassword request started")
    println("📤 Request body: $request")

    val response: HttpResponse = client.post("$baseUrl/api/Auth/reset-password") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }

    val rawBody = response.bodyAsText()
    println("📥 Raw response resetPassword: $rawBody")

    return try {
        if (!response.status.isSuccess()) {
            val errorResponse = response.body<ResetPasswordResponse>()
            throw Exception(errorResponse.message ?: "Request failed")
        }
        response.body<ResetPasswordResponse>()
    } catch (e: Exception) {
        println("❌ resetPassword failed: ${e.message}")
        throw Exception(e.message ?: "resetPassword failed")
    }
}
suspend fun verifyEmailUser(request: EmailVerificationRequest,client: HttpClient): EmailVerificationResponse{
    val response: HttpResponse = client.post("$baseUrl/api/Auth/verify-email-otp") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }
    val rawBody = response.bodyAsText()
    println("📥 Raw response email verify: $rawBody") // ADD THIS

    return try {
        // Check HTTP status code first
        if (!response.status.isSuccess()) {
            // 400, 401, 500, etc. - treat as error
            val errorResponse = response.body<EmailVerificationResponse>()
            throw Exception(errorResponse.message ?: "Request failed")
        }

        // HTTP 200 - parse and return
        response.body<EmailVerificationResponse>()

    } catch (e: Exception) {
        println("❌ otp verify failed: ${e.message}")
        throw Exception(e.message ?: "otp verify failed")
    }
} //DONE

suspend fun googleSignIn(request: GoogleSignInRequest,client: HttpClient): GoogleSignInResponse {

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

        // HTTP 200 - parse and return
        response.body<GoogleSignInResponse>()

    } catch (e: Exception) {
        println("❌ googleSignIn failed: ${e.message}")
        println("Raw response googleSignIn: $rawBody")
        throw Exception(e.message ?: "googleSignIn failed")
    }
} //DONE
