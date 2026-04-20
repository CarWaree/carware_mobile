package com.example.carware.viewModel.auth.forgotPassword

data class ForgotPasswordState (
    val email:String="",

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,

    val emailError: Boolean = false,

    )