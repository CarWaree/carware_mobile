package com.example.carware.network.api

import com.example.carware.network.apiResponse.history.GetHistoryResponse
import com.example.carware.network.createHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess


suspend fun getHistory(client: HttpClient): List<GetHistoryResponse> {
    val response: HttpResponse = client.get {
        url("$baseUrl/api/history")
        contentType(ContentType.Application.Json)
    }
    println("--- RESPONSE DEBUG HISTORY: Status: ${response.status.value}")
    val rawBody = response.bodyAsText()
    println("--- RAW BODY HISTORY: $rawBody")

    if (response.status.isSuccess()) {
        return response.body<List<GetHistoryResponse>>()
    } else {
        println("--- ERROR DETAILS HISTORY: $rawBody")
        throw Exception("API Error HISTORY(${response.status.value}): $rawBody")
    }
}
