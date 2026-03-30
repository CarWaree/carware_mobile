package com.example.carware.viewModel.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoryScreenViewModel(

): ViewModel() {

    private  val _state= MutableStateFlow<HistoryScreenState>(HistoryScreenState.Loading)
    val state: StateFlow<HistoryScreenState> = _state.asStateFlow()

    init {

    }
    fun loadHistory(){
        viewModelScope.launch {
            _state.value = HistoryScreenState.Loading




        }
    }
}