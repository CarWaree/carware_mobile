package com.example.carware.repository

import com.example.carware.cache.appointmentsStore
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
import com.example.carware.network.cacheData.AppointmentsCachedData
import com.example.carware.network.cacheData.VehiclesCacheData
import com.example.carware.util.storage.PreferencesManager
import kotlin.collections.emptyList

class VehicleRepository(private val prefs: PreferencesManager) {

    suspend fun getBrandsRepo(): List<Brand> {
        return getBrands() // your existing top-level function
    }

    suspend fun getVehiclesRepo(): List<Vehicles> {
        val token = prefs.getToken() ?: ""
        if (token.isEmpty()) return emptyList()

        return try {
            val response = getVehicles(token)
            val vehicles = response.data

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

    suspend fun getAppointmentsRepo(): List<Appointments> {
        // 1. Get token from SharedPrefs
        val token = prefs.getToken() ?: ""
        if (token.isEmpty()) return emptyList()

        return try {
            // 2. Call API
            val response = getAppointments(token)
            val appointments = response.data

            appointmentsStore.set(
                AppointmentsCachedData(appointments = appointments, userId = 0)
            )

            appointments

            // 3. Return only the list of data
        } catch (e: Exception) {
            // CHANGED: get() instead of read()
            val cached = appointmentsStore.get()
            cached?.appointments ?: emptyList()
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




