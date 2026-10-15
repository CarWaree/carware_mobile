package com.example.carware.feature.auth.presentation.resetPassword

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

class ResetPasswordViewModel(
    private val repository: AuthRepository,
    private val preferencesManager: PreferencesManager,
) : ViewModel() {

    private val _state = MutableStateFlow(ResetPasswordState())
    val state: StateFlow<ResetPasswordState> = _state.asStateFlow()

    fun onPasswordChange(value: String) = _state.update { it.copy(pass = value) }
    fun onConfirmPasswordChange(value: String) = _state.update { it.copy(confPass = value) }
    fun clearErrorMessage() = _state.update { it.copy(errorMessage = null) }

    private fun validateForm(): String? {
        val state = _state.value
        var passError = false
        var confPassError = false
        var errorMessage: String? = null

        if (state.pass.isBlank()) {
            passError = true
            errorMessage = "Password is required"
        } else if (state.pass.length < 8) {
            passError = true
            errorMessage = "Password must be at least 8 characters"
        } else if (state.confPass.isBlank()) {
            confPassError = true
            errorMessage = "Please confirm your password"
        } else if (state.pass != state.confPass) {
            confPassError = true
            errorMessage = "Passwords do not match"
        }

        _state.update { it.copy(passError = passError, confPassError = confPassError) }
        return errorMessage
    }

    fun newPassword() {
        val validationError = validateForm()
        if (validationError != null) {
            _state.update { it.copy(errorMessage = validationError) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            val token = preferencesManager.getResetToken() ?: run {
                _state.update { it.copy(isLoading = false, errorMessage = "Session expired, please try again") }
                return@launch  // exits viewModelScope.launch { }
            }
            when (val result = repository.resetPassword(
                token = token ,
                newPassword = _state.value.pass,
                confirmPassword = _state.value.confPass
            )) {
                is ApiResult.Success -> _state.update {
                    it.copy(isLoading = false, isSuccess = true)
                }
                is ApiResult.Error -> _state.update {
                    it.copy(isLoading = false, errorMessage = result.message)
                }
                is ApiResult.Exception -> _state.update {
                    it.copy(isLoading = false, errorMessage = result.throwable.message ?: "An error occurred")
                }
            }
        }
    }
}