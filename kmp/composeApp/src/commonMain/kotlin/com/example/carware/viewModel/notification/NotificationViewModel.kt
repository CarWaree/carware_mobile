package com.example.carware.viewModel.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carware.Notification.PushTokenProvider
import com.example.carware.network.api.registerFCMToken
import com.example.carware.network.apiRequests.notifications.RegisterTokenRequest
import com.example.carware.repository.NotificationsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val notificationsRepository: NotificationsRepository,
    private val pushTokenProvider: PushTokenProvider
): ViewModel() {
    private val _state = MutableStateFlow<NotificationState>(NotificationState.Idle)
    val state = _state.asStateFlow()

    fun onPermissionResult(granted: Boolean) {
        if (granted) {
            registerToken()
        }
    }
    private fun registerToken() {
        pushTokenProvider.getPushToken { token ->
            viewModelScope.launch {
                _state.value = NotificationState.Loading
                try {
                    notificationsRepository.registerFCMTokenRepo(
                        RegisterTokenRequest(token = token, platform = 1)
                    )
                    _state.value = NotificationState.Success
                } catch (e: Exception) {
                    _state.value = NotificationState.Error(e.message ?: "Unknown error")
                }
            }
        }
    }

    fun testPushNotification() {
        viewModelScope.launch {
            try {
                notificationsRepository.testFcmNotificationRepo()
            } catch (e: Exception) {
                println("FCM_TEST Failed: ${e.message}")
            }
        }
    }
}
