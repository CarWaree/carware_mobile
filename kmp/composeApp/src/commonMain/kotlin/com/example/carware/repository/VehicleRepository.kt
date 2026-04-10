package com.example.carware.repository

import com.example.carware.cache.vehiclesStore
import com.example.carware.network.api.addVehicles
import com.example.carware.network.api.getAppointments
import com.example.carware.network.api.getBrands
import com.example.carware.network.api.getModels
import com.example.carware.network.api.getVehicles
import com.example.carware.network.apiRequests.vehicle.VehicleRequest
import com.example.carware.network.apiResponse.appointment.Appointments
import com.example.carware.network.apiResponse.vehicle.Brand
import com.example.carware.network.apiResponse.vehicle.Model
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
        // 1. Get token from SharedPrefs


        return try {
            // 2. Call API
            val response = getAppointments(client)

            // 3. Return only the list of data
            response.data ?: emptyList()
        } catch (e: Exception) {
            emptyList() // Return empty on error to prevent UI crashes
        }
    }
}




