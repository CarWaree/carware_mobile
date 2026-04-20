package com.example.carware.viewModel.history

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

    private val _state = MutableStateFlow<HistoryScreenState>(HistoryScreenState.Loading)

    val state: StateFlow<HistoryScreenState> = _state.asStateFlow()

    init {
        loadHistoryItem()
        loadHistory()
    }

    fun loadHistory() {
        viewModelScope.launch {
            viewModelScope.launch {
                if (_state.value !is HistoryScreenState.Success) {
                    _state.value = HistoryScreenState.Loading
                }
                try {

                    val historyItems = repository.getHistoryRepo()
                    val historyItem = repository.getHistoryItemRepo(1)

                    if (historyItems.isEmpty())
                        _state.value = HistoryScreenState.Error("No History Found")
                    else
                        _state.value = HistoryScreenState.Success(historyItems, historyItem)

                } catch (e: Exception) {
                    _state.value = HistoryScreenState.Error(
                        e.message ?: "Unknown error: ${e::class.simpleName}"
                    )

                }
            }
        }
    }

    fun loadHistoryItem() {
        viewModelScope.launch {
            viewModelScope.launch {
                if (_state.value !is HistoryScreenState.Success) {
                    _state.value = HistoryScreenState.Loading
                }
                try {
                    val historyItems = repository.getHistoryRepo()

                    val historyItem = repository.getHistoryItemRepo(state.value.id)




                    if (historyItem.id == null)
                        _state.value = HistoryScreenState.Error("No History Found")
                    else
                        _state.value = HistoryScreenState.Success(historyItems, historyItem)

                } catch (e: Exception) {
                    _state.value = HistoryScreenState.Error(
                        e.message ?: "Unknown error: ${e::class.simpleName}"
                    )

                }
            }
        }
    }


}