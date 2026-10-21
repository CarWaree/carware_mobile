package com.example.carware.feature.auth.presentation.changePassword


data class ChangePassState(
    val oldPass: String = "",
    val newPass: String = "",
    val confNewPass: String = "",

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null,


    val oldPassError: Boolean = false,
    val newPassError: Boolean = false,
    val confNewPassError: Boolean =false,

)