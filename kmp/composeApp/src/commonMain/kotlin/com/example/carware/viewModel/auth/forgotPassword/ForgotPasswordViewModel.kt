package com.example.carware.viewModel.auth.forgotPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carware.network.apiRequests.auth.ForgotPasswordRequest
import com.example.carware.repository.auth.AuthRepository
import com.example.carware.util.storage.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForgotPasswordViewModel (
    private val repository: AuthRepository,
    private val preferencesManager: PreferencesManager,
) : ViewModel() {
    private val _state= MutableStateFlow(ForgotPasswordState())
    val state: StateFlow<ForgotPasswordState> = _state.asStateFlow()

    fun onEmailChange(value: String) = _state.update { it.copy(email = value) }

    fun clearErrorMessage() = _state.update { it.copy(errorMessage = null) }

    private fun validateForm(): String?{
        val state =_state.value
        var emailError=false
        var errorMessage: String? = null



        if (state.email.isBlank()){
            emailError=true
            errorMessage ="Email is Requeired"
        }
        return errorMessage
    }

    fun forgotPassword(){
        val validationError = validateForm()
        if (validationError != null) {
            _state.update { it.copy(errorMessage = validationError) }
            return
        }

        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true, errorMessage = null) }
                val request = ForgotPasswordRequest(
                    email = _state.value.email
                )
                val response=repository.forgotPasswordRepo(request)
                 _state.update { it.copy(isLoading = false, isSuccess = true) }

            }catch (e: Exception){
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
