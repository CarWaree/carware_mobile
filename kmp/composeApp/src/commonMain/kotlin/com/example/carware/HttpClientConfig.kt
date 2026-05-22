package com.example.carware

import com.example.carware.network.core.ApiLogger


object HttpClientConfig {
    // Toggle logging on/off
    var enableLogging = true

    // Other settings you might need later
    var requestTimeoutMillis = 3_000L  // 10 seconds
    var connectTimeoutMillis = 3_000L
    var socketTimeoutMillis = 3_000L

    init {
        // Set logging based on your needs
        ApiLogger.isEnabled = enableLogging
    }
}
