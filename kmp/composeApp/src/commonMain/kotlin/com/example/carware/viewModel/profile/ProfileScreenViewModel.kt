package com.example.carware.viewModel.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carware.network.apiRequests.profile.UpdateProfileRequest
import com.example.carware.network.apiResponse.profile.GetProfileResponse
import com.example.carware.network.cache.VehiclesCacheData
import com.example.carware.repository.ProfileRepository
import com.example.carware.repository.VehicleRepository
import com.example.carware.util.storage.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileScreenViewModel(
    private val vehiclesRepository: VehicleRepository,

    private val repository: ProfileRepository
) : ViewModel() {
    private val _state = MutableStateFlow<ProfileScreenState>(ProfileScreenState.Loading)
    val state: StateFlow<ProfileScreenState> = _state.asStateFlow()

    private val _editState = MutableStateFlow(EditProfileState())
    val editState: StateFlow<EditProfileState> = _editState

    init {
        loadProfile()
    }
    fun onFullNameChange(value: String) {
        _editState.value = _editState.value.copy(fullName = value)
    }

    fun onEmailChange(value: String) {
        _editState.value = _editState.value.copy(email = value)
    }

    fun onPhoneChange(value: String) {
        _editState.value = _editState.value.copy(phone = value)
    }

    fun loadEditState() {
        val current = _state.value as? ProfileScreenState.Success ?: return
        current.profile.phoneNumber?.let {
            _editState.value = _editState.value.copy(
                fullName = current.profile.fullName,
                email = current.profile.email,
                phone = it
            )
        }
    }

    fun saveProfile() {
        viewModelScope.launch {
            try {
                repository.updateProfileRepo(
                    UpdateProfileRequest(
                        fullName = _editState.value.fullName,
                        phoneNumber = _editState.value.phone,
                        pendingEmail = _editState.value.email
                    )
                )

                loadProfile() // refresh after save
            } catch (e: Exception) {
                _editState.value = _editState.value.copy(
                    errorMessage = e.message ?: "Unknown error: ${e::class.simpleName}"
                )
            }
        }
    }

    fun loadProfile() {
        viewModelScope.launch {
            if (_state.value !is ProfileScreenState.Success) {
                _state.value = ProfileScreenState.Loading
            }
            try {
                val profile = repository.getProfileRepo()
                val profileDetails = profile.data

                val vehicleList = vehiclesRepository.getVehiclesRepo()

                _state.value = ProfileScreenState.Success(profileDetails, vehicleList) // always set success

                loadEditState()

            } catch (e: Exception) {
                _state.value =
                    ProfileScreenState.Error(e.message ?: "Unknown error: ${e::class.simpleName}")
            }
        }
    }
}