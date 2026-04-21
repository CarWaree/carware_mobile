package com.example.carware.repository

import com.example.carware.network.api.getHistory
import com.example.carware.network.api.getHistoryItem
import com.example.carware.network.apiResponse.history.GetHistoryItemResponse
import com.example.carware.network.apiResponse.history.GetHistoryResponse
import io.ktor.client.HttpClient

class HistoryRepository(
    private val client: HttpClient) {
    suspend fun getHistoryRepo(): List<GetHistoryResponse> {


        val response = getHistory(client)
        return response
    }
    suspend fun getHistoryItemRepo(id: Int): GetHistoryItemResponse {
        val response = getHistoryItem(client, id)
        return response
    }
}
