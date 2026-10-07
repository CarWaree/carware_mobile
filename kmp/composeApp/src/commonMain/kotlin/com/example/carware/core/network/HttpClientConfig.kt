package com.example.carware.core.network

object HttpClientConfig {
    // Toggle logging on/off
    var enableLogging = true

    // Other settings you might need later
    var requestTimeoutMillis = 10_000L  // 10 seconds
    var connectTimeoutMillis = 10_000L
    var socketTimeoutMillis = 10_000L

    init {
        // Set logging based on your needs
        ApiLogger.isEnabled = enableLogging
    }
}