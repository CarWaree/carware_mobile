package com.example.carware.network

import com.example.carware.network.apiRequests.LoginRequest
import com.example.carware.network.apiRequests.SignUpRequest
import com.example.carware.network.apiRequests.ForgotPasswordRequest
import com.example.carware.network.apiRequests.OTPRequest
import com.example.carware.network.apiRequests.ResetPasswordRequest
import com.example.carware.network.apiResponse.AuthResponse
import com.example.carware.network.apiResponse.ForgotPasswordResponse
import com.example.carware.network.apiResponse.OTPResponse
import com.example.carware.network.apiResponse.ResetPasswordResponse
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.request.setBody
import io.ktor.http.*
import io.ktor.http.contentType

val baseUrl="https://7w8zsfr4-7136.uks1.devtunnels.ms"
suspend fun signupUser(request: SignUpRequest): AuthResponse {
    val client = createHttpClient()
    return client.post("$baseUrl/api/Auth/register") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }.body()
}
suspend fun loginUser(request: LoginRequest):  AuthResponse{
    val client = createHttpClient()

    return client.post("$baseUrl/api/Auth/login") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }.body()

}
suspend fun forgotPasswordUser(request: ForgotPasswordRequest): ForgotPasswordResponse{
    val client=createHttpClient()
    return client.post("$baseUrl/api/Auth/forgot-password") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }.body()
}
suspend fun otpVerificationUser(request: OTPRequest): OTPResponse{
    val client=createHttpClient()
    return client.post("$baseUrl/api/Auth/Verify-Otp") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }.body()
}
suspend fun resetPasswordUser(request: ResetPasswordRequest): ResetPasswordResponse{
    val client=createHttpClient()
    return client.post("$baseUrl/api/Auth/reset-password") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }.body()
}