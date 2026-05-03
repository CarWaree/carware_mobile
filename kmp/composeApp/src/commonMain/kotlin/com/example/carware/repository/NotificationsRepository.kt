package com.example.carware.repository

import com.example.carware.Notification.SavedNotification
import com.example.carware.cache.notificationStore
import com.example.carware.network.api.registerFCMToken
import com.example.carware.network.api.testFcmNotification
import com.example.carware.network.apiRequests.notifications.RegisterTokenRequest
import com.example.carware.network.apiResponse.notifications.RegisterTokenResponse
import com.example.carware.network.cache.NotificationCacheData
import io.ktor.client.HttpClient

class NotificationsRepository(private val client: HttpClient) {
    suspend fun registerFCMTokenRepo(request: RegisterTokenRequest): RegisterTokenResponse{
        return registerFCMToken(request,client)

    }


        suspend fun testFcmNotificationRepo() {
            return testFcmNotification(client)


        }
    suspend fun getNotificationsRepo(): List<SavedNotification> {
        return notificationStore.get()?.notifications ?: emptyList()
    }
    suspend fun getUnreadNotificationsCountRepo(): Int {
        return notificationStore.get()?.notifications?.count { !it.read } ?: 0
    }
    suspend fun markAllNotificationsAsReadRepo() {
        val currentCache = notificationStore.get() ?: NotificationCacheData()
        val updated = currentCache.notifications.map { it.copy(read = true) }
        notificationStore.set(currentCache.copy(notifications = updated))
    }
    suspend fun saveNotificationRepo(notification: SavedNotification) {
        val currentCache = notificationStore.get() ?: NotificationCacheData()
        val updatedNotifications = currentCache.notifications + notification
        notificationStore.set(currentCache.copy(notifications = updatedNotifications))
    }


}
