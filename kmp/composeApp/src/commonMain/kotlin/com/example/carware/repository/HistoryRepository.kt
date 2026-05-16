package com.example.carware.repository

import com.example.carware.cache.historyStore
import com.example.carware.network.core.ApiResult
import com.example.carware.network.api.getHistory
import com.example.carware.network.api.getHistoryItem
import com.example.carware.network.apiResponse.history.GetHistoryItemResponse
import com.example.carware.network.apiResponse.history.GetHistoryResponse
import com.example.carware.network.cache.HistoryCacheData
import io.ktor.client.HttpClient

class HistoryRepository(
    private val client: HttpClient
) {
    suspend fun getHistoryRepo(): List<GetHistoryResponse> {
        return when (val result = getHistory(client)) {
            is ApiResult.Success -> {
                val histories = result.data
                historyStore.set(
                    HistoryCacheData(histories)
                )
                histories
            }

            is ApiResult.Error -> {
                // HTTP error occurred - try to return cached data
                val cached = historyStore.get()
                cached?.historyItems ?: emptyList()
            }

            is ApiResult.Exception -> {
                // Network/parsing error - try to return cached data
                val cached = historyStore.get()
                cached?.historyItems ?: emptyList()
            }
        }
    }

    suspend fun getHistoryItemRepo(id: Int): GetHistoryItemResponse {
        return when (val result = getHistoryItem(client, id)) {
            is ApiResult.Success -> {
                val history = result.data
                historyStore.set(
                    HistoryCacheData(historyItem = history)
                )
                history  // ✅ Return the data
            }

            is ApiResult.Error -> {
                val cached = historyStore.get()
                cached?.historyItem ?: throw Exception("No cached history item available")
            }

            is ApiResult.Exception -> {
                val cached = historyStore.get()
                cached?.historyItem ?: throw Exception("No cached history item available")
            }
        }
    }

}
