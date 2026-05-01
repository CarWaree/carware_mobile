package com.example.carware.network.cache

import com.example.carware.network.apiResponse.reminder.Reminder

data class ReminderCacheData(
    val reminders: List<Reminder> = emptyList(),
    val lastUpdated: String=" ",
    val userId: Int = 0
)