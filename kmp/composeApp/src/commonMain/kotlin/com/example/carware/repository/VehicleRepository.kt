package com.example.carware.repository

import com.example.carware.network.Api.addVehicles
import com.example.carware.network.Api.getBrands
import com.example.carware.network.Api.getModels
import com.example.carware.network.Api.getVehicle
import com.example.carware.network.apiRequests.vehicle.VehicleRequest
import com.example.carware.network.apiResponse.vehicle.Brand
import com.example.carware.network.apiResponse.vehicle.Model
import com.example.carware.network.apiResponse.vehicle.Vehicle
import com.example.carware.network.apiResponse.vehicle.VehicleResponse
import com.example.carware.network.apiResponse.vehicle.Vehicles
import kotlinx.coroutines.delay

class VehicleRepository {

    suspend fun getBrandsRepo(): List<Brand> {
        return getBrands() // your existing top-level function
    }

    suspend fun getVehicleRepo(token: String): List<Vehicles> {
        // Call the API function with token
        val responseWrapper = getVehicle(token)

        // Extract the list of vehicles
        val vehicleList = responseWrapper.data

        // Validation
        if (vehicleList.isEmpty()) {
            println("--- REPO DEBUG: Server message: ${responseWrapper.message}")
            throw NoSuchElementException("The user has no vehicles.")
        }

        return vehicleList
    }
    suspend fun getModelsRepo(brandId: Int): List<Model> {
        return getModels(brandId) // your existing top-level function
    }

    suspend fun addVehicleRepo(
        vehicleRequest: VehicleRequest,
        token: String  // Add token parameter
    ): VehicleResponse = addVehicles(vehicleRequest, token)

}

