package com.example.carware.repository

import com.example.carware.network.api.getServiceCenters
import com.example.carware.network.api.getServiceType
import com.example.carware.network.api.setAppointment
import com.example.carware.network.apiRequests.schedule.SetAppointmentRequest
import com.example.carware.network.apiResponse.appointment.AppointmentResponse
import com.example.carware.network.apiResponse.schedule.Centers
import com.example.carware.network.apiResponse.schedule.Service
import io.ktor.client.HttpClient

class ServiceRepository(private val client: HttpClient) {

    suspend fun getServiceTypeRepo(): List<Service> {

        return getServiceType(client)
    }
    suspend fun getServiceCentersRepo(): List<Centers> {

        return getServiceCenters(client)
    }
    suspend fun setAppointmentRepo(
        request: SetAppointmentRequest,
    ): AppointmentResponse {
        return setAppointment(request,client)
    }
}