package com.example.carware.viewModel.notification

import com.example.carware.Notification.SavedNotification

sealed class NotificationState {
    object Loading : NotificationState()
    object Success : NotificationState()
    data class Error(val message: String) : NotificationState()
}

sealed class NotificationsUiState {
    object Loading : NotificationsUiState()
    data class Success(val notifications: List<SavedNotification>, val unreadCount: Int) : NotificationsUiState()
    data class Error(val message: String) : NotificationsUiState()
}
