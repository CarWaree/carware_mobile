package com.example.carware.Notification

actual fun getPushTokenProvider(): PushTokenProvider {
    return object : PushTokenProvider {
        override fun getPushToken(onTokenReceived: (String) -> Unit) {
            // Token is handled in AppDelegate — nothing needed here
            // iOS token registration happens via messaging(_:didReceiveRegistrationToken:)
        }
    }
}