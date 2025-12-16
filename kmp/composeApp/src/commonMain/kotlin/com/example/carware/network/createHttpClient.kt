package com.example.carware.network

import getHttpClientEngine
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.basic
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createHttpClient(): HttpClient {

    return HttpClient(getHttpClientEngine()) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                allowSpecialFloatingPointValues = true
            })
        }


        install(Logging) {
            level = LogLevel.ALL
        }


//        install(Auth) {
//            bearer {
//                // This lambda is called *before* every request to get the token
//                loadTokens {
//                    // IMPORTANT: Replace these placeholders with your actual token retrieval logic
//                    val accessToken =SharedToken.token // Retrieve the stored token
//
//                    if (accessToken != null) {
//                        BearerTokens(accessToken, " ")
//                    } else {
//                        null
//                    }
//                }
//

        }
    }


