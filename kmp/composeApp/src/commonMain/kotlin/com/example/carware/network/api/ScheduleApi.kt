package com.example.carware.network.api

import com.example.carware.network.apiRequests.schedule.SetAppointmentRequest
import com.example.carware.network.apiResponse.schedule.ServiceTypesResponse
import com.example.carware.network.apiResponse.appointment.AppointmentResponse
import com.example.carware.network.apiResponse.appointment.GetAppointmentResponse
import com.example.carware.network.apiResponse.schedule.ServiceCentersResponse
import com.example.carware.core.network.ApiResult
import com.example.carware.core.network.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

//suspend fun getServiceType(client: HttpClient): List<Service> {
//    return client.get("$baseUrl/api/Service") {
//        contentType(ContentType.Application.Json)
//    }.body<ServiceTypesResponse>().data
//}

suspend fun getServiceType(
    client: HttpClient,
    centerId: Int
): ApiResult<ServiceTypesResponse> =
    safeApiCall {
        client.get("$baseUrl/api/CenterMobile/$centerId/services") // need to handle the cetner ID
    }

suspend fun getServiceCenters(client: HttpClient): ApiResult<ServiceCentersResponse> =
    safeApiCall {
        client.get("$baseUrl/api/CenterMobile")
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