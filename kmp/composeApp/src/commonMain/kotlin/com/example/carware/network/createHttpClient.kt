package com.example.carware.network

import com.example.carware.util.storage.PreferencesManager
import getHttpClientEngine
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createHttpClient(preferencesManager: PreferencesManager): HttpClient {
    return HttpClient(getHttpClientEngine()) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                allowSpecialFloatingPointValues = true
            } ,contentType = ContentType.Any)
        }
     
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 100_00  // 10 seconds
            connectTimeoutMillis = 100_00
            socketTimeoutMillis = 100_00
        }

        install(Auth) {
            bearer {
                loadTokens {
                    val token = preferencesManager.getToken()
                    if (token != null) {
                        BearerTokens(
                            accessToken = token,
                            refreshToken = ""
                        )
                    } else null // ✅ return null if no token so Auth skips the header
                }
            }
        }
    }
}

