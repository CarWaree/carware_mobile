package com.example.carware.viewModel.auth.logIn

data class LogInState(


    val emailOrUsername: String = "",
    val pass: String = "",

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,


    val emailOrUsernameError: Boolean = false,
    val passError: Boolean = false,
)