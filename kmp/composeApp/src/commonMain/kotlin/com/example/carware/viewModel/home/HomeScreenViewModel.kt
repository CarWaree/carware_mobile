package com.example.carware.viewModel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carware.repository.VehicleRepository
import com.example.carware.util.storage.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class HomeScreenViewModel(
    val repository: VehicleRepository,
    private val preferencesManager: PreferencesManager

) : ViewModel() {


    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()

    init {
        loadVehicles()
    }

    // In ViewModel: loadVehicles()

    // In HomeViewModel.kt
    fun loadVehicles() {
        viewModelScope.launch {
            _state.value = HomeScreenState.Loading
            try {
                val vehicleList = repository.getVehiclesRepo()

                if (vehicleList.isEmpty()) {
                    _state.value = HomeScreenState.Error("No vehicles found.")
                } else {
                    _state.value = HomeScreenState.Success(vehicleList)
                }
            } catch (e: Exception) {
                _state.value = HomeScreenState.Error(e.message ?: "An error occurred")
            }
        }
    }
}