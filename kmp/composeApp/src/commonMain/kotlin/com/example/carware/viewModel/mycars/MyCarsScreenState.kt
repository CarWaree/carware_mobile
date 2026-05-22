package com.example.carware.viewModel.mycars

import com.example.carware.network.apiResponse.profile.ProfileDetails
import com.example.carware.network.apiResponse.vehicle.Vehicles

sealed class MyCarsScreenState {
    data object Loading : MyCarsScreenState()
    data class Success(val cars: List<Vehicles>) : MyCarsScreenState()
    data class Error(val message: String) : MyCarsScreenState()
}
