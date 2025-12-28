package com.example.carware.viewModel.auth.logIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carware.network.apiRequests.auth.LoginRequest
import com.example.carware.repository.auth.AuthRepository
import com.example.carware.util.storage.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LogInViewModel(
    private val repository: AuthRepository,
    private val preferencesManager: PreferencesManager,
) : ViewModel() {
    private val _state = MutableStateFlow(LogInState())
    val state: StateFlow<LogInState> = _state.asStateFlow()

    fun onEmailOrUsernameChange(value: String) = _state.update { it.copy(emailOrUsername = value) }
    fun onPasswordChange(value: String) = _state.update { it.copy(pass = value) }
    fun clearErrorMessage() = _state.update { it.copy(errorMessage = null) }


    private fun validateForm(): String? {
        val state = _state.value

        var emailOrUsernameError = false
        var passError = false
        var errorMessage: String? = null


        if (state.emailOrUsername.isBlank()) {
            emailOrUsernameError = true
            errorMessage = "email or username is required"

        } else if (state.pass.isBlank()) {
            passError = true
            errorMessage = "Password is required"
        }
        _state.update {
            it.copy(
                emailOrUsernameError = emailOrUsernameError,
                passError = passError
            )
        }
        return errorMessage
    }

    fun login() {
        val validationError = validateForm()
        if (validationError != null) {
            _state.update { it.copy(errorMessage = validationError) }
            return
        }

        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true, errorMessage = null) }

                val request = LoginRequest(
                    emailOrUsername = _state.value.emailOrUsername,
                    password = _state.value.pass

                )
                val response = repository.logInRepo(request)

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

