package com.example.carware.viewModel.history

import com.example.carware.network.apiResponse.history.GetHistoryItemResponse
import com.example.carware.network.apiResponse.history.GetHistoryResponse


sealed class HistoryScreenState {
    data object Loading : HistoryScreenState()
    data class Success(val historyItems: List<GetHistoryResponse>) : HistoryScreenState()
    data class Error(val message: String) : HistoryScreenState()
}

sealed class HistoryItemState {
    data object Loading : HistoryItemState()
    data class Success(val historyItem: GetHistoryItemResponse) : HistoryItemState()
    data class Error(val message: String) : HistoryItemState()
}