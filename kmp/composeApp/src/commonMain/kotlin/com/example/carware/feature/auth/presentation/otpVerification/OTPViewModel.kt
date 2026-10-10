package com.example.carware.feature.auth.presentation.otpVerification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carware.core.network.ApiResult
import com.example.carware.feature.auth.data.remote.otp.OTPRequest
import com.example.carware.feature.auth.data.remote.otp.OTPResponse
import com.example.carware.core.network.UiResult
import com.example.carware.core.storage.PreferencesManager
import com.example.carware.feature.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OTPViewModel(
    private val repository: AuthRepository,
    private val preferencesManager: PreferencesManager,
) : ViewModel() {

    private val _state = MutableStateFlow(OTPState())
    val state: StateFlow<OTPState> = _state.asStateFlow()

    fun onOtpChange(value: String) = _state.update { it.copy(otp = value) }
    fun clearErrorMessage() = _state.update { it.copy(errorMessage = null) }

    private fun validateForm(): String? {
        val state = _state.value

        var otpError = false
        var errorMessage: String? = null


        if (state.otp.isBlank()) {
            otpError = true
            errorMessage = "otp is required"

        }

        _state.update {
            it.copy(
                otpError = otpError,
            )
        }
        return errorMessage
    }


    fun otpVerification(email: String) {
        val validationError = validateForm()
        if (validationError != null) {
            _state.update { it.copy(errorMessage = validationError) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = repository.otpVerification(email, _state.value.otp)) {
                is ApiResult.Success -> {
                    preferencesManager.saveResetToken(result.data.resetPasswordToken)
                    _state.update { it.copy(isLoading = false, isSuccess = true) }
                }
                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false, errorMessage = result.message) }
                }
                is ApiResult.Exception -> {
                    _state.update { it.copy(isLoading = false, errorMessage = result.throwable.message) }
                }
            }
        }
    }}