package com.example.carware.repository

import com.example.carware.network.api.addVehicles
import com.example.carware.network.api.getBrands
import com.example.carware.network.api.getModels
import com.example.carware.network.api.getVehicles
import com.example.carware.network.apiRequests.vehicle.VehicleRequest
import com.example.carware.network.apiResponse.vehicle.Brand
import com.example.carware.network.apiResponse.vehicle.Model
import com.example.carware.network.apiResponse.vehicle.VehicleResponse
import com.example.carware.network.apiResponse.vehicle.Vehicles
import com.example.carware.util.storage.PreferencesManager

class VehicleRepository(private val prefs: PreferencesManager) {

    suspend fun getBrandsRepo(): List<Brand> {
        return getBrands() // your existing top-level function
    }

    suspend fun getVehiclesRepo(): List<Vehicles> {
        // 1. Get token from SharedPrefs
        val token = prefs.getToken() ?: ""

        if (token.isEmpty()) return emptyList()

        return try {
            // 2. Call API
            val response = getVehicles(token)

            // 3. Return only the list of data
            response.data ?: emptyList()
        } catch (e: Exception) {
            emptyList() // Return empty on error to prevent UI crashes
        }
    }

    suspend fun getModelsRepo(brandId: Int): List<Model> {
        return getModels(brandId) // your existing top-level function
    }

    suspend fun addVehicleRepo(
        vehicleRequest: VehicleRequest,
        token: String  // Add token parameter
    ): VehicleResponse = addVehicles(vehicleRequest, token)

}

