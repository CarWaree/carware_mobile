package com.example.carware.network.core

import com.example.carware.HttpClientConfig
import com.example.carware.network.api.baseUrl
import com.example.carware.network.api.refreshTokenCall
import com.example.carware.network.apiRequests.auth.RefreshTokenRequest
import com.example.carware.network.apiResponse.auth.RefreshTokenResponse
import com.example.carware.util.storage.PreferencesManager
import getHttpClientEngine
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.AcceptAllCookiesStorage
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
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
            }, contentType = ContentType.Any)
        }

        install(HttpTimeout) {
            requestTimeoutMillis = HttpClientConfig.requestTimeoutMillis
            connectTimeoutMillis = HttpClientConfig.connectTimeoutMillis
            socketTimeoutMillis = HttpClientConfig.socketTimeoutMillis
        }

        install(HttpCookies) {
            storage = AcceptAllCookiesStorage()
        }

        install(Auth) {
            bearer {
                loadTokens {
                    val token = preferencesManager.getToken()
                    if (token != null) {
                        BearerTokens(
                            accessToken = token,
                            refreshToken = preferencesManager.getRefreshToken() ?: ""
                        )
                    } else null
                }

                refreshTokens {
                    try {
                        println("🔄 [REFRESH] Starting token refresh...")

                        val refreshToken = preferencesManager.getRefreshToken()
                        println("🔄 [REFRESH] Refresh token: ${refreshToken?.take(20) ?: "NULL"}")

                        if (refreshToken == null) {
                            println("❌ [REFRESH] No refresh token found, aborting")
                            return@refreshTokens null
                        }

                        println("🔄 [REFRESH] Creating refresh client...")
                        val refreshClient = HttpClient(getHttpClientEngine()) {
                            install(ContentNegotiation) {
                                json(Json {
                                    ignoreUnknownKeys = true
                                    isLenient = true
                                }, contentType = ContentType.Any)
                            }
                        }
                        println("✅ [REFRESH] Refresh client created")

                        println("📡 [REFRESH] Calling refreshTokenCall...")
                        val result = refreshTokenCall(
                            request = RefreshTokenRequest(refreshToken = refreshToken),
                            client = refreshClient
                        )
                        println("📡 [REFRESH] Got result: $result")

                        when (result) {
                            is ApiResult.Success -> {
                                println("✅ [REFRESH] Success! Response: ${result.data}")
                                val data = result.data.data
                                println("📝 [REFRESH] New access token: ${data.accessToken.take(20)}...")
                                println("📝 [REFRESH] New refresh token: ${data.refreshToken.take(20)}...")
                                println("📝 [REFRESH] Expires on: ${data.accessTokenExpiration}")

                                preferencesManager.saveToken(data.accessToken)
                                preferencesManager.saveRefreshToken(data.refreshToken)
                                preferencesManager.saveExpiresOn(data.accessTokenExpiration)
                                println("💾 [REFRESH] Tokens saved successfully")

                                BearerTokens(
                                    accessToken = data.accessToken,
                                    refreshToken = data.refreshToken
                                )
                            }
                            is ApiResult.Error -> {
                                println("❌ [REFRESH] API Error - code: ${result.code}")
                                println("❌ [REFRESH] API Error - message: ${result.message}")
                                println("❌ [REFRESH] API Error - body: ${result.body}")
                                preferencesManager.clearToken()
                                preferencesManager.saveRefreshToken(null)
                                null
                            }
                            is ApiResult.Exception -> {
                                println("❌ [REFRESH] Exception: ${result.throwable.message}")
                                println("❌ [REFRESH] Stacktrace: ${result.throwable.stackTraceToString()}")
                                preferencesManager.clearToken()
                                preferencesManager.saveRefreshToken(null)
                                null
                            }
                        }
                    } catch (e: Exception) {
                        println("❌ [REFRESH] Unexpected crash: ${e.message}")
                        println("❌ [REFRESH] Stacktrace: ${e.stackTraceToString()}")
                        preferencesManager.clearToken()
                        preferencesManager.saveRefreshToken(null)
                        null
                    }
                }            }
        }
    }
}
