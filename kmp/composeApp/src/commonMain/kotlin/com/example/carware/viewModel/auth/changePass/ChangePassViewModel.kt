package com.example.carware.viewModel.auth.changePass

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carware.network.apiRequests.auth.ChangePassRequest
import com.example.carware.network.apiResponse.auth.AuthResponse
import com.example.carware.network.apiResponse.auth.ChangePassResponse
import com.example.carware.network.core.UiResult
import com.example.carware.repository.auth.AuthRepository
import com.plusmobileapps.konnectivity.Konnectivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChangePassViewModel(
    private val repository: AuthRepository,
    connectivityManager: Konnectivity

) : ViewModel() {
    private val _state = MutableStateFlow(ChangePassState())
    val state: StateFlow<ChangePassState> = _state.asStateFlow()
    val isConnected = connectivityManager.isConnectedState

    fun onPassChange(value: String) = _state.update { it.copy(oldPass = value) }
    fun onNewPassChange(value: String) = _state.update { it.copy(newPass = value) }
    fun onConfNewPassChange(value: String) = _state.update { it.copy(confNewPass = value) }

    private fun validateForm(): String? {
        val state = _state.value

        var oldPassError = false
        var newPassError = false
        var confNewPassError = false
        var errorMessage: String? = null

        if (state.oldPass.isBlank()) {
            oldPassError = true
            errorMessage = "Old password is required"
        } else if (state.newPass.isBlank()) {
            newPassError = true
            errorMessage = "New password is required"
        } else if (state.confNewPass.isBlank()) {
            confNewPassError = true
            errorMessage = "Please confirm your new password"
        } else if (state.newPass != state.confNewPass) {
            confNewPassError = true
            errorMessage = "Passwords do not match"
        }

        _state.update {
            it.copy(
                oldPassError = oldPassError,
                newPassError = newPassError,
                confNewPassError = confNewPassError
            )
        }

        return errorMessage
    }

    fun changePass() {
        val validationError = validateForm()
        if (validationError != null) {
            _state.update { it.copy(errorMessage = validationError) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            val request = ChangePassRequest(
                oldPassword = _state.value.oldPass,
                newPassword = _state.value.newPass,
                confirmPassword = _state.value.confNewPass
            )

            when (val result: UiResult<ChangePassResponse> = repository.changePassRepo(request)) {
                is UiResult.Success -> {
                    val response = result.data
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = true,
                            successMessage = "password changed successfully" // need translation
                        )
                    }
                }

                is UiResult.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }

    fun clearMessage() {
        _state.update {
            it.copy(
                successMessage = null,
                errorMessage = null,
            )
        }
    }
}