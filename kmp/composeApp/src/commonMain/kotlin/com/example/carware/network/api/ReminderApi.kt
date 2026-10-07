package com.example.carware.network.api

import com.example.carware.network.apiRequests.reminder.ReminderRequest
import com.example.carware.network.apiResponse.reminder.GetReminderResponse
import com.example.carware.network.apiResponse.reminder.ReminderResponse
import com.example.carware.core.network.ApiResult
import com.example.carware.core.network.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

suspend fun setReminder(client: HttpClient, request: ReminderRequest): ApiResult<ReminderResponse> =
    safeApiCall {
        client.post("$baseUrl/api/maintenancereminder") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }
suspend fun getReminder(client: HttpClient): ApiResult<GetReminderResponse> =
    safeApiCall { client.get("$baseUrl/api/maintenancereminder/my") }