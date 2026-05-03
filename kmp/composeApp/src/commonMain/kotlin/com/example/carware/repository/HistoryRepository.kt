package com.example.carware.repository

import com.example.carware.cache.historyStore
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


      return  try {
          val response =getHistory(client)
          val histories=response
          historyStore.set(
              HistoryCacheData(histories)
          )
          histories
      }catch (e: Exception){
          val cached=historyStore.get()
          cached?.historyItems?:emptyList()
      }
    }
    suspend fun getHistoryItemRepo(id: Int): GetHistoryItemResponse {
        return try {
            val response = getHistoryItem(client, id)

            historyStore.set(
                HistoryCacheData(historyItem = response)
            )

            response

        } catch (e: Exception) {
            val cached = historyStore.get()
            cached?.historyItem ?: throw Exception("No cached history item")
        }
    }
}
