package com.example.carware.repository.auth

import com.example.carware.network.api.forgotPasswordUser
import com.example.carware.network.api.loginUser
import com.example.carware.network.api.otpVerificationUser
import com.example.carware.network.api.resetPasswordUser
import com.example.carware.network.api.signupUser
import com.example.carware.network.apiRequests.auth.ForgotPasswordRequest
import com.example.carware.network.apiRequests.auth.LoginRequest
import com.example.carware.network.apiRequests.auth.OTPRequest
import com.example.carware.network.apiRequests.auth.ResetPasswordRequest
import com.example.carware.network.apiRequests.auth.SignUpRequest
import com.example.carware.network.apiResponse.auth.AuthResponse
import com.example.carware.network.apiResponse.auth.ForgotPasswordResponse
import com.example.carware.network.apiResponse.auth.OTPResponse
import com.example.carware.network.apiResponse.auth.ResetPasswordResponse
import com.example.carware.util.storage.PreferencesManager

class AuthRepository(private val preferencesManager: PreferencesManager) {

    suspend fun signUpRepo(request: SignUpRequest): AuthResponse {

        return signupUser(request)  // Calls your API function directly
    }


    suspend fun logInRepo(request: LoginRequest): AuthResponse {
        return loginUser(request)
    }

    suspend fun forgotPasswordRepo(request: ForgotPasswordRequest): ForgotPasswordResponse {
        return forgotPasswordUser(request)
    }

    suspend fun otpVerificationRepo(request: OTPRequest): OTPResponse {
        return otpVerificationUser(request)
    }

    suspend fun resetPasswordRepo(request: ResetPasswordRequest): ResetPasswordResponse {
        return resetPasswordUser(request)
    }

}