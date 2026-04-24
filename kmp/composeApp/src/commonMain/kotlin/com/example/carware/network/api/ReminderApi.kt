package com.example.carware.network.api

import com.example.carware.network.apiRequests.reminder.ReminderRequest
import com.example.carware.network.apiResponse.reminderr.GetReminderResponse
import com.example.carware.network.apiResponse.reminderr.ReminderResponse
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

suspend fun setReminder(client: HttpClient, request: ReminderRequest): ReminderResponse {
    val response: HttpResponse = client.post("$baseUrl/api/maintenancereminder")
    {
        contentType(ContentType.Application.Json)
        setBody(request)
    }
    val rawBody = response.bodyAsText()
    println("📥 Raw  reminder response: $rawBody")

     return try{
        // Check HTTP status code first
        if (!response.status.isSuccess()) {
            val errorResponse = response.body<ReminderResponse>()
            throw Exception(errorResponse.message )
        }
         response.body<ReminderResponse>()

    } catch (e: Exception) {
        println("❌ set reminder  failed: ${e.message}")
        throw Exception(e.message ?: "reminder failed")
    }
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