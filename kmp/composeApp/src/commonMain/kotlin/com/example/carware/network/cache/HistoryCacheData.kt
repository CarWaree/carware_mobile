package com.example.carware.network.cache

import com.example.carware.network.apiResponse.history.GetHistoryItemResponse
import com.example.carware.network.apiResponse.history.GetHistoryResponse
import kotlinx.serialization.Serializable

@Serializable
data class HistoryCacheData(
    val historyItems: List<GetHistoryResponse> = emptyList(),
    val historyItem: GetHistoryItemResponse? = null

)
