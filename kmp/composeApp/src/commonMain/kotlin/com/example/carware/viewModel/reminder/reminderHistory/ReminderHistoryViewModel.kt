package com.example.carware.viewModel.reminder.reminderHistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carware.repository.ReminderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReminderHistoryViewModel(
    private val repository: ReminderRepository

) : ViewModel() {
    private val _reminderState =
        MutableStateFlow<ReminderHistoryState>(ReminderHistoryState.Loading)
    val reminderState: StateFlow<ReminderHistoryState> = _reminderState.asStateFlow()

    private val _reminderItemState =
        MutableStateFlow<ReminderHistoryItemState>(ReminderHistoryItemState.Loading)
    val reminderItemState: StateFlow<ReminderHistoryItemState> = _reminderItemState.asStateFlow()

    init {
        loadHistory()
    }

    fun loadHistory() {
        viewModelScope.launch {
            _reminderState.value = ReminderHistoryState.Loading
            try {
                val items = repository.getReminderRepo()
                _reminderState.value = if (items.isEmpty())
                    ReminderHistoryState.Error("No reminders Yet")
                else
                    ReminderHistoryState.Success(items)
            } catch (e: Exception) {
                _reminderState.value = ReminderHistoryState.Error(
                    e.message ?: "Error"
                )

            }
        }
    }

}