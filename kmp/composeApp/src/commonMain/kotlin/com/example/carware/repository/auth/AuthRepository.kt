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
import com.example.carware.util.storage.PreferencesManager
import io.ktor.client.HttpClient

class AuthRepository(
    private val client: HttpClient) {

    suspend fun signUpRepo(request: SignUpRequest): SignUpResponse {

        return signupUser(request,client)  // Calls your API function directly
    }


    suspend fun logInRepo(request: LoginRequest): AuthResponse {

        return loginUser(request,client)
    }

    suspend fun forgotPasswordRepo(request: ForgotPasswordRequest): ForgotPasswordResponse {
        return forgotPasswordUser(request,client)
    }

    suspend fun otpVerificationRepo(request: OTPRequest): OTPResponse {
        return otpVerificationUser(request,client)
    }

    suspend fun resetPasswordRepo(request: ResetPasswordRequest): ResetPasswordResponse {
        return resetPasswordUser(request,client)
    }
    suspend fun verifyEmailRepo(request: EmailVerificationRequest): EmailVerificationResponse{
        return verifyEmailUser(request,client)
    }

    suspend fun googleSignInRepo(request: GoogleSignInRequest): GoogleSignInResponse {
        return googleSignIn(request,client)
    }

}