package com.example.carware.repository

import com.example.carware.cache.reminderStore
import com.example.carware.network.api.getReminder
import com.example.carware.network.api.setReminder
import com.example.carware.network.apiRequests.reminder.ReminderRequest
import com.example.carware.network.apiResponse.reminder.Reminder
import com.example.carware.network.apiResponse.reminder.ReminderResponse
import com.example.carware.network.cache.ReminderCacheData
import io.ktor.client.HttpClient

class ReminderRepository(private val  client: HttpClient) {

    suspend fun setReminderRepo(request: ReminderRequest): ReminderResponse{
        return setReminder(client,request)

    }
    suspend fun getReminderRepo(): List<Reminder> {
        return try {
            val response = getReminder(client)
            val reminders = response.data

            // NEW: Save to cache using set()
            reminderStore.set(
                ReminderCacheData(reminders = reminders, userId = 0)
            )

            // Return fresh data
            reminders

        } catch (e: Exception) {
            // CHANGED: get() instead of read()
            val cached = reminderStore.get()
            cached?.reminders ?: emptyList()
        }
    }
}