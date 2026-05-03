package com.example.carware.network.api

import com.example.carware.network.apiRequests.reminder.ReminderRequest
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
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ReminderApiTest {

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
    // Tests for setReminder()
    // ==========================================

    @Test
    fun testSetReminder_Success() = runTest {
        // Arrange: Updated to perfectly match your Reminder data class
        val mockJsonResponse = """
            {
                "data": {
                    "id": 1,
                    "notificationDate": "2024-06-01",
                    "typeName": "Oil Change",
                    "vehicleName": "Toyota Camry",
                    "note": "Change engine oil"
                },
                "statusCode": 201,
                "message": "Reminder created successfully"
            }
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.Created)

        val request = ReminderRequest(
            notificationDate = "2024-06-01",
            repeatInterval = 3,
            repeatUnit = "MONTHS",
            repeatCount = 5,
            note = "Change engine oil",
            typeId = 2,
            vehicleId = 101
        )

        // Act
        val response = setReminder(mockClient, request)

        // Assert
        assertEquals("Reminder created successfully", response.message)
        assertEquals(201, response.statusCode)
        assertNotNull(response.data)
        // Asserting the new fields
        assertEquals("Oil Change", response.data.typeName)
        assertEquals("Toyota Camry", response.data.vehicleName)
    }

    @Test
    fun testSetReminder_Failure_ThrowsException() = runTest {
        val errorJsonResponse = """{"message": "Invalid date format"}"""
        val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.BadRequest)

        val request = ReminderRequest(
            notificationDate = "invalid-date",
            repeatInterval = 0,
            repeatUnit = "",
            repeatCount = 0,
            note = "",
            typeId = 0,
            vehicleId = 0
        )

        val exception = assertFailsWith<Exception> {
            setReminder(mockClient, request)
        }

        val expectedMessage = "HTTP 400: $errorJsonResponse"
        assertEquals(expectedMessage, exception.message)
    }

    // ==========================================
    // Tests for getReminder()
    // ==========================================

    @Test
    fun testGetReminder_Success_ReturnsList() = runTest {
        // Arrange: Updated the list items to match the Reminder data class
        val mockJsonResponse = """
            {
                "data": [
                    {
                        "id": 1,
                        "notificationDate": "2024-07-15",
                        "typeName": "Tire Rotation",
                        "vehicleName": "Honda Civic",
                        "note": "Rotate front to back"
                    },
                    {
                        "id": 2,
                        "notificationDate": "2024-08-01",
                        "typeName": "Inspection",
                        "vehicleName": "Ford Mustang",
                        "note": "Annual state inspection"
                    }
                ],
                "statusCode": 200,
                "message": "Reminders fetched successfully"
            }
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)

        // Act
        val response = getReminder(mockClient)

        // Assert
        assertEquals("Reminders fetched successfully", response.message)
        assertEquals(200, response.statusCode)
        assertNotNull(response.data)
        assertEquals(2, response.data.size)
        // Checking the parsed fields of the first item
        assertEquals("Tire Rotation", response.data[0].typeName)
        assertEquals("Honda Civic", response.data[0].vehicleName)
    }

    @Test
    fun testGetReminder_Success_ReturnsEmptyList() = runTest {
        val mockJsonResponse = """
            {
                "data": [],
                "statusCode": 200,
                "message": "No reminders found"
            }
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)

        val response = getReminder(mockClient)

        assertEquals(200, response.statusCode)
        assertTrue(response.data.isEmpty())
    }

    @Test
    fun testGetReminder_Failure_ParsesErrorMessage() = runTest {
        val errorJsonResponse = """
            {
                "data": [],
                "statusCode": 404,
                "message": "User not found"
            }
        """.trimIndent()

        val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.NotFound)

        val exception = assertFailsWith<Exception> {
            getReminder(mockClient)
        }

        assertEquals("User not found", exception.message)
    }
}