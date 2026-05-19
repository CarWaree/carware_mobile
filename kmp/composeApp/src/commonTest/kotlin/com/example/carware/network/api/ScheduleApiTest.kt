package com.example.carware.network.api

import com.example.carware.network.apiRequests.schedule.SetAppointmentRequest
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
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ScheduleApiTest {

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
    // Tests for getServiceType()
    // ==========================================

    @Test
    fun testGetServiceType_Success_ReturnsList() = runTest {
        // Arrange: Matches ServiceTypesResponse wrapper
        val mockJsonResponse = """
            {
                "data": [
                    { "id": 1, "name": "Oil Change" },
                    { "id": 2, "name": "Tire Rotation" }
                ],
                "statusCode": 200,
                "message": "Services fetched successfully"
            }
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)

        // Act
        val result = getServiceType(mockClient)

        assertTrue(result is ApiResult.Success)
        val response = (result as ApiResult.Success).data

// Assert: Access the list through the response wrapper
        assertNotNull(response)
        val responseList = response.data


        // Assert: Your function returns the List<Service> directly from the .data field
        assertNotNull(responseList)
        assertEquals(2, responseList.size)
        assertEquals(1, responseList[0].id)
        assertEquals("Oil Change", responseList[0].name)
    }

    // ==========================================
    // Tests for getServiceCenters()
    // ==========================================

    @Test
    fun testGetServiceCenters_Success_ReturnsList() = runTest {
        // Arrange: This endpoint directly returns a List<Centers>, NO wrapper object!
        val mockJsonResponse = """
            [
                {
                    "id": 10,
                    "name": "Downtown Auto",
                    "location": "123 Main St",
                    "phone": "555-1234"
                },
                {
                    "id": 11,
                    "name": "Uptown Garage",
                    "location": null,
                    "phone": null
                }
            ]
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)

        // Act
        val result = getServiceCenters(mockClient)

        assertTrue(result is ApiResult.Success)
        val responseList = (result as ApiResult.Success).data

// Assert
        assertNotNull(responseList)
        assertEquals(2, responseList.size)
        assertEquals(1, responseList[0].id)
        assertEquals("Center Name", responseList[0].name)

        // Assert
        assertNotNull(responseList)
        assertEquals(2, responseList.size)
        assertEquals(10, responseList[0].id)
        assertEquals("Downtown Auto", responseList[0].name)
        // Testing that nullable fields parse properly
        assertEquals(null, responseList[1].location)
    }

    // ==========================================
    // Tests for getAppointments()
    // ==========================================

    @Test
    fun testGetAppointments_Success_ReturnsResponse() = runTest {
        // Arrange: Matches GetAppointmentResponse containing a List<Appointments>
        val mockJsonResponse = """
            {
                "data": [
                    {
                        "id": 100,
                        "date": "2024-11-20",
                        "status": "Scheduled",
                        "vehicleName": "Toyota Camry",
                        "serviceName": "Oil Change",
                        "providerName": "Downtown Auto"
                    }
                ],
                "statusCode": 200,
                "message": "Appointments fetched successfully"
            }
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)

        // Act
        val result = getAppointments( mockClient)

        assertTrue(result is ApiResult.Success)
        val response = (result as ApiResult.Success).data
        // Assert
        assertEquals("Appointments fetched successfully", response.message)
        assertEquals(200, response.statusCode)
        assertNotNull(response.data)
        assertEquals(1, response.data.size)
        assertEquals("Scheduled", response.data[0].status)
    }

    @Test
    fun testGetAppointments_Failure_ThrowsCustomException() = runTest {
        // Arrange
        val errorJsonResponse = """{"message": "Unauthorized request"}"""
        val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.Unauthorized)

        // Act & Assert
        val exception = assertFailsWith<Exception> {
            getAppointments(mockClient)
        }

        // Your code throws exactly: Exception("API Error (${response.status.value}): $errorBody")
        val expectedMessage = "API Error (401): $errorJsonResponse"
        assertEquals(expectedMessage, exception.message)
    }

    // ==========================================
    // Tests for setAppointment()
    // ==========================================

    @Test
    fun testSetAppointment_Success_ReturnsResponse() = runTest {
        // Arrange: Matches AppointmentResponse containing a single Appointment
        val mockJsonResponse = """
            {
                "data": {
                    "id": 999,
                    "date": "2024-12-01",
                    "status": "Pending",
                    "vehicleName": "Honda Civic",
                    "serviceName": "Tire Rotation",
                    "providerName": "Uptown Garage"
                },
                "statusCode": 201,
                "message": "Appointment created successfully"
            }
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.Created)

        val request = SetAppointmentRequest(
            date = "2024-12-01",
            timeSlot = "10:00 AM",
            vehicleId = 1,
            serviceId = 2,
            serviceCenterId = 11
        )

        // Act
        val result = setAppointment(request, mockClient)

        assertTrue(result is ApiResult.Success)
        val response = (result as ApiResult.Success).data
        // Assert
        assertEquals("Appointment created successfully", response.message)
        assertEquals(201, response.statusCode)
        assertNotNull(response.data)
        assertEquals(999, response.data.id)
        assertEquals("Pending", response.data.status)
        assertEquals("Honda Civic", response.data.vehicleName)
    }

    @Test
    fun testSetAppointment_Failure_ThrowsCustomException() = runTest {
        // Arrange
        val errorJsonResponse = """{"message": "Time slot unavailable"}"""
        val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.Conflict)

        val request = SetAppointmentRequest(
            date = "2024-12-01",
            timeSlot = "10:00 AM",
            vehicleId = 1,
            serviceId = 2,
            serviceCenterId = 11
        )

        // Act & Assert
        val exception = assertFailsWith<Exception> {
            setAppointment(request, mockClient)
        }

        // Your code throws exactly: Exception("Server error: ${response.status.value} - $errorBody")
        val expectedMessage = "Server error: 409 - $errorJsonResponse"
        assertEquals(expectedMessage, exception.message)
    }
}