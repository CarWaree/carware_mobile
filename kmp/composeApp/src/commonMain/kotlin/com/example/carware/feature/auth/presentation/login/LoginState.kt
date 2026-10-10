package com.example.carware.feature.auth.presentation.login

data class LoginState(
    val emailOrUsername: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val emailOrUsernameError: Boolean = false,
    val passwordError: Boolean = false
)