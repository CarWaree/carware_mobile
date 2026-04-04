package com.example.carware.repository

import com.example.carware.network.api.getHistory
import com.example.carware.network.apiResponse.history.GetHistoryResponse
import com.example.carware.util.storage.PreferencesManager

class HistoryRepository(private val prefs: PreferencesManager) {
    suspend fun getHistoryItemRepo(): List<GetHistoryResponse> {
        val token = prefs.getToken() ?: ""
        println("--- TOKEN: '$token'")
        if (token.isEmpty()) return emptyList()

        val response = getHistory(token)
        return response
    }
}