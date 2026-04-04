package com.example.carware.network.api

import com.example.carware.network.apiResponse.history.GetHistoryResponse
import com.example.carware.network.createHttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

//suspend fun getHistory(token: String): GetHistoryResponse{
//    val client = createHttpClient()
//    val response: HttpResponse = client.get {
//        url("$baseUrl/api/history")
//        header("Authorization", "Bearer $token")
//        contentType(ContentType.Application.Json)
//    }
//    println("--- RESPONSE DEBUG: Status: ${response.status.value}")
//    println("--- RAW BODY: ${response.bodyAsText()}")
//
//    if (response.status.isSuccess())
//    {
//        return  response.body<GetHistoryResponse>()
//    }else {
//        val errorBody = try {
//            response.bodyAsText()
//        } catch (e: Exception) {
//            "Could not read error body."
//        }
//        println("--- ERROR DETAILS: $errorBody")
//        throw Exception("API Error (${response.status.value}): $errorBody")
//    }

suspend fun getHistory(token: String): List<GetHistoryResponse> {
    val client = createHttpClient()
    val response: HttpResponse = client.get {
        url("$baseUrl/api/history")
        header("Authorization", "Bearer $token")
        contentType(ContentType.Application.Json)
    }
    println("--- RESPONSE DEBUG: Status: ${response.status.value}")
    val rawBody = response.bodyAsText()
    println("--- RAW BODY: $rawBody")

    if (response.status.isSuccess()) {
        return response.body<List<GetHistoryResponse>>()
    } else {
        println("--- ERROR DETAILS: $rawBody")
        throw Exception("API Error (${response.status.value}): $rawBody")
    }
}
