package com.example.carware.feature.auth.presentation.resetPassword

data class ResetPasswordState(


val pass: String = "",
val confPass:String="",

val isLoading: Boolean = false,
val isSuccess: Boolean = false,
val errorMessage: String? = null,


val passError: Boolean = false,
val confPassError: Boolean = false,

)