package com.example.carware.viewModel.HomeScreen

    data class Car(
        val id: Int,
        val brandName: String,
        val modelName: String,
        val year: Int,
        val color: String,
        val userName: String
    )

sealed class HomeScreenState {
    data object Loading : HomeScreenState()
    data class Success(val car: Car) : HomeScreenState()
    data class Error(val message: String) : HomeScreenState()
}