package com.example.carware.viewModel.auth.signUp

  data class SignUpState(
    val firstName: String = "",
    val lastName: String = "",

    val userName: String = "",
    val email: String = "",
    val pass: String = "",
    val confPass: String = "",
    val agreeTerms: Boolean = false,

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val needsEmailVerification: Boolean = false,  // Add this


    val userNameError: Boolean = false,
    val emailError: Boolean = false,
    val passError: Boolean = false,
    val confPassError: Boolean = false,
    val agreedError: Boolean = false
)