package com.example.carware.network.core

import com.example.carware.HttpClientConfig
import com.example.carware.network.api.baseUrl
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
                        println("🔄 [AUTH] Attempting to refresh token...")
                        println("🔄 [AUTH] existing token = {${preferencesManager.getToken()?.take(20)}}")

                        val rawResponse = client.post("${baseUrl}/api/auth/refresh-token") {
                            markAsRefreshTokenRequest()
                        }

                        println("📡 [AUTH] Status: ${rawResponse.status}")
                        println("📡 [AUTH] Raw response body: ${rawResponse.bodyAsText()}")

                        val response: RefreshTokenResponse = rawResponse.body()

                        println("✅ [AUTH] Token refresh successful")
                        println("📝 [AUTH] New access token: ${response.data.accessToken.take(20)}...")

                        // Save new tokens
                        preferencesManager.saveToken(response.data.accessToken)
                        preferencesManager.saveRefreshToken(response.data.refreshToken)
                        preferencesManager.saveExpiresOn(response.data.accessTokenExpiration)

                        println("💾 [AUTH] Tokens saved to preferences")

                        // Return the new tokens
                        BearerTokens(
                            accessToken = response.data.accessToken,
                            refreshToken = response.data.refreshToken
                        )
                    } catch (e: Exception) {
                        println("❌ [AUTH] Token refresh failed: ${e.message}")
                        println("❌ [AUTH] Exception: ${e.stackTraceToString()}")

                        preferencesManager.clearToken()
                        preferencesManager.saveRefreshToken(null)
                        println("🚪 [AUTH] Tokens cleared - user needs to login")

                        null
                    }
                }            }
        }
        install(HttpCookies) {
            storage = AcceptAllCookiesStorage()
        }
    }
}
