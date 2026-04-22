package com.example.carware.viewModel.home

import com.example.carware.network.apiResponse.appointment.Appointments
import com.example.carware.network.apiResponse.vehicle.Vehicles


sealed class HomeScreenState {
    data object Loading : HomeScreenState()
    data class Success(val cars: List<Vehicles>,
                       val appointments:List<Appointments>
    ) : HomeScreenState()
    data class Error(val message: String) : HomeScreenState()
}