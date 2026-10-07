package com.example.carware.network.api

import com.example.carware.network.apiRequests.notifications.RegisterTokenRequest
import com.example.carware.network.apiResponse.notifications.RegisterTokenResponse
import com.example.carware.core.network.ApiResult
import com.example.carware.core.network.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

suspend fun registerFCMToken(
    request: RegisterTokenRequest,
    client: HttpClient
): ApiResult<RegisterTokenResponse> =
    safeApiCall {
        client.post { // Use .put for updates
            url("$baseUrl/api/notifications/register-token")
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }

suspend fun testFcmNotification(client: HttpClient): ApiResult<Unit> =
    safeApiCall {
        client.get("$baseUrl/api/notifications/test-fcm")
    }