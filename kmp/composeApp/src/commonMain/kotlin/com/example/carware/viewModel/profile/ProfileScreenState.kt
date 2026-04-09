package com.example.carware.viewModel.profile

import com.example.carware.network.apiResponse.appointment.Appointments
import com.example.carware.network.apiResponse.profile.ProfileDetails
import com.example.carware.network.apiResponse.vehicle.Vehicles
import com.example.carware.viewModel.home.HomeScreenState

sealed class ProfileScreenState {
    data object Loading : ProfileScreenState()
    data class Success(val profile: ProfileDetails,val cars: List<Vehicles>) : ProfileScreenState()
    data class Error(val message: String) : ProfileScreenState()
}
data class EditProfileState(
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",

    val errorMessage: String? = null,

    val fullNameError: Boolean = false,
    val emailError: Boolean = false,
    val phoneError: Boolean = false,
)