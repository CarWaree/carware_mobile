package com.example.carware.viewModel.vehicle.addcar

data class AddCarScreenState(
    val selectedBrand: String? = null,
    val selectedModel: String? = null,

    val selectedBrandId: Int? = null,
    val selectedModelId: Int? = null,

    val selectedColor: String? = null,
    val selectedYear: Int? = null,
    val carId: Int? = null,


    val availableBrands: List<String> = emptyList(),
    val availableModels: List<String> = emptyList(),
    val availableColors: List<String> = listOf("Red", "Blue", "Black", "Silver", "White", "Gray"),
    val availableYears: List<Int> = (2026 downTo 1970).toList(),
    val isSaveButtonEnabled: Boolean = false,
    val isSelectedCar: Boolean = false,

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)
