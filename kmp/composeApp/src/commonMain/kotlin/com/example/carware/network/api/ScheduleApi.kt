package com.example.carware.network.api

import com.example.carware.network.apiResponse.schedule.Centers
import com.example.carware.network.apiResponse.schedule.Service
import com.example.carware.network.apiResponse.schedule.ServiceTypesResponse
import com.example.carware.network.createHttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType

suspend fun getServiceType(): List<Service> {
    val client = createHttpClient()
    return client.get("$baseUrl/api/Service") {
        contentType(ContentType.Application.Json)
    }.body<ServiceTypesResponse>().data
}

suspend fun getServiceCenters(): List<Centers> {
    val client = createHttpClient()

    return client.get("$baseUrl/api/ServiceCenters") {
        contentType(ContentType.Application.Json)
    }.body<List<Centers>>()
}