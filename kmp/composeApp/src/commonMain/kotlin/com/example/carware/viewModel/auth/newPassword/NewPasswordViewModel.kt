package com.example.carware.viewModel.auth.newPassword

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carware.network.api.resetPasswordUser
import com.example.carware.network.apiRequests.auth.ResetPasswordRequest
import com.example.carware.repository.auth.AuthRepository
import com.example.carware.util.storage.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.readResourceBytes

class NewPasswordViewModel (
    private val repository: AuthRepository,
    private val preferencesManager: PreferencesManager,
) : ViewModel() {

    private val _state = MutableStateFlow(NewPasswordState())

    val state : StateFlow<NewPasswordState> = _state.asStateFlow()

    fun onPasswordChange(value: String) = _state.update { it.copy(pass = value) }
    fun onConfirmPasswordChange(value: String) = _state.update { it.copy(pass = value) }

    fun clearErrorMessage() = _state.update { it.copy(errorMessage = null) }

    private  fun validateForm():String?{
        val state =_state.value

        var passError=false
        var confPassError=false
        var errorMessage: String? = null



        if (state.confPass.isBlank()) {
            confPassError = true
            errorMessage = "email or username is required"

        } else if (state.pass.isBlank()) {
            passError = true
            errorMessage = "Password is required"
        }
        _state.update {
            it.copy(
                confPassError = confPassError,
                passError = passError
            )
        }
        return errorMessage
    }

    fun newPassword(){
        val validationError = validateForm()
        if (validationError != null) {
            _state.update { it.copy(errorMessage = validationError) }
            return
        }

        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true, errorMessage = null) }

                val request = ResetPasswordRequest(
                    token = preferencesManager.getToken(),
                    newPassword = _state.value.pass,
                    confirmPassword =_state.value.confPass
                )

                val response=repository.resetPasswordRepo(request)
                _state.update {
                    it.copy(isLoading = false , isSuccess = true)
                }

            }catch (e: Exception) {
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