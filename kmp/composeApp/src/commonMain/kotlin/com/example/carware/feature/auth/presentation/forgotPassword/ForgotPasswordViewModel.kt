package com.example.carware.feature.auth.presentation.forgotPassword

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

class ForgotPasswordViewModel (
    private val repository: AuthRepository,
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

    fun forgotPassword() {
        val validationError = validateForm()
        if (validationError != null) {
            _state.update { it.copy(errorMessage = validationError) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = repository.forgotPassword(_state.value.email)) {
                is ApiResult.Success -> _state.update {
                    it.copy(isLoading = false, isSuccess = true)
                }
                is ApiResult.Error -> _state.update {
                    it.copy(isLoading = false, errorMessage = result.message)
                }
                is ApiResult.Exception -> _state.update {
                    it.copy(isLoading = false, errorMessage = "Something went wrong")
                }
            }
        }
    }}
