package com.example.carware.reposotory

import com.example.carware.network.apiRequests.auth.LoginRequest
import com.example.carware.network.core.UiResult
import com.example.carware.repository.auth.AuthRepository
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AuthRepositoryTest {

    @Test
    fun `loginUser should return success response when API returns 200`() = runTest {
        // 1. Setup Mock Engine with a full valid JSON matching AuthResponse and UserData
        val mockEngine = MockEngine { _ ->
            respond(
                content = """{
                    "statusCode": 200,
                    "message": "Success",
                    "data": {
                        "isAuthenticated": true,
                        "firstName": "John",
                        "lastName": "Doe",
                        "username": "johndoe",
                        "email": "test@email.com",
                        "roles": ["User"],
                        "accessToken": "fake_token",
                        "refreshToken": "fake_refresh",
                        "refreshTokenExpiration": "2025-12-31T23:59:59Z"
                    }
                }""",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val mockClient = HttpClient(mockEngine) {
            install(ContentNegotiation) { 
                json(Json { 
                    ignoreUnknownKeys = true 
                    isLenient = true
                    encodeDefaults = true
                }) 
            }
        }

        val repository = AuthRepository(mockClient)

        // 2. Execute
        val result = repository.logInRepo(LoginRequest("test@email.com", "password123"))

// 3. Assert
        assertTrue(result is UiResult.Success)
        val response = (result as UiResult.Success).data

        assertEquals(200, response.statusCode)
    }
}
