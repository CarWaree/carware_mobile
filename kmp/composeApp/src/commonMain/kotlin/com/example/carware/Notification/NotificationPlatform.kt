package com.example.carware.Notification

interface PushTokenProvider {
    fun getPushToken(onTokenReceived: (String) -> Unit)
}

expect fun getPushTokenProvider(): PushTokenProvider