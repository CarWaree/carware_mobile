package com.example.carware.network.api

import com.example.carware.network.apiRequests.reminder.ReminderRequest
import com.example.carware.network.apiResponse.reminder.GetReminderResponse
import com.example.carware.network.apiResponse.reminder.ReminderResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json

suspend fun setReminder(client: HttpClient, request: ReminderRequest): ReminderResponse {
    println("📤 Sending request: ${Json.encodeToString(request)}")

    val response: HttpResponse = client.post("$baseUrl/api/maintenancereminder") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }

    val rawBody = response.bodyAsText()
    println("📥 Raw response: $rawBody")

    if (!response.status.isSuccess()) {
        throw Exception("HTTP ${response.status.value}: $rawBody")
    }

    return Json.decodeFromString<ReminderResponse>(rawBody)
}
suspend fun getReminder(client: HttpClient): GetReminderResponse{
    val response: HttpResponse=client.get("$baseUrl/api/maintenancereminder/my")
    val rawBody = response.bodyAsText()
    println("📥 Raw  get reminder response: $rawBody")

    return try{
        if (!response.status.isSuccess()) {
            val errorResponse = response.body<GetReminderResponse>()
            throw Exception(errorResponse.message)
        }

        response.body<GetReminderResponse>()

    } catch (e: Exception) {
        println("❌ set reminder  failed: ${e.message}")
        throw Exception(e.message ?: "reminder failed")
    }
}