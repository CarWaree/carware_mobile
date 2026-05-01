package com.example.carware.network.cache

import com.example.carware.network.apiResponse.reminder.Reminder
import kotlinx.serialization.Serializable

@Serializable
data class ReminderCacheData(
    val reminders: List<Reminder> = emptyList(),
    val lastUpdated: String=" ",
    val userId: Int = 0
)