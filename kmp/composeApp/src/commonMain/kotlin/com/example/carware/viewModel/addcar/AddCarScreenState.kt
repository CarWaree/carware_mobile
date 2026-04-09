package com.example.carware.viewModel.addcar

data class AddCarScreenState(
    val selectedBrand: String? = null,
    val selectedModel: String? = null,

    val selectedBrandId: Int? = null,
    val selectedModelId: Int? = null,

    val selectedColor: String? = null,
    val selectedYear: Int? = null,



    val availableBrands: List<String> = emptyList(),
    val availableModels: List<String> = emptyList(),
    val availableColors: List<String> = listOf("Red","Blue","Black","Silver","White","Gray"),
    val availableYears: List<Int> = (1970..2026).toList(),
    val isSaveButtonEnabled: Boolean = false,
    val isSelectedCar: Boolean =false
)
