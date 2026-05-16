package com.example.carware.viewModel.auth.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carware.network.apiRequests.auth.GoogleSignInRequest
import com.example.carware.network.apiRequests.auth.SignUpRequest
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

class SignUpViewModel(
    private val repository: AuthRepository,
    private val vehicleRepository: VehicleRepository,
    private val preferencesManager: PreferencesManager,
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpState())

    val state: StateFlow<SignUpState> = _state.asStateFlow()

    fun onFirstNameChange(value: String) = _state.update { it.copy(firstName = value) }
    fun onLastNameChange(value: String) = _state.update { it.copy(lastName = value) }
    fun onUserNameChange(value: String) = _state.update { it.copy(userName = value) }
    fun onEmailChange(value: String) = _state.update { it.copy(email = value) }
    fun onPasswordChange(value: String) = _state.update { it.copy(pass = value) }

    fun onConfirmPasswordChange(value: String) =
        _state.update { it.copy(confPass = value, confPassError = false) }

    fun onToggleAgree(value: Boolean) =
        _state.update { it.copy(agreeTerms = value, agreedError = false) }

    fun clearErrorMessage() = _state.update { it.copy(errorMessage = null) }

    private fun validateForm(): String? {
        val state = _state.value
        var firstNameError = false
        var lastNameError = false
        var userNameError = false
        var emailError = false
        var passError = false
        var confPassError = false
        var agreedError = false
        var errorMessage: String? = null

        // Check each field and set specific error
        if (state.lastName.isBlank()) {
            lastNameError = true
            errorMessage = "First Name is required"
        } else if (state.firstName.isBlank()) {
            firstNameError = true
            errorMessage = "First Name is required"
        } else if (state.userName.isBlank()) {
            userNameError = true
            errorMessage = "Username is required"
        } else if (state.email.isBlank()) {
            emailError = true
            errorMessage = "Email is required"
        } else if (state.pass.isBlank()) {
            passError = true
            errorMessage = "Password is required"
        } else if (state.confPass.isBlank()) {
            confPassError = true
            errorMessage = "Confirm password is required"
        } else if (state.pass != state.confPass) {
            confPassError = true
            errorMessage = "Passwords do not match"
        } else if (!state.agreeTerms) {
            agreedError = true
            errorMessage = "You must agree to the terms"
        }

        _state.update {
            it.copy(
                firstNameError = firstNameError,
                lastNameError = lastNameError,
                userNameError = userNameError,
                emailError = emailError,
                passError = passError,
                confPassError = confPassError,
                agreedError = agreedError
            )
        }

        return errorMessage
    }

    fun signup() {
        // Validate first
        val validationError = validateForm()
        if (validationError != null) {
            _state.update { it.copy(errorMessage = validationError) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            val request = SignUpRequest(
                firstName = _state.value.firstName,
                lastName = _state.value.lastName,
                userName = _state.value.userName,
                email = _state.value.email,
                password = _state.value.pass,
                confirmPassword = _state.value.confPass,
            )

            when (val result: UiResult<SignUpResponse> = repository.signUpRepo(request)) {
                is UiResult.Success -> {
                    val response = result.data
                    val isEmailVerified = response.data?.isEmailVerified ?: false
                    preferencesManager.saveEmailVerified(isEmailVerified)

                    val vehicles = vehicleRepository.getVehiclesRepo()
                    val hasAddedCar = vehicles.isNotEmpty()
                    preferencesManager.setCarAdded(hasAddedCar)

                    _state.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = isEmailVerified,
                            needsEmailVerification = !isEmailVerified,
                            isCarAdded = hasAddedCar
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

            when (val result: UiResult<GoogleSignInResponse> = repository.googleSignInRepo(request)) {
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
