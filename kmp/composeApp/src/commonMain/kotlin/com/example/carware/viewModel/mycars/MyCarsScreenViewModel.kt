package com.example.carware.viewModel.mycars

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carware.cache.vehiclesStore
import com.example.carware.repository.VehicleRepository
import com.example.carware.util.storage.PreferencesManager
import com.example.carware.viewModel.home.HomeScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class MyCarsScreenViewModel(
    private val vehiclesRepository: VehicleRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    private val _state = MutableStateFlow<MyCarsScreenState>(MyCarsScreenState.Loading)
        val state: StateFlow<MyCarsScreenState> = _state.asStateFlow()

    init {
        loadCars()
    }

    fun loadCars() {
        viewModelScope.launch {
            if (_state.value !is MyCarsScreenState.Success) {
                _state.value = MyCarsScreenState.Loading
            }
            try {
                val vehicleList = vehiclesRepository.getVehiclesRepo()
                _state.value = MyCarsScreenState.Success(vehicleList)
            } catch (e: Exception) {
                // Try cache before showing error
                val cached = vehiclesStore.get()
                if (cached != null && cached.vehicles.isNotEmpty()) {
                    _state.value = MyCarsScreenState.Success(cached.vehicles)
                } else {
                    _state.value = MyCarsScreenState.Error("No internet connection")
                }
            }
        }

         }

    fun setPrimaryVehicle(carId: Int) {
        preferencesManager.setPrimaryCarId(carId)
    }

    fun getPrimaryCarId(): Int {
        return preferencesManager.getPrimaryCarId()
    }



}
