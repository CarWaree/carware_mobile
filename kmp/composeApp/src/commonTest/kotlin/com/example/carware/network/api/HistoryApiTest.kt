package com.example.carware.network.api

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

class HistoryApiTest {

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
    // Tests for getHistory() (Returns List)
    // ==========================================

    @Test
    fun testGetHistory_Success_ReturnsList() = runTest {
        // Arrange: Mocking a JSON Array matching GetHistoryResponse
        // Note: totalPrice is a String here based on your previous screenshot
        val mockJsonResponse = """
            [
                {
                    "id": 1,
                    "carName": "Toyota Camry",
                    "providerName": "Quick Fix Auto",
                    "date": "2023-10-12",
                    "totalPrice": "150.00",
                    "paymentMethod": "Credit Card"
                },
                {
                    "id": 2,
                    "carName": "Honda Civic",
                    "providerName": "Bob's Garage",
                    "date": "2023-11-05",
                    "totalPrice": "45.50",
                    "paymentMethod": "Cash"
                }
            ]
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)

        // Act
        val result = getHistory( mockClient)

        assertTrue(result is ApiResult.Success)
        val response = (result as ApiResult.Success).data
        // Assert
        assertNotNull(response)
        assertEquals(2, response.size)

        assertEquals(1, response[0].id)
        assertEquals("Toyota Camry", response[0].carName)
        assertEquals("Quick Fix Auto", response[0].providerName)
        assertEquals("150.00", response[0].totalPrice)
    }

    @Test
    fun testGetHistory_Success_ReturnsEmptyList() = runTest {
        val mockJsonResponse = "[]"
        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)

        val response = getHistory(mockClient)

        assertNotNull(response)
    }

    @Test
    fun testGetHistory_Failure_ThrowsException() = runTest {
        val errorJsonResponse = """{"message": "Unauthorized access"}"""
        val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.Unauthorized)

        val exception = assertFailsWith<Exception> {
            getHistory(mockClient)
        }

        val expectedMessage = "API Error (401): $errorJsonResponse"
        assertEquals(expectedMessage, exception.message)
    }

    // ==========================================
    // Tests for getHistoryItem() (Returns Single Item)
    // ==========================================

    @Test
    fun testGetHistoryItem_Success_ReturnsSingleItem() = runTest {
        // Arrange: Mocking a single JSON Object matching GetHistoryItemResponse
        // Note: totalPrice is an Int here based on your latest screenshot
        val mockJsonResponse = """
            {
                "id": 99,
                "carName": "Ford Mustang",
                "serviceName": "Full Synthetic Oil Change",
                "providerName": "Speedy Lube",
                "date": "2024-01-15",
                "totalPrice": 89,
                "paymentMethod": "Debit Card",
                "serviceDetails": "Replaced oil filter and topped off fluids."
            }
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)
        val historyIdToFetch = 99

        // Act
        val result = getHistoryItem( mockClient,historyIdToFetch)

        assertTrue(result is ApiResult.Success)
        val response = (result as ApiResult.Success).data
        // Assert
        assertNotNull(response)
        assertEquals(99, response.id)
        assertEquals("Ford Mustang", response.carName)
        assertEquals("Full Synthetic Oil Change", response.serviceName)
        assertEquals(89, response.totalPrice) // Asserting it parsed as an Int
        assertEquals("Replaced oil filter and topped off fluids.", response.serviceDetails)
    }

    @Test
    fun testGetHistoryItem_Failure_NotFound() = runTest {
        val errorJsonResponse = """{"message": "History record not found"}"""
        val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.NotFound)

        val exception = assertFailsWith<Exception> {
            getHistoryItem(mockClient, 999)
        }

        val expectedMessage = "API Error (404): $errorJsonResponse"
        assertEquals(expectedMessage, exception.message)
    }
}