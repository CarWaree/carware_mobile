package com.example.carware.viewModel.vehicle.editCar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.carware.network.apiRequests.vehicle.UpdateVehicleRequest
import com.example.carware.network.apiResponse.vehicle.Brand
import com.example.carware.network.apiResponse.vehicle.Model
import com.example.carware.network.apiResponse.vehicle.Vehicles
import com.example.carware.repository.VehicleRepository
import com.example.carware.screens.vehicle.EditCarScreen
import com.example.carware.util.storage.PreferencesManager
import com.example.carware.viewModel.vehicle.addcar.AddCarScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditCarViewModel(
    private val repository: VehicleRepository,
) : ViewModel() {

    var brands by mutableStateOf<List<Brand>>(emptyList())
    var models: List<Model> = emptyList()
    val availableYears: List<Int> = (1990..2100).toList()
    val colors = listOf("Red", "Blue", "Black", "White", "Silver")

    private val _state = MutableStateFlow(AddCarScreenState())
    val state: StateFlow<AddCarScreenState> = _state
    fun selectBrand(brand: Brand) {
        _state.update { current ->

            val newState = current.copy(
                selectedBrandId = brand.id,
                selectedBrand = brand.name,
                selectedModelId = null,
                selectedModel = null,
                availableModels = emptyList(), // reset models
            )
            newState.copy(
                isSaveButtonEnabled = newState.selectedBrandId != null &&
                        newState.selectedModelId != null &&
                        !newState.selectedColor.isNullOrEmpty() &&
                        newState.selectedYear != null
            )

        }
        viewModelScope.launch {
            val modelsFromApi = repository.getModelsRepo(brand.id)
            models = modelsFromApi
            _state.update { it.copy(availableModels = modelsFromApi.map { it.name }) }
        }
    }

    fun selectModel(model: Model) {
        _state.update { current ->
            val newState = current.copy(
                selectedModelId = model.id,
                selectedModel = model.name
            )
            newState.copy(
                isSaveButtonEnabled = newState.selectedBrandId != null &&
                        newState.selectedModelId != null &&
                        !newState.selectedColor.isNullOrEmpty() &&
                        newState.selectedYear != null
            )
        }
    }

    fun selectColor(color: String) {
        _state.update { current ->
            val newState = current.copy(
                selectedColor = color
            )
            newState.copy(
                isSaveButtonEnabled = newState.selectedBrandId != null &&
                        newState.selectedModelId != null &&
                        !newState.selectedColor.isNullOrEmpty() &&
                        newState.selectedYear != null
            )
        }

    }

    fun selectYear(year: Int) {
        _state.update { current ->
            val newState = current.copy(
                selectedYear = year
            )
            newState.copy(
                isSaveButtonEnabled = newState.selectedBrandId != null &&
                        newState.selectedModelId != null &&
                        !newState.selectedColor.isNullOrEmpty() &&
                        newState.selectedYear != null
            )
        }
    }


    fun loadCar(carId: Int) {
        viewModelScope.launch {

            val car = repository.getVehicleRepo(carId)  // your new endpoint


            val brandsFromApi = repository.getBrandsRepo()
            brands = brandsFromApi

            val matchedBrand = brandsFromApi.firstOrNull { it.name == car.brandName }
            val modelsFromApi = matchedBrand?.let { repository.getModelsRepo(it.id) } ?: emptyList()
            models = modelsFromApi

            _state.update {
                it.copy(
                    carId = car.id,
                    availableBrands = brandsFromApi.map { it.name },
                    availableModels = modelsFromApi.map { it.name },
                    selectedBrand = car.brandName,
                    selectedBrandId = matchedBrand?.id,
                    selectedModel = car.modelName,
                    selectedModelId = modelsFromApi.firstOrNull { it.name == car.modelName }?.id,
                    selectedYear = car.year,
                    selectedColor = car.color,
                    isSaveButtonEnabled = true
                )
            }
        }
    }

    fun updateVehicle( navController: NavController) {

        val carId = _state.value.carId ?: return
        val brandId = _state.value.selectedBrandId
        val modelId = _state.value.selectedModelId
        val year = _state.value.selectedYear
        val color = _state.value.selectedColor

        if (brandId == null || modelId == null || year == null || color.isNullOrEmpty()) return

        viewModelScope.launch {
            try {
                repository.updateVehicleRepo(
                    id = carId,
                    UpdateVehicleRequest(
                        id = carId,
                        brandName = _state.value.selectedBrand ?: "",
                        modelName = _state.value.selectedModel ?: "",
                        year = year,
                        color = color
                    )
                )
                withContext(Dispatchers.Main) {
                    navController.popBackStack() // go back to home
                }
            } catch (e: Exception) {
                println("Failed to update vehicle: ${e.message}")
            }
        }
    }
}