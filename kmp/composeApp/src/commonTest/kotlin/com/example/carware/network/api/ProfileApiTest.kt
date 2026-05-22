package com.example.carware.network.api

import com.example.carware.network.apiRequests.profile.UpdateProfileRequest
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
import kotlin.test.DefaultAsserter.assertTrue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ProfileApiTest {

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
    // Tests for getProfile()
    // ==========================================

    @Test
    fun testGetProfile_Success() = runTest {
        // Arrange: statusCode is now an Integer (200) instead of a String!
        val mockJsonResponse = """
            {
                "data": {
                    "fullName": "Marwan",
                    "phoneNumber": "1234567890",
                    "email": "test@email.com",
                    "profileImageUrl": ""
                },
                "statusCode": 200,
                "message": "Profile fetched successfully"
            }
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)


        val result = getProfile(mockClient)

        assertTrue(result is ApiResult.Success)
        val response = (result as ApiResult.Success).data
        // Act

        // Assert
        assertEquals("Profile fetched successfully", response.message)
        assertEquals(200, response.statusCode) // Asserting against an Int!
        assertNotNull(response.data)
        assertEquals("test@email.com", response.data.email)
    }

    @Test
    fun testGetProfile_Failure_ThrowsException() = runTest {
        val errorJsonResponse = """{"message": "Unauthorized access"}"""
        val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.Unauthorized)

        val exception = assertFailsWith<Exception> {
            getProfile(mockClient)
        }

        val expectedMessage = "API Error Profile (401): $errorJsonResponse"
        assertEquals(expectedMessage, exception.message)
    }

    // ==========================================
    // Tests for updateProfile()
    // ==========================================

    @Test
    fun testUpdateProfile_Success() = runTest {
        // Arrange: statusCode is now an Integer here as well.
        val mockJsonResponse = """
            {
                "data": {
                    "fullName": "Marwan Updated",
                    "phoneNumber": "0987654321",
                    "email": "new@email.com",
                    "profileImageUrl": ""
                },
                "statusCode": 200,
                "message": "Profile updated successfully"
            }
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)

        val request = UpdateProfileRequest(
            fullName = "Marwan Updated",
            phoneNumber = "0987654321",
            pendingEmail = "new@email.com"
        )

        val result = updateProfile(request, mockClient)

        assertTrue(result is ApiResult.Success)
        val response = (result as ApiResult.Success).data

        // Act

        // Assert
        assertEquals("Profile updated successfully", response.message)
        assertEquals<Comparable<*>>(200, response.statusCode) // Asserting against an Int!
        assertNotNull(response.data)
        assertEquals("new@email.com", response.data.email)
    }

    @Test
    fun testUpdateProfile_Failure_ThrowsException() = runTest {
        val errorJsonResponse = """{"message": "Invalid phone number format"}"""
        val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.BadRequest)

        val request = UpdateProfileRequest(
            fullName = "Marwan Updated",
            phoneNumber = "invalid_phone",
            pendingEmail = ""
        )

        val exception = assertFailsWith<Exception> {
            updateProfile(request, mockClient)
        }

        val expectedMessage = "Update Error Profile (400): $errorJsonResponse"
        assertEquals(expectedMessage, exception.message)
    }
}