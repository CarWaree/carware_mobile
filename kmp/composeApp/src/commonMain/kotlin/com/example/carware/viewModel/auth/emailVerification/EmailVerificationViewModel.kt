package com.example.carware.viewModel.auth.emailVerification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carware.network.apiRequests.auth.EmailVerificationRequest
import com.example.carware.network.apiResponse.auth.EmailVerificationResponse
import com.example.carware.network.core.UiResult
import com.example.carware.repository.auth.AuthRepository
import com.example.carware.util.storage.PreferencesManager
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
            val request = EmailVerificationRequest(
                email = email,
                otp = _state.value.otp
            )

            when (val result: UiResult<EmailVerificationResponse> =
                repository.verifyEmailRepo(request)) {
                is UiResult.Success -> {
                    val response = result.data
                    val accessToken = response.data?.accessToken
                    val refreshToken = response.data?.refreshToken

                    if (accessToken == null || refreshToken == null) {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = "Token missing from server response"
                            )
                        }
                        return@launch
                    }

                    preferencesManager.saveToken(accessToken)
                    preferencesManager.saveRefreshToken(refreshToken)
                    preferencesManager.saveEmailVerified(true)

                    _state.update {
                        it.copy(isLoading = false, isSuccess = true)
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

}