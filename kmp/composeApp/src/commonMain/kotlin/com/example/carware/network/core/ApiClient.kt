package com.example.carware.network.core

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

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
                ApiLogger.logError("Failed to parse response body: ${e.message}")
                ApiResult.Exception(e)
            }
        }
        response.status.value == 401 -> {
            val body = response.bodyAsText()
            ApiLogger.logError("Unauthorized (401): $body")
            ApiResult.Error(
                code = 401,
                message = "Unauthorized",
                body = body
            )
        }
        else -> {
            val body = response.bodyAsText()
            ApiLogger.logError("HTTP Error ${response.status.value}: $body")

            // Try to extract message from JSON response
            val errorMessage = try {
                val errorJson = Json.parseToJsonElement(body).jsonObject
                errorJson["message"]?.jsonPrimitive?.content ?: response.status.description
            } catch (e: Exception) {
                response.status.description
            }

            ApiResult.Error(
                code = response.status.value,
                message = errorMessage,
                body = body
            )
        }
    }
} catch (e: Exception) {
    ApiLogger.logError("Network error: ${e.message}")
    ApiResult.Exception(e)
}