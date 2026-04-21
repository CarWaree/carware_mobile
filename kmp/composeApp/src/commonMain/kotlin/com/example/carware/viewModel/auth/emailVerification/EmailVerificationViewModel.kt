package com.example.carware.viewModel.auth.emailVerification

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carware.network.apiRequests.auth.EmailVerificationRequest
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
            try {
                _state.update { it.copy(isLoading = true, errorMessage = null) }
                val request = EmailVerificationRequest(
                    email = email,
                    otp = _state.value.otp
                )
                val response = repository.verifyEmailRepo(request)

                val accessToken = response.data?.accessToken
                    ?: throw IllegalStateException("accessToken missing in response")
                preferencesManager.saveToken(accessToken)

                val refreshToken = response.data?.refreshToken
                    ?: throw IllegalStateException(" refresh    Token missing in response")
                preferencesManager.saveRefreshToken(refreshToken)

//                val expToken=response.data.expiresOn
//                preferencesManager.saveExpiresOn(expToken)
                preferencesManager.saveEmailVerified(true)

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