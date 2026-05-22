package com.example.carware.network.api

import com.example.carware.network.apiRequests.notifications.RegisterTokenRequest
import com.example.carware.network.core.ApiResult
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class NotificationsApiTest {

    // Helper function to build a fake Ktor client
    private fun createMockClient(
        responseContent: String,
        statusCode: HttpStatusCode
    ): HttpClient {
        val mockEngine = MockEngine { _ ->
            respond(
                content = responseContent,
                status = statusCode,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        return HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }

    // ==========================================
    // Tests for registerFCMToken()
    // ==========================================

    @Test
    fun testRegisterFCMToken_Success() = runTest {
        // Arrange: Mocking the JSON matching your RegisterTokenResponse
        val mockJsonResponse = """
            {
                "message": "Token registered successfully"
            }
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)

        // Using the properties from your RegisterTokenRequest screenshot
        val request = RegisterTokenRequest(
            token = "dummy_fcm_token_xyz_123",
            platform = 1 // e.g., 1 for Android, 2 for iOS
        )
        val result = registerFCMToken(request, mockClient)

        assertTrue(result is ApiResult.Success)
        val response = (result as ApiResult.Success).data
        // Act

        // Assert
        assertEquals("Token registered successfully", response.message)
    }

    @Test
    fun testRegisterFCMToken_Failure_ThrowsException() = runTest {
        // Arrange
        val errorJsonResponse = """{"error": "Invalid token format"}"""
        val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.BadRequest)

        val request = RegisterTokenRequest(
            token = "bad_token",
            platform = 1
        )

        // Act & Assert
        val exception = assertFailsWith<Exception> {
            registerFCMToken(request, mockClient)
        }

        // Your code throws: Exception("Update Error (${response.status.value}): $rawBody")
        val expectedMessage = "Update Error (400): $errorJsonResponse"
        assertEquals(expectedMessage, exception.message)
    }

    // ==========================================
    // Tests for testFcmNotification()
    // ==========================================

    @Test
    fun testFcmNotification_Success_DoesNotThrow() = runTest {
        // Arrange: The endpoint returns 200 OK. The body doesn't matter because you don't parse it.
        val mockJsonResponse = """{"status": "Notification sent"}"""
        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)

        // Act
        // If this function throws an exception, the test will automatically fail.
        // Since it doesn't throw on a 200 OK, the test will pass!
        testFcmNotification(mockClient)
    }

    @Test
    fun testFcmNotification_Failure_ThrowsException() = runTest {
        // Arrange: Simulating a 500 Internal Server Error from Firebase/Your Backend
        val errorJsonResponse = """{"error": "Failed to reach Firebase"}"""
        val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.InternalServerError)

        // Act & Assert
        val exception = assertFailsWith<Exception> {
            testFcmNotification(mockClient)
        }

        // Your code throws: Exception("FCM Test Error (${response.status.value}): $rawBody")
        val expectedMessage = "FCM Test Error (500): $errorJsonResponse"
        assertEquals(expectedMessage, exception.message)
    }
}