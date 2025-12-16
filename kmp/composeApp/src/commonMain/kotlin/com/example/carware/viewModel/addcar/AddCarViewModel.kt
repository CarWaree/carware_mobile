package com.example.carware.viewModel.addcar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.carware.navigation.AddCarScreen
import com.example.carware.navigation.HomeScreen
import com.example.carware.network.apiRequests.vehicle.VehicleRequest
import com.example.carware.network.apiResponse.vehicle.Brand
import com.example.carware.network.apiResponse.vehicle.Model
import com.example.carware.repository.VehicleRepository
import com.example.carware.util.storage.PreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddCarViewModel(val repository: VehicleRepository, preferencesManager: PreferencesManager) : ViewModel() {
    var brands by mutableStateOf<List<Brand>>(emptyList())
    var models: List<Model> = emptyList()
    val availableYears: List<Int> = (1990..2100).toList()
    val colors = listOf("Red", "Blue", "Black", "White", "Silver")  // predefined colors
    val preferencesManager = preferencesManager
    private val _state = MutableStateFlow(AddCarScreenState())
    val state: StateFlow<AddCarScreenState> = _state


    init {
        viewModelScope.launch {
            val brandsFromApi = repository.getBrandsRepo()
            brands = brandsFromApi
            _state.update { it.copy(availableBrands = brandsFromApi.map { it.name }) }
        }
    }

    fun selectBrand(brand: Brand) {
        _state.update { current ->

            val newState = current.copy(
                selectedBrandId = brand.id,
                selectedBrand = brand.name,
                selectedModelId = null,
                selectedModel = null,
                availableModels = emptyList(), // reset models
                isSaveButtonEnabled = checkSaveButtonEnabled()
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

    private fun checkSaveButtonEnabled(): Boolean {
        val s = _state.value
        return s.selectedBrandId != null &&
                s.selectedModelId != null &&
                !s.selectedColor.isNullOrEmpty() &&
                s.selectedYear != null
    }

    fun addVehicle(navController: NavController) {
        val brandId = _state.value.selectedBrandId
        val modelId = _state.value.selectedModelId
        val year = _state.value.selectedYear
        val color = _state.value.selectedColor

        // Ensure all fields are selected
        if (brandId == null || modelId == null || year == null || color.isNullOrEmpty()) return

        viewModelScope.launch {
            val request = VehicleRequest(
                brandId = brandId,
                modelId = modelId,
                year = year,
                color = color,
            )

            try {
                // Get token
                val token = preferencesManager.getToken()
                    ?: throw Exception("User not logged in")

                // Call repository with token
                val response = repository.addVehicleRepo(request, token)

                // Mark car as added
                preferencesManager.setCarAdded(true)

                println("Vehicle added successfully: ${response.message}")

                withContext(Dispatchers.Main) {
                    navController.navigate(HomeScreen) {
                        popUpTo(AddCarScreen) { inclusive = true }
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    println("Failed to add vehicle: ${e.message}")
                    // Show error to user (Snackbar/Toast)
                }
            }
        }
    }
}


