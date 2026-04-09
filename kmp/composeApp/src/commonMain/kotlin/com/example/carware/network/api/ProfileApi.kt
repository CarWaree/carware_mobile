package com.example.carware.network.api

import com.example.carware.network.apiRequests.profile.UpdateProfileRequest
import com.example.carware.network.apiResponse.profile.GetProfileResponse
import com.example.carware.network.apiResponse.profile.UpdateProfileResponse
import com.example.carware.network.createHttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

suspend fun getProfile(token: String): GetProfileResponse {
    val client = createHttpClient()
    val response: HttpResponse = client.get {
        url("$baseUrl/api/Profile")
        header("Authorization", "Bearer $token")
        contentType(ContentType.Application.Json)
    }
    println("--- RESPONSE DEBUG: Status: ${response.status.value}")
    val rawBody = response.bodyAsText()
    println("--- RAW BODY: $rawBody")

    if (response.status.isSuccess()) {
        return response.body<GetProfileResponse>()
    } else {
        println("--- ERROR DETAILS: $rawBody")
        throw Exception("API Error (${response.status.value}): $rawBody")
    }
}
suspend fun updateProfile(token: String, request: UpdateProfileRequest): UpdateProfileResponse {
    val client = createHttpClient()
    val response: HttpResponse = client.put { // Use .put for updates
        url("$baseUrl/api/Profile")
        header("Authorization", "Bearer $token")
        contentType(ContentType.Application.Json)
        setBody(request)
    }

    println("--- RESPONSE DEBUG: Status: ${response.status.value}")
    val rawBody = response.bodyAsText()
    println("--- RAW BODY: $rawBody")
    if (response.status.isSuccess()) {
        return response.body<UpdateProfileResponse>()
    } else {
        throw Exception("Update Error (${response.status.value}): $rawBody")
    }
}