package com.example.carware.viewModel.reminder.reminderHistory

import com.example.carware.network.apiResponse.history.GetHistoryItemResponse
import com.example.carware.network.apiResponse.history.GetHistoryResponse
import com.example.carware.network.apiResponse.reminder.GetReminderResponse
import com.example.carware.network.apiResponse.reminder.Reminder


sealed class ReminderHistoryState {
    data object Loading : ReminderHistoryState()
    data class Success(val historyItems: List<Reminder>) : ReminderHistoryState()
    data class Error(val message: String) : ReminderHistoryState()
}

sealed class ReminderHistoryItemState {
    data object Loading : ReminderHistoryItemState()
    data class Success(val historyItem: GetHistoryItemResponse) : ReminderHistoryItemState()
    data class Error(val message: String) : ReminderHistoryItemState()
}