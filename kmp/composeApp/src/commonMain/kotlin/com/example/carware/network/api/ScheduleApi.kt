package com.example.carware.network.api

import com.example.carware.network.apiRequests.schedule.SetAppointmentRequest
import com.example.carware.network.apiResponse.schedule.Centers
import com.example.carware.network.apiResponse.schedule.Service
import com.example.carware.network.apiResponse.schedule.ServiceTypesResponse
import com.example.carware.network.apiResponse.appointment.AppointmentResponse
import com.example.carware.network.apiResponse.appointment.GetAppointmentResponse
import com.example.carware.network.apiResponse.schedule.ServiceCentersResponse
import com.example.carware.network.core.ApiResult
import com.example.carware.network.core.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json

//suspend fun getServiceType(client: HttpClient): List<Service> {
//    return client.get("$baseUrl/api/Service") {
//        contentType(ContentType.Application.Json)
//    }.body<ServiceTypesResponse>().data
//}

suspend fun getServiceType(client: HttpClient): ApiResult<ServiceTypesResponse> =
    safeApiCall {
        client.get("$baseUrl/api/Service")
    }

suspend fun getServiceCenters(client: HttpClient): ApiResult<List<Centers>> =
    safeApiCall {
        client.get("$baseUrl/api/ServiceCenters")
    }
suspend fun getAppointments(client: HttpClient): ApiResult<GetAppointmentResponse> =
    safeApiCall {
        client.get {
            url("$baseUrl/api/Appointment/my")
        }
    }

suspend fun setAppointment(
    request: SetAppointmentRequest,
    client: HttpClient
): ApiResult<AppointmentResponse> =
    safeApiCall {
        client.post("$baseUrl/api/Appointment") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }