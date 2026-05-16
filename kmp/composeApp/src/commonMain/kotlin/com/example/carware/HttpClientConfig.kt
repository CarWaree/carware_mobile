package com.example.carware

import com.example.carware.network.core.ApiLogger


object HttpClientConfig {
    // Toggle logging on/off
    var enableLogging = true

    // Other settings you might need later
    var requestTimeoutMillis = 100_000L  // 10 seconds
    var connectTimeoutMillis = 100_000L
    var socketTimeoutMillis = 100_000L

    init {
        // Set logging based on your needs
        ApiLogger.isEnabled = enableLogging
    }
}
