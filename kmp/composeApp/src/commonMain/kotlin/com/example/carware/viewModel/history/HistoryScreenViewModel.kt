package com.example.carware.viewModel.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carware.repository.HistoryRepository
import com.example.carware.viewModel.history.HistoryScreenState.Loading.id
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoryScreenViewModel(
    private val repository: HistoryRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<HistoryScreenState>(HistoryScreenState.Loading)

    val state: StateFlow<HistoryScreenState> = _state.asStateFlow()

    init {
        loadHistory()
    }

    fun loadHistory() {
        viewModelScope.launch {
            val historyItem = repository.getHistoryItemRepo(id)

            _state.value = HistoryScreenState.Loading
            try {
                val historyItems = repository.getHistoryRepo()

                if (historyItems.isEmpty()) {
                    _state.value = HistoryScreenState.Error("No History Found")
                } else {
                    _state.value = HistoryScreenState.Success(
                        historyItems = historyItems,
                        historyItem = historyItem,
                    )
                }
            } catch (e: Exception) {
                _state.value = HistoryScreenState.Error(
                    e.message ?: "Unknown error: ${e::class.simpleName}"
                )
            }
        }
    }

    fun loadHistoryItem(id: Int) {
        viewModelScope.launch {
            try {
                val currentState = _state.value

                val historyItems = when (currentState) {
                    is HistoryScreenState.Success -> currentState.historyItems
                    else -> repository.getHistoryRepo()
                }

                val historyItem = repository.getHistoryItemRepo(id)

                if (historyItem.id == null) {
                    _state.value = HistoryScreenState.Error("No History Found")
                } else {
                    _state.value = HistoryScreenState.Success(
                        historyItems = historyItems,
                        historyItem = historyItem,
                    )
                }
            } catch (e: Exception) {
                _state.value = HistoryScreenState.Error(
                    e.message ?: "Unknown error: ${e::class.simpleName}"
                )
            }
        }
    }


}