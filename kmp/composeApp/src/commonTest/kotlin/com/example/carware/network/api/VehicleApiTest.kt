package com.example.carware.network.api

import com.example.carware.network.apiRequests.vehicle.UpdateVehicleRequest
import com.example.carware.network.apiRequests.vehicle.VehicleRequest
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

class VehicleApiTest {

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
    // Tests for getBrands()
    // ==========================================

    @Test
    fun testGetBrands_Success_ReturnsList() = runTest {
        // Arrange: BrandResponse has a "data" wrapper containing the list
        val mockJsonResponse = """
            {
                "data": [
                    { "id": 1, "name": "Toyota" },
                    { "id": 2, "name": "Honda" }
                ]
            }
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)

        // Act
        val responseList = getBrands(client = mockClient)

        // Assert: Your function extracts and returns the List<Brand> directly
        assertNotNull(responseList)
        assertEquals(2, responseList.size)
        assertEquals(1, responseList[0].id)
        assertEquals("Toyota", responseList[0].name)
    }

    // ==========================================
    // Tests for getModels()
    // ==========================================

    @Test
    fun testGetModels_Success_ReturnsList() = runTest {
        // Arrange: ModelResponse has a "data" wrapper containing the list
        val mockJsonResponse = """
            {
                "data": [
                    { "id": 10, "name": "Camry" },
                    { "id": 11, "name": "Corolla" }
                ]
            }
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)

        // Act
        val responseList = getModels(brandId = 1, client = mockClient)

        // Assert
        assertNotNull(responseList)
        assertEquals(2, responseList.size)
        assertEquals("Camry", responseList[0].name)
    }

    // ==========================================
    // Tests for getVehicles() (List of User's Vehicles)
    // ==========================================

    @Test
    fun testGetVehicles_Success() = runTest {
        // Arrange: Matches GetVehicleResponse containing List<Vehicles>
        val mockJsonResponse = """
            {
                "data": [
                    {
                        "id": 100,
                        "brandName": "Toyota",
                        "modelName": "Camry",
                        "year": 2022,
                        "color": "Black",
                        "userName": "Marwan"
                    }
                ],
                "statusCode": 200,
                "message": "Vehicles fetched successfully"
            }
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)

        // Act
        val response = getVehicles(mockClient)

        // Assert
        assertEquals("Vehicles fetched successfully", response.message)
        assertEquals(200, response.statusCode)
        assertNotNull(response.data)
        assertEquals(1, response.data.size)
        assertEquals("Black", response.data[0].color)
    }

    @Test
    fun testGetVehicles_Failure_ThrowsException() = runTest {
        val errorJsonResponse = """{"message": "Unauthorized"}"""
        val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.Unauthorized)

        val exception = assertFailsWith<Exception> {
            getVehicles(mockClient)
        }

        // Uses response.status.value (401)
        val expectedMessage = "API Error (401): $errorJsonResponse"
        assertEquals(expectedMessage, exception.message)
    }

    // ==========================================
    // Tests for getVehicle() (Single Vehicle by ID)
    // ==========================================

    @Test
    fun testGetVehicleSingle_Success() = runTest {
        // Arrange: Matches VehicleResponse containing a single Vehicle object
        val mockJsonResponse = """
            {
                "data": {
                    "id": 100,
                    "brandName": "Honda",
                    "modelName": "Civic",
                    "year": 2021,
                    "color": "White",
                    "userName": "Marwan"
                },
                "statusCode": 200,
                "message": "Vehicle found"
            }
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)

        // Act
        val response = getVehicle(mockClient, 100)

        // Assert
        assertEquals(200, response.statusCode)
        assertEquals("Honda", response.data.brandName)
        assertEquals("Civic", response.data.modelName)
    }

    @Test
    fun testGetVehicleSingle_Failure_ThrowsException() = runTest {
        val errorJsonResponse = """{"message": "Vehicle not found"}"""
        val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.NotFound)

        val exception = assertFailsWith<Exception> {
            getVehicle(mockClient, 999)
        }

        // Uses response.status.value (404)
        val expectedMessage = "Get Vehicle Error (404): $errorJsonResponse"
        assertEquals(expectedMessage, exception.message)
    }

    // ==========================================
    // Tests for addVehicles()
    // ==========================================

    @Test
    fun testAddVehicles_Success() = runTest {
        // Arrange
        val mockJsonResponse = """
            {
                "data": {
                    "id": 101,
                    "brandName": "Ford",
                    "modelName": "Mustang",
                    "year": 2023,
                    "color": "Red",
                    "userName": "Marwan"
                },
                "statusCode": 201,
                "message": "Vehicle added"
            }
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.Created)

        val request = VehicleRequest(
            brandId = 3,
            modelId = 15,
            year = 2023,
            color = "Red"
        )

        // Act
        val response = addVehicles(request, mockClient)

        // Assert
        assertEquals("Vehicle added", response.message)
        assertEquals(201, response.statusCode)
        assertEquals("Mustang", response.data.modelName)
    }

    @Test
    fun testAddVehicles_Failure_ThrowsException() = runTest {
        val errorJsonResponse = """{"message": "Invalid brand"}"""
        val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.BadRequest)
        val request = VehicleRequest(0, 0, 0, "")

        val exception = assertFailsWith<Exception> {
            addVehicles(request, mockClient)
        }

        // Notice: This uses response.status (400 Bad Request), not response.status.value
        val expectedMessage = "API Error (400 Bad Request): $errorJsonResponse"
        assertEquals(expectedMessage, exception.message)
    }

    // ==========================================
    // Tests for updateVehicle()
    // ==========================================

    @Test
    fun testUpdateVehicle_Success() = runTest {
        // Arrange: Matches UpdateVehicleResponse (no data object needed)
        val mockJsonResponse = """
            {
                "statusCode": 200,
                "message": "Car updated successfully"
            }
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)

        val request = UpdateVehicleRequest(
            id = 100,
            brandName = "Toyota",
            modelName = "Camry",
            year = 2024,
            color = "Blue"
        )

        // Act
        val response = updateVehicle(mockClient, 100, request)

        // Assert
        assertEquals(200, response.statusCode)
        assertEquals("Car updated successfully", response.message)
    }

    @Test
    fun testUpdateVehicle_Failure_ThrowsException() = runTest {
        val errorJsonResponse = """{"message": "Update failed"}"""
        val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.BadRequest)
        val request = UpdateVehicleRequest(100, "", "", 2024, "Blue")

        val exception = assertFailsWith<Exception> {
            updateVehicle(mockClient, 100, request)
        }

        // Notice: This uses response.status (400 Bad Request)
        val expectedMessage = "Update car  Error (400 Bad Request): $errorJsonResponse"
        assertEquals(expectedMessage, exception.message)
    }

    // ==========================================
    // Tests for deleteVehicle()
    // ==========================================

    @Test
    fun testDeleteVehicle_Success() = runTest {
        // Arrange: Matches DeleteVehicleResponse
        val mockJsonResponse = """
            {
                "statusCode": 200,
                "message": "Car deleted successfully"
            }
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)

        // Act
        val response = deleteVehicle(mockClient, 100)

        // Assert
        assertEquals(200, response.statusCode)
        assertEquals("Car deleted successfully", response.message)
    }

    @Test
    fun testDeleteVehicle_Failure_ThrowsException() = runTest {
        val errorJsonResponse = """{"message": "Deletion failed"}"""
        val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.NotFound)

        val exception = assertFailsWith<Exception> {
            deleteVehicle(mockClient, 999)
        }

        // Notice: This uses response.status (404 Not Found)
        val expectedMessage = "Delete car  Error (404 Not Found): $errorJsonResponse"
        assertEquals(expectedMessage, exception.message)
    }
}