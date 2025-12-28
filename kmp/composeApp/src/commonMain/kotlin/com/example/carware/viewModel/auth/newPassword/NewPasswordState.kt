package com.example.carware.viewModel.auth.newPassword

data class NewPasswordState(


val pass: String = "",
val confPass:String="",

val isLoading: Boolean = false,
val isSuccess: Boolean = false,
val errorMessage: String? = null,


val passError: Boolean = false,
val confPassError: Boolean = false,

)