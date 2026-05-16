package com.example.carware.network.core

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.isSuccess

suspend inline fun <reified T> safeApiCall(
    crossinline request: suspend () -> HttpResponse
): ApiResult<T> = try {
    val response = request()

    ApiLogger.log("HTTP ${response.status.value} - ${response.request.method} ${response.request.url}")

    when {
        response.status.isSuccess() -> {
            try {
                val body = response.body<T>()
                ApiLogger.logSuccess("Parsed response successfully")
                ApiResult.Success(body)
            } catch (e: Exception) {
                // Parsing failed - response was successful but body couldn't be parsed
                ApiLogger.logError("Failed to parse response body: ${e.message}")
                ApiResult.Exception(e)
            }
        }
        response.status.value == 401 -> {
            // Unauthorized - handle auth errors specifically
            val body = response.bodyAsText()
            ApiLogger.logError("Unauthorized (401): $body")
            ApiResult.Error(
                code = 401,
                message = "Unauthorized",
                body = body
            )
        }
        else -> {
            // Other HTTP errors (4xx, 5xx)
            val body = response.bodyAsText()
            ApiLogger.logError("HTTP Error ${response.status.value}: $body")
            ApiResult.Error(
                code = response.status.value,
                message = response.status.description,
                body = body
            )
        }
    }
} catch (e: Exception) {
    // Network error or request failed
    ApiLogger.logError("Network error: ${e.message}")
    ApiResult.Exception(e)
}