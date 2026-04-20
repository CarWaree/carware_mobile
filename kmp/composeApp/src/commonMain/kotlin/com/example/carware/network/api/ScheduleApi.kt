package com.example.carware.network.api

import com.example.carware.network.apiRequests.schedule.SetAppointmentRequest
import com.example.carware.network.apiResponse.schedule.Centers
import com.example.carware.network.apiResponse.schedule.Service
import com.example.carware.network.apiResponse.schedule.ServiceTypesResponse
import com.example.carware.network.apiResponse.appointment.AppointmentResponse
import com.example.carware.network.apiResponse.vehicle.GetVehicleResponse
import com.example.carware.network.createHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json

suspend fun getServiceType(client: HttpClient): List<Service> {
    return client.get("$baseUrl/api/Service") {
        contentType(ContentType.Application.Json)
    }.body<ServiceTypesResponse>().data
}

suspend fun getServiceCenters(client: HttpClient): List<Centers> {

    return client.get("$baseUrl/api/ServiceCenters") {
        contentType(ContentType.Application.Json)
    }.body<List<Centers>>()
}


suspend fun getAppointments( client: HttpClient): AppointmentResponse {
    val response: HttpResponse = client.get {
        url("$baseUrl/api/Appointments/my")
        contentType(ContentType.Application.Json)
    }
    println("--- RESPONSE DEBUG get Appointments : Status: ${response.status.value}")

    if (response.status.isSuccess()) {
        return response.body<AppointmentResponse>()
    } else {
        val errorBody = try {
            response.bodyAsText()
        } catch (e: Exception) {
            "Could not read error body."
        }
        println("--- ERROR DETAILS get Appointments: $errorBody")
        throw Exception("API Error (${response.status.value}): $errorBody")
    }
}

suspend fun setAppointment(
    request: SetAppointmentRequest,
    client: HttpClient
): AppointmentResponse {

    return try {
        val response = client.post("$baseUrl/api/Appointments") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        // Get raw response
        val rawBody = response.bodyAsText()
        println("Raw Response: set Appointments $rawBody")

        // Try to parse it
        try {
            Json.decodeFromString<AppointmentResponse>(rawBody)
        } catch (e: Exception) {
            // If parsing fails, create a default success response
            println("Parse error set Appointments: ${e.message}")
            AppointmentResponse(
                data = emptyList(),
                statusCode = response.status.value,
                message = "Appointment created successfully"
            )
        }
    } catch (e: Exception) {
        println("API Error: ${e.message}")
        throw e
    }
}
