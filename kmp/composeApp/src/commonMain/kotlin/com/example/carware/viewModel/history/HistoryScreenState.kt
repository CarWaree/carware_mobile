package com.example.carware.viewModel.history

import com.example.carware.network.apiResponse.history.GetHistoryResponse


sealed class HistoryScreenState {
    data object Loading : HistoryScreenState()
    data class Success(val historyItems: List<GetHistoryResponse>) : HistoryScreenState()
    data class Error(val message: String) : HistoryScreenState()
}