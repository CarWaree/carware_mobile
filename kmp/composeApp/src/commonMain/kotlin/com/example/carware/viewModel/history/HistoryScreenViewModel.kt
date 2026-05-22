package com.example.carware.viewModel.history

import androidx.compose.material3.Text
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carware.repository.HistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoryScreenViewModel(
    private val repository: HistoryRepository,
) : ViewModel() {

    private val _historyState = MutableStateFlow<HistoryScreenState>(HistoryScreenState.Loading)
    val historyState: StateFlow<HistoryScreenState> = _historyState.asStateFlow()

    private val _historyItemState = MutableStateFlow<HistoryItemState>(HistoryItemState.Loading)
    val historyItemState: StateFlow<HistoryItemState> = _historyItemState.asStateFlow()

    init {
        loadHistory()
    }

    fun loadHistory() {
        viewModelScope.launch {
            _historyState.value = HistoryScreenState.Loading
            try {
                val items = repository.getHistoryRepo()
                _historyState.value = if (items.isEmpty()) {
                    HistoryScreenState.Error("No History Found")
                } else {
                    HistoryScreenState.Success(items)
                }
            } catch (e: Exception) {
                _historyState.value = HistoryScreenState.Error(
                    e.message ?: "Unknown error"
                )
            }
        }
    }

    fun loadHistoryItem(id: Int) {
        viewModelScope.launch {
            _historyItemState.value = HistoryItemState.Loading
            try {
                val item = repository.getHistoryItemRepo(id)
                println("--- VIEWMODEL: got item, setting Success")
                _historyItemState.value = HistoryItemState.Success(item)
                println("--- VIEWMODEL: state is now ${_historyItemState.value}")
            } catch (e: Exception) {
                println("--- VIEWMODEL ERROR: ${e.message}")
                _historyItemState.value = HistoryItemState.Error(e.message ?: "Unknown error")
            }
        }
    }}