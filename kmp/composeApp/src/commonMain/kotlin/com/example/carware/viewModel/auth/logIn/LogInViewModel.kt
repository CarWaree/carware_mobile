package com.example.carware.viewModel.auth.logIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carware.network.apiRequests.auth.GoogleSignInRequest
import com.example.carware.network.apiRequests.auth.LoginRequest
import com.example.carware.network.apiResponse.auth.AuthResponse
import com.example.carware.network.apiResponse.auth.GoogleSignInResponse
import com.example.carware.network.apiResponse.auth.SignUpResponse
import com.example.carware.network.core.UiResult
import com.example.carware.repository.VehicleRepository
import com.example.carware.repository.auth.AuthRepository
import com.example.carware.util.storage.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LogInViewModel(
    private val repository: AuthRepository,
    private val vehicleRepository: VehicleRepository,
    private val preferencesManager: PreferencesManager,
) : ViewModel() {
    private val _state = MutableStateFlow(LogInState())
    val state: StateFlow<LogInState> = _state.asStateFlow()

    fun onEmailOrUsernameChange(value: String) = _state.update { it.copy(emailOrUsername = value) }
    fun onPasswordChange(value: String) = _state.update { it.copy(pass = value) }
    fun clearErrorMessage() = _state.update { it.copy(errorMessage = null) }


    private fun validateForm(): String? {
        val state = _state.value

        var emailOrUsernameError = false
        var passError = false
        var errorMessage: String? = null


        if (state.emailOrUsername.isBlank()) {
            emailOrUsernameError = true
            errorMessage = "email or username is required"

        } else if (state.pass.isBlank()) {
            passError = true
            errorMessage = "Password is required"
        }
        _state.update {
            it.copy(
                emailOrUsernameError = emailOrUsernameError,
                passError = passError
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

            val request = LoginRequest(
                emailOrUsername = _state.value.emailOrUsername,
                password = _state.value.pass

            )

            when (val result: UiResult<AuthResponse> = repository.logInRepo(request)) {
                is UiResult.Success -> {
                    val response = result.data
                    val token = response.data?.accessToken

                    if (token == null) {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = "Token missing from server response"
                            )
                        }
                        return@launch
                    }

                    preferencesManager.performLogin(token)
                    val expireToken = response.data.refreshTokenExpiration
                    preferencesManager.saveExpiresOn(expireToken)

                    val isEmailVerified = response.data?.isAuthenticated ?: false
                    preferencesManager.saveEmailVerified(isEmailVerified)

                    val vehicles = vehicleRepository.getVehiclesRepo()
                    val hasAddedCar = vehicles.isNotEmpty()
                    preferencesManager.setCarAdded(hasAddedCar)

                    _state.update {
                        it.copy(
                            isLoading = false,
                            isCarAdded = hasAddedCar,
                            isSuccess = true
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

    fun googleSignIn(idToken: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            val request = GoogleSignInRequest(idToken)

            when (val result: UiResult<GoogleSignInResponse> =
                repository.googleSignInRepo(request)) {
                is UiResult.Success -> {
                    val response = result.data
                    preferencesManager.performLogin(token = response.accessToken)
                    preferencesManager.saveEmailVerified(true)

                    val vehicles = vehicleRepository.getVehiclesRepo()
                    val hasAddedCar = vehicles.isNotEmpty()
                    preferencesManager.setCarAdded(hasAddedCar)

                    _state.update {
                        it.copy(
                            isLoading = false,
                            isCarAdded = hasAddedCar,
                            isSuccess = true
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

}

