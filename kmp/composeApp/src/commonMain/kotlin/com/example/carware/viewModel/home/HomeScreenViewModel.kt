package com.example.carware.viewModel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carware.cache.appointmentsStore
import com.example.carware.cache.vehiclesStore
import com.example.carware.network.apiResponse.appointment.Appointments
import com.example.carware.repository.VehicleRepository
import com.example.carware.util.storage.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class HomeScreenViewModel(
    val repository: VehicleRepository,
    private val preferencesManager: PreferencesManager

) : ViewModel() {


    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()

    val cachedVehicles = vehiclesStore.updates
        .filterNotNull()
        .map { it.vehicles }

    val cachedAppointments = appointmentsStore.updates
        .filterNotNull()
        .map { it.appointments }


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
                val appointmentsList=repository.getAppointmentsRepo()

                if (vehicleList.isEmpty()) {
                    _state.value = HomeScreenState.Error("No vehicles found.")
                } else {
                    _state.value = HomeScreenState.Success(vehicleList,appointmentsList)
                }
            } catch (e: Exception) {
                _state.value = HomeScreenState.Error(e.message ?: "An error occurred")
            }
        }
    }
}