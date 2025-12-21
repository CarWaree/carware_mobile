package com.example.carware.viewModel.schedule

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carware.network.apiResponse.schedule.Centers
import com.example.carware.network.apiResponse.schedule.Service
import com.example.carware.network.apiResponse.vehicle.Brand
import com.example.carware.repository.ServiceRepository
import com.example.carware.repository.VehicleRepository
import com.example.carware.viewModel.addcar.AddCarScreenState
import com.example.carware.viewModel.home.HomeScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScheduleScreenViewModel(
    val repository: ServiceRepository,
    private val vehicleRepository: VehicleRepository
) : ViewModel() {

    var serviceTypes by mutableStateOf<List<String>>(emptyList())

    private val _state = MutableStateFlow(ScheduleScreenState())
    val state: StateFlow<ScheduleScreenState> = _state.asStateFlow()


    init {
        viewModelScope.launch {
            try {

                val services = repository.getServiceTypeRepo()
                val centers = repository.getServiceCentersRepo()
                val cars = vehicleRepository.getVehiclesRepo()

                _state.update { it.copy(
                    availableServicesTypes = services,
                    availableCenters = centers,
                    availableCars = cars
                )}
            } catch (e: Exception) {
                println("Fetch failed: ${e.message}")
            }
        }
    }
    fun selectServiceType(serviceName: String) {
        _state.update { it.copy(selectedService = serviceName) }
    }
    fun selectCenter(center: Centers) {
        _state.update { it.copy(selectedCenter = center.name) }
    }

    // Inside ScheduleScreenViewModel.kt

    fun selectVehicle(carId: Int) {
        // 1. Get the current list from the current state value
        val currentCars = _state.value.availableCars

        // 2. Find the car
        val car = currentCars.find { it.id == carId }

        if (car != null) {
            // 3. Use .update to push the new state
            _state.update { currentState ->
                currentState.copy(
                    selectedCarId = car.id,
                    selectedCarUserId = car.userName // Ensure this field exists in your Vehicles model
                )
            }
            println("SUCCESS: Selected ${car.brandName} with ID: ${car.id}")
        } else {
            println("ERROR: Could not find car with ID $carId in the list of ${currentCars.size} cars")
        }
    }
}