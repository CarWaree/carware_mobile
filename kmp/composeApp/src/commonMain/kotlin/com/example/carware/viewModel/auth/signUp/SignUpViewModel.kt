package com.example.carware.viewModel.auth.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carware.network.apiRequests.auth.SignUpRequest
import com.example.carware.repository.auth.AuthRepository
import com.example.carware.util.storage.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val repository: AuthRepository,
    private val preferencesManager: PreferencesManager,
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpState())

    val state: StateFlow<SignUpState> = _state.asStateFlow()

    fun onFirstNameChange(value: String) = _state.update { it.copy(firstName = value) }
    fun onLastNameChange(value: String) = _state.update { it.copy(lastName = value) }
    fun onUserNameChange(value: String) = _state.update { it.copy(userName = value) }
    fun onEmailChange(value: String) = _state.update { it.copy(email = value) }
    fun onPasswordChange(value: String) = _state.update { it.copy(pass = value) }

    fun onConfirmPasswordChange(value: String) =
        _state.update { it.copy(confPass = value, confPassError = false) }

    fun onToggleAgree(value: Boolean) =
        _state.update { it.copy(agreeTerms = value, agreedError = false) }

    fun clearErrorMessage() = _state.update { it.copy(errorMessage = null) }

    private fun validateForm(): String? {
        val state = _state.value

        var userNameError = false
        var emailError = false
        var passError = false
        var confPassError = false
        var agreedError = false
        var errorMessage: String? = null

        // Check each field and set specific error
        if (state.userName.isBlank()) {
            userNameError = true
            errorMessage = "Username is required"
        } else if (state.email.isBlank()) {
            emailError = true
            errorMessage = "Email is required"
        } else if (state.pass.isBlank()) {
            passError = true
            errorMessage = "Password is required"
        } else if (state.confPass.isBlank()) {
            confPassError = true
            errorMessage = "Confirm password is required"
        } else if (state.pass != state.confPass) {
            confPassError = true
            errorMessage = "Passwords do not match"
        } else if (!state.agreeTerms) {
            agreedError = true
            errorMessage = "You must agree to the terms"
        }

        _state.update {
            it.copy(
                userNameError = userNameError,
                emailError = emailError,
                passError = passError,
                confPassError = confPassError,
                agreedError = agreedError
            )
        }

        return errorMessage
    }

    fun signup() {
        // Validate first
        val validationError = validateForm()
        if (validationError != null) {
            _state.update { it.copy(errorMessage = validationError) }
            return
        }

        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true, errorMessage = null) }

                val request = SignUpRequest(
                    firstName = _state.value.firstName,
                    lastName = _state.value.lastName,
                    userName = _state.value.userName,
                    email = _state.value.email,
                    password = _state.value.pass,
                    confirmPassword = _state.value.confPass,
                )

                val response = repository.signUpRepo(request)

                if (response.data?.isAuthenticated == true) {
                    _state.update { it.copy(isLoading = false, isSuccess = true) }
                } else {
                    // User created but needs email verification
                    _state.update { it.copy(isLoading = false, needsEmailVerification = true) }
                }


                val token = response.data?.token
                    ?: throw IllegalStateException("Token missing in response")
                preferencesManager.saveToken(token)


                _state.update {
                    it.copy(isLoading = false, isSuccess = true)

                }

            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "An error occurred"
                    )
                }
            }
        }
    }

}
