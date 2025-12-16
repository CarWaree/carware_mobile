package com.example.carware.viewModel.HomeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carware.network.apiResponse.vehicle.Vehicle
import com.example.carware.network.apiResponse.vehicle.VehicleResponse
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

    private fun loadVehicles() {
        viewModelScope.launch {
            _state.value = HomeScreenState.Loading
            try {
                // Get token
                val token = preferencesManager.getToken()
                    ?: throw Exception("User not logged in")

                // Pass token to repository
                val vehicleList = repository.getVehicleRepo(token)

                val firstVehicle = vehicleList.firstOrNull()

                if (firstVehicle == null) {
                    _state.value = HomeScreenState.Error("No vehicles found for this user.")
                    return@launch
                }

                val carForUi = Car(
                    id = firstVehicle.id,
                    brandName = firstVehicle.brandName,
                    modelName = firstVehicle.modelName,
                    year = firstVehicle.year,
                    color = firstVehicle.color,
                    userName = firstVehicle.userName
                )
                _state.value = HomeScreenState.Success(carForUi)

            } catch (e: Exception) {
                println("loading failed: ${e.message}")
                _state.value = HomeScreenState.Error("Failed to load car data: ${e.message}")
            }
        }
    }}