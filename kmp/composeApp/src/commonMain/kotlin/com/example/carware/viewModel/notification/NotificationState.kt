package com.example.carware.viewModel.notification

sealed class NotificationState {
    object Idle : NotificationState()
    object Loading : NotificationState()
    object Success : NotificationState()
    data class Error(val message: String) : NotificationState()
}
