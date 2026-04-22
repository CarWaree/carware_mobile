package com.example.carware.network.api

import com.example.carware.network.apiRequests.schedule.SetAppointmentRequest
import com.example.carware.network.apiResponse.schedule.Centers
import com.example.carware.network.apiResponse.schedule.Service
import com.example.carware.network.apiResponse.schedule.ServiceTypesResponse
import com.example.carware.network.apiResponse.appointment.AppointmentResponse
import com.example.carware.network.apiResponse.appointment.GetAppointmentResponse
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


suspend fun getAppointments(client: HttpClient): GetAppointmentResponse {
    println("=== GET APPOINTMENTS REQUEST ===")
    println("URL: $baseUrl/api/Appointments/my")

    val response: HttpResponse = client.get {
        url("$baseUrl/api/Appointment/my")
        contentType(ContentType.Application.Json)
    }

    println("=== GET APPOINTMENTS RESPONSE ===")
    println("Status: ${response.status.value}")
    println("Headers: ${response.headers.entries()}")

    if (response.status.isSuccess()) {
        val rawBody = response.bodyAsText()
        println("Raw Body: $rawBody")
        return try {
            Json.decodeFromString<GetAppointmentResponse>(rawBody)
        } catch (e: Exception) {
            println("Parse Error: ${e.message}")
            throw Exception("Failed to parse response: ${e.message}")
        }
    } else {
        val errorBody = try { response.bodyAsText() } catch (e: Exception) { "Could not read error body." }
        println("Error Body: $errorBody")
        throw Exception("API Error (${response.status.value}): $errorBody")
    }
}

suspend fun setAppointment(request: SetAppointmentRequest, client: HttpClient): AppointmentResponse {
    println("=== SET APPOINTMENT REQUEST ===")
    println("URL: $baseUrl/api/Appointment")
    println("date: ${request.date}")
    println("timeSlot: ${request.timeSlot}")
    println("vehicleId: ${request.vehicleId}")
    println("serviceId: ${request.serviceId}")
    println("centerId: ${request.serviceCenterId}")

    val response = client.post("$baseUrl/api/Appointment") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }

    println("=== SET APPOINTMENT RESPONSE ===")
    println("Status: ${response.status.value}")

    if (!response.status.isSuccess()) {
        val errorBody = try { response.bodyAsText() } catch (e: Exception) { "Could not read error body" }
        println("Error Body: $errorBody")
        throw Exception("Server error: ${response.status.value} - $errorBody")
    }

    val rawBody = response.bodyAsText()
    println("Raw Body: $rawBody")

    return Json.decodeFromString<AppointmentResponse>(rawBody)
}