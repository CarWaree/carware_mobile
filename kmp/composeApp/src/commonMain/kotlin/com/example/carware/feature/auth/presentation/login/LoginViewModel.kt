package com.example.carware.feature.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carware.core.network.ApiResult
import com.example.carware.core.storage.PreferencesManager
import com.example.carware.feature.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun onEmailOrUsernameChange(value: String) = _state.update { it.copy(emailOrUsername = value) }
    fun onPasswordChange(value: String) = _state.update { it.copy(password = value) }
    fun clearErrorMessage() = _state.update { it.copy(errorMessage = null) }

    private fun validateForm(): String? {
        val state = _state.value
        var emailOrUsernameError = false
        var passwordError = false
        var errorMessage: String? = null

        if (state.emailOrUsername.isBlank()) {
            emailOrUsernameError = true
            errorMessage = "Email or username is required"
        } else if (state.password.isBlank()) {
            passwordError = true
            errorMessage = "Password is required"
        }

        _state.update {
            it.copy(
                emailOrUsernameError = emailOrUsernameError,
                passwordError = passwordError
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
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = repository.login(
                emailOrUsername = _state.value.emailOrUsername,
                password = _state.value.password
            )) {
                is ApiResult.Success -> {
                    val data = result.data
                    preferencesManager.performLogin(data.accessToken)
                    preferencesManager.saveRefreshToken(data.refreshToken)
                    preferencesManager.saveExpiresOn(data.refreshTokenExpiration)
                    preferencesManager.saveEmailVerified(true)

                    _state.update {
                        it.copy(isLoading = false, isSuccess = true)
                    }
                }
                is ApiResult.Error -> {
                    _state.update {
                        it.copy(isLoading = false, errorMessage = result.message)
                    }
                }
                is ApiResult.Exception -> {
                    _state.update {
                        it.copy(isLoading = false, errorMessage = "Something went wrong")
                    }
                }
            }
        }
    }
    fun googleSignIn(idToken: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = repository.googleSignIn(idToken)) {
                is ApiResult.Success -> {
                    val data = result.data
                    preferencesManager.performLogin(data.accessToken)
                    preferencesManager.saveRefreshToken(data.refreshToken)
                    preferencesManager.saveExpiresOn(data.refreshTokenExpiration)
                    preferencesManager.saveEmailVerified(true)

                    _state.update {
                        it.copy(isLoading = false, isSuccess = true)
                    }
                }
                is ApiResult.Error -> {
                    _state.update {
                        it.copy(isLoading = false, errorMessage = result.message)
                    }
                }
                is ApiResult.Exception -> {
                    _state.update {
                        it.copy(isLoading = false, errorMessage = "Something went wrong")
                    }
                }
            }
        }
    }
}