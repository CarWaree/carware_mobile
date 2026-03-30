package com.example.carware.viewModel.history

import com.example.carware.network.apiResponse.vehicle.Vehicles

sealed class HistoryScreenState {

        data object Loading : HistoryScreenState()
        data class Success(val cars: List<Vehicles>) : HistoryScreenState()
        data class Error(val message: String) : HistoryScreenState()

}