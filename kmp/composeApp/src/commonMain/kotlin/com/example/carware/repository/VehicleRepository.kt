package com.example.carware.repository

import com.example.carware.cache.vehiclesStore
import com.example.carware.network.api.addVehicles
import com.example.carware.network.api.deleteVehicle
import com.example.carware.network.api.getAppointments
import com.example.carware.network.api.getBrands
import com.example.carware.network.api.getModels
import com.example.carware.network.api.getVehicle
import com.example.carware.network.api.getVehicles
import com.example.carware.network.api.updateVehicle
import com.example.carware.network.apiRequests.vehicle.UpdateVehicleRequest
import com.example.carware.network.apiRequests.vehicle.VehicleRequest
import com.example.carware.network.apiResponse.appointment.Appointments
import com.example.carware.network.apiResponse.vehicle.Brand
import com.example.carware.network.apiResponse.vehicle.DeleteVehicleResponse
import com.example.carware.network.apiResponse.vehicle.Model
import com.example.carware.network.apiResponse.vehicle.UpdateVehicleResponse
import com.example.carware.network.apiResponse.vehicle.Vehicle
import com.example.carware.network.apiResponse.vehicle.VehicleResponse
import com.example.carware.network.apiResponse.vehicle.Vehicles
import com.example.carware.network.cache.VehiclesCacheData
import com.example.carware.network.core.ApiResult
import com.example.carware.util.storage.PreferencesManager
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow

class VehicleRepository(
    private val client: HttpClient
) {

    suspend fun getBrandsRepo(): List<Brand> {
        return when (val result = getBrands(client = client)) {
            is ApiResult.Success -> result.data.data
            is ApiResult.Error -> emptyList()
            is ApiResult.Exception -> emptyList()
        }
    }

    suspend fun getVehiclesRepo(): List<Vehicles> {
        return when (val result = getVehicles(client)) {
            is ApiResult.Success -> {
                val vehicles = result.data.data ?: emptyList()
                vehiclesStore.set(
                    VehiclesCacheData(vehicles = vehicles, userId = 0)
                )
                vehicles
            }

            is ApiResult.Error -> {
                val cached = vehiclesStore.get()
                cached?.vehicles ?: emptyList()
            }

            is ApiResult.Exception -> {
                val cached = vehiclesStore.get()
                cached?.vehicles ?: emptyList()
            }
        }
    }

    suspend fun getVehicleRepo(id: Int): Vehicle {
        return when (val result = getVehicle(client, id)) {
            is ApiResult.Success -> result.data.data
            is ApiResult.Error -> throw Exception("Get vehicle failed: ${result.message}")
            is ApiResult.Exception -> throw Exception("Get vehicle error: ${result.throwable.message}")
        }
    }

    suspend fun getModelsRepo(brandId: Int): List<Model> {
        return when (val result = getModels(brandId, client)) {
            is ApiResult.Success -> result.data.data
            is ApiResult.Error -> emptyList()
            is ApiResult.Exception -> emptyList()
        }
    }

    suspend fun addVehicleRepo(
        vehicleRequest: VehicleRequest,
    ): VehicleResponse {
        return when (val result = addVehicles(vehicleRequest, client)) {
            is ApiResult.Success -> result.data
            is ApiResult.Error -> throw Exception("Add vehicle failed: ${result.message}")
            is ApiResult.Exception -> throw Exception("Add vehicle error: ${result.throwable.message}")
        }
    }

    suspend fun getAppointmentsRepo(): List<Appointments> {
        return when (val result = getAppointments(client)) {
            is ApiResult.Success -> result.data.data ?: emptyList()
            is ApiResult.Error -> emptyList()
            is ApiResult.Exception -> emptyList()
        }
    }

    suspend fun deleteVehicleRepo(id: Int): DeleteVehicleResponse {
        return when (val result = deleteVehicle(client, id)) {
            is ApiResult.Success -> result.data
            is ApiResult.Error -> throw Exception("Delete vehicle failed: ${result.message}")
            is ApiResult.Exception -> throw Exception("Delete vehicle error: ${result.throwable.message}")
        }
    }

    suspend fun updateVehicleRepo(
        id: Int,
        updateVehicleRequest: UpdateVehicleRequest
    ): UpdateVehicleResponse {
        return when (val result = updateVehicle(client, id, updateVehicleRequest)) {
            is ApiResult.Success -> result.data
            is ApiResult.Error -> throw Exception("Update vehicle failed: ${result.message}")
            is ApiResult.Exception -> throw Exception("Update vehicle error: ${result.throwable.message}")
        }
    }
}




