package com.example.carware.repository

import com.example.carware.network.api.getServiceCenters
import com.example.carware.network.api.getServiceType
import com.example.carware.network.api.setAppointment
import com.example.carware.network.apiRequests.schedule.SetAppointmentRequest
import com.example.carware.network.apiResponse.appointment.AppointmentResponse
import com.example.carware.network.apiResponse.schedule.Centers
import com.example.carware.network.apiResponse.schedule.Service
import com.example.carware.network.core.ApiResult
import io.ktor.client.HttpClient

class ServiceRepository(private val client: HttpClient) {

    suspend fun getServiceTypeRepo(): List<Service> {
        return when (val result = getServiceType(client)) {
            is ApiResult.Success -> {
                result.data.data  // ServiceTypesResponse has .data property with List<Service>
            }

            is ApiResult.Error -> {
                throw Exception("Get service types failed: ${result.message}")
            }

            is ApiResult.Exception -> {
                throw Exception("Get service types error: ${result.throwable.message}")
            }
        }
    }

    suspend fun getServiceCentersRepo(): List<Centers> {
        return when (val result = getServiceCenters(client)) {
            is ApiResult.Success -> {
                result.data  // ServiceTypesResponse has .data property with List<Service>
            }

            is ApiResult.Error -> {
                throw Exception("Get service centers failed: ${result.message}")
            }

            is ApiResult.Exception -> {
                throw Exception("Get service centers error: ${result.throwable.message}")
            }
        }
    }

    suspend fun setAppointmentRepo(request: SetAppointmentRequest): AppointmentResponse {
        return when (val result = setAppointment(request, client)) {

            is ApiResult.Success -> {
                result.data
            }

            is ApiResult.Error -> {
                throw Exception("Set Appointment failed: ${result.message}")
            }

            is ApiResult.Exception -> {
                throw Exception(" Set Appointment error: ${result.throwable.message}")
            }
        }
    }

}