package com.example.carware.repository

import com.example.carware.network.api.registerFCMToken
import com.example.carware.network.api.testFcmNotification
import com.example.carware.network.apiRequests.notifications.RegisterTokenRequest
import com.example.carware.network.apiResponse.notifications.RegisterTokenResponse
import io.ktor.client.HttpClient

class NotificationsRepository(private val client: HttpClient) {
    suspend fun registerFCMTokenRepo(request: RegisterTokenRequest): RegisterTokenResponse{
        return registerFCMToken(request,client)
    }


        suspend fun testFcmNotificationRepo() {
            return testFcmNotification(client)
        }


}
