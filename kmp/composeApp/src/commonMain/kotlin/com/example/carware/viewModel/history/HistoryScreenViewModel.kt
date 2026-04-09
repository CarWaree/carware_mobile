package com.example.carware.viewModel.history

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carware.repository.HistoryRepository
import com.example.carware.util.storage.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoryScreenViewModel(
    private val repository: HistoryRepository,
    ): ViewModel() {

    private val _state = MutableStateFlow<HistoryScreenState>(HistoryScreenState.Loading)

    val state: StateFlow<HistoryScreenState> =_state.asStateFlow()

    init {

        loadHistory()
    }

     fun loadHistory() {
         viewModelScope.launch {
             _state.value= HistoryScreenState.Loading
             try {

                 val historyItems =repository.getHistoryItemRepo()

                 if (historyItems.isEmpty())
                     _state.value= HistoryScreenState.Error("No History Found")
                 else
                     _state.value= HistoryScreenState.Success(historyItems)

             }catch (e: Exception){
                 _state.value = HistoryScreenState.Error(e.message ?: "Unknown error: ${e::class.simpleName}")

             }
         }
    }


}