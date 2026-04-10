package com.example.carware.viewModel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carware.cache.vehiclesStore
import com.example.carware.repository.VehicleRepository
import com.example.carware.util.storage.PreferencesManager
import com.example.carware.viewModel.profile.ProfileScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch



class HomeScreenViewModel(
    private val repository: VehicleRepository,

) : ViewModel() {


    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()

    val cachedVehicles = vehiclesStore.updates
        .filterNotNull()
        .map { it.vehicles }

    init {
        loadVehicles()
    }


    fun loadVehicles() {
        viewModelScope.launch {
            if (_state.value !is HomeScreenState.Success) {
                _state.value = HomeScreenState.Loading
            }
            try {
                val vehicleList = repository.getVehiclesRepo()
                val appointmentsList = repository.getAppointmentsRepo()

                if (vehicleList.isEmpty()) {
                    _state.value = HomeScreenState.Error("No vehicles found.")
                } else {
                    _state.value = HomeScreenState.Success(vehicleList, appointmentsList)
                }
            } catch (e: Exception) {
                // ✅ Try cache before showing error
                val cached = vehiclesStore.get()
                if (cached != null && cached.vehicles.isNotEmpty()) {
                    _state.value = HomeScreenState.Success(
                        cached.vehicles,
                        emptyList() // no appointments cached
                    )
                } else {
                    _state.value = HomeScreenState.Error("No internet connection")
                }
            }
        }
    }
}