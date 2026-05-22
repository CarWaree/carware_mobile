package com.example.carware.network.api

import com.example.carware.network.apiRequests.reminder.ReminderRequest
import com.example.carware.network.apiResponse.reminder.GetReminderResponse
import com.example.carware.network.apiResponse.reminder.ReminderResponse
import com.example.carware.network.core.ApiResult
import com.example.carware.network.core.safeApiCall
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

suspend fun setReminder(client: HttpClient, request: ReminderRequest): ApiResult<ReminderResponse> =
    safeApiCall {
        client.post("$baseUrl/api/maintenancereminder") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }
suspend fun getReminder(client: HttpClient): ApiResult<GetReminderResponse> =
    safeApiCall { client.get("$baseUrl/api/maintenancereminder/my") }