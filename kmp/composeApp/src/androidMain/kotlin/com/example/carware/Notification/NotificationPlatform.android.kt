package com.example.carware.Notification
import com.google.firebase.messaging.FirebaseMessaging


actual fun getPushTokenProvider(): PushTokenProvider {
    return object : PushTokenProvider {
        override fun getPushToken(onTokenReceived: (String) -> Unit) {
            FirebaseMessaging.getInstance().token
                .addOnSuccessListener { token ->
                    onTokenReceived(token)
                }
        }
    }
}