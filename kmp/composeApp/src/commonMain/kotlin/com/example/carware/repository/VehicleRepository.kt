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
import com.example.carware.util.storage.PreferencesManager
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow

class VehicleRepository(
    private val client: HttpClient
) {

    suspend fun getBrandsRepo(): List<Brand> {
        return try {
            getBrands(client = client)
        } catch (e: Exception) {
            emptyList() // ✅ don't crash, return empty
        }
    }

    suspend fun getVehiclesRepo(): List<Vehicles> {


        return try {
            val response = getVehicles(client)
            val vehicles = response.data ?: emptyList()

            // NEW: Save to cache using set()
            vehiclesStore.set(
                VehiclesCacheData(vehicles = vehicles, userId = 0)
            )

            // Return fresh data
            vehicles

        } catch (e: Exception) {
            // CHANGED: get() instead of read()
            val cached = vehiclesStore.get()
            cached?.vehicles ?: emptyList()
        }
    }

    suspend fun getVehicleRepo(id: Int): Vehicle {
        return try {
            val response = getVehicle(client, id)
            response.data
        } catch (e: Exception) {
            println("Failed to get vehicle: ${e.message}")
            throw e
        }
    }

    suspend fun getModelsRepo(brandId: Int): List<Model> {
        return try {
            getModels(brandId, client)
        } catch (e: Exception) {
            emptyList() // ✅ don't crash, return empty
        }
    }

    suspend fun addVehicleRepo(
        vehicleRequest: VehicleRequest,
    ): VehicleResponse {
        return addVehicles(vehicleRequest, client)

    }


    suspend fun getAppointmentsRepo(): List<Appointments> {
        return try {
            val response = getAppointments(client)
            println("=== REPO: getAppointments success, count: ${response.data?.size}")
            response.data ?: emptyList()
        } catch (e: Exception) {
            println("=== REPO ERROR: ${e.message}")
            emptyList()
        }
    }

    suspend fun deleteVehicleRepo(id: Int): DeleteVehicleResponse {
        return deleteVehicle(client, id)
    }

    suspend fun updateVehicleRepo(
        id: Int,
        updateVehicleRequest: UpdateVehicleRequest
    ): UpdateVehicleResponse {
        return updateVehicle(client, id, updateVehicleRequest)
    }
}




