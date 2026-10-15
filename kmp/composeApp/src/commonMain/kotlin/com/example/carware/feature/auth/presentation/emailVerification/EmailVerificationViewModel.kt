package com.example.carware.feature.auth.presentation.emailVerification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carware.core.network.ApiResult
import com.example.carware.feature.auth.data.remote.emailVerification.EmailVerificationRequest
import com.example.carware.feature.auth.data.remote.emailVerification.EmailVerificationResponse
import com.example.carware.core.network.UiResult
import com.example.carware.core.storage.PreferencesManager
import com.example.carware.feature.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EmailVerificationViewModel
    (
    private val repository: AuthRepository,
    private val preferencesManager: PreferencesManager,

    ) : ViewModel() {
    private val _state = MutableStateFlow(EmailVerificationState())
    val state: StateFlow<EmailVerificationState> = _state.asStateFlow()

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

    fun emailVerification(email: String) {
        val validationError = validateForm()
        if (validationError != null) {
            _state.update { it.copy(errorMessage = validationError) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            // 1. Repository already maps response DTO to EmailVerificationResult domain model
            when (val result = repository.verifyEmail(email, _state.value.otp)) {
                is ApiResult.Success -> {
                    val verificationResult = result.data

                    // 2. Save non-null properties directly from EmailVerificationResult domain object
                    preferencesManager.saveToken(verificationResult.accessToken)
                    preferencesManager.saveRefreshToken(verificationResult.refreshToken)
                    preferencesManager.saveEmailVerified(true)

                    _state.update {
                        it.copy(isLoading = false, isSuccess = true)
                    }
                }

                is ApiResult.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }

                is ApiResult.Exception<*> -> TODO()
            }
        }
    }
}