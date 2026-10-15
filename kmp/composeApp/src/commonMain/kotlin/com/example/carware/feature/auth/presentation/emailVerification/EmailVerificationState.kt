package com.example.carware.feature.auth.presentation.emailVerification

data class EmailVerificationState(

    val otp: String = "",

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,


    val otpError: Boolean = false,
)