package com.example.carware.repository

import com.example.carware.cache.reminderStore
import com.example.carware.network.api.getReminder
import com.example.carware.network.api.setReminder
import com.example.carware.network.apiRequests.reminder.ReminderRequest
import com.example.carware.network.apiResponse.reminder.Reminder
import com.example.carware.network.apiResponse.reminder.ReminderResponse
import com.example.carware.network.cache.ReminderCacheData
import com.example.carware.network.core.ApiResult
import com.example.carware.network.core.UiResult
import io.ktor.client.HttpClient

class ReminderRepository(private val  client: HttpClient) {

    suspend fun setReminderRepo(request: ReminderRequest): UiResult<ReminderResponse> {
        return when (val result = setReminder(client, request)) {
            is ApiResult.Success -> {
                UiResult.Success(result.data)
            }
            is ApiResult.Error -> {
                UiResult.Error(result.message)
            }
            is ApiResult.Exception -> {
                UiResult.Error("Set reminder error: ${result.throwable.message}")
            }
        }
    }
    suspend fun getReminderRepo(): List<Reminder> {
        return when (val result = getReminder(client)) {
            is ApiResult.Success -> {
                val reminders = result.data.data  // Note: getReminder returns GetReminderResponse
                reminderStore.set(
                    ReminderCacheData(reminders = reminders, userId = 0)
                )
                reminders
            }
            is ApiResult.Error -> {
                val cached = reminderStore.get()
                cached?.reminders ?: emptyList()
            }
            is ApiResult.Exception -> {
                val cached = reminderStore.get()
                cached?.reminders ?: emptyList()
            }
        }
    }
}