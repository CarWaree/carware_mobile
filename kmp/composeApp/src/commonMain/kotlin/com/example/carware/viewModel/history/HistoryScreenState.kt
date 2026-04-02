package com.example.carware.viewModel.history


sealed class HistoryScreenState {
    data object Loading : HistoryScreenState()
    data class Success(val ) : HistoryScreenState()
    data class Error(val message: String) : HistoryScreenState()
}