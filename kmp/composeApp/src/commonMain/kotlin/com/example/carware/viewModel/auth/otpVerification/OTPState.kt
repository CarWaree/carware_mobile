package com.example.carware.viewModel.auth.otpVerification

data class OTPState(


    val otp: String = "",

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,


    val otpError: Boolean = false,
)