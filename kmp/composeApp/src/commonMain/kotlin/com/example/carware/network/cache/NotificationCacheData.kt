package com.example.carware.network.cache

import com.example.carware.Notification.SavedNotification
import com.example.carware.network.apiResponse.reminder.Reminder
import kotlinx.serialization.Serializable

@Serializable
data class NotificationCacheData(
    val notifications: List<SavedNotification> = emptyList(),
)