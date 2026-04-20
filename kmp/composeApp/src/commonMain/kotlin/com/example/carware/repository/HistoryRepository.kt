package com.example.carware.repository

import com.example.carware.network.api.getHistory
import com.example.carware.network.apiResponse.history.GetHistoryResponse
import com.example.carware.util.storage.PreferencesManager
import io.ktor.client.HttpClient

class HistoryRepository(
    private val client: HttpClient) {
    suspend fun getHistoryItemRepo(): List<GetHistoryResponse> {


        val response = getHistory(client)
        return response
    }
}