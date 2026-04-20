package com.example.carware

import android.app.Service
import android.util.Log
import com.example.carware.network.apiRequests.notifications.RegisterTokenRequest
import com.example.carware.repository.NotificationsRepository
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.java.KoinJavaComponent.inject


class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val notificationsRepository: NotificationsRepository by inject()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                notificationsRepository.registerFCMTokenRepo(
                    RegisterTokenRequest(token = token, platform = 1)
                )
            } catch (e: Exception) {
                Log.e("FCM_SERVICE", "Token registration failed: ${e.message}")
            }
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
    }
}