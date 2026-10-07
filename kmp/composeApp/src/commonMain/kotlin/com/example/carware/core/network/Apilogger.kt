package com.example.carware.core.network

object ApiLogger {
    var isEnabled = true

    fun log(message: String) {
        if (isEnabled) {
            println("🌐 [API] $message")
        }
    }

    fun logError(message: String) {
        if (isEnabled) {
            println("❌ [API] $message")
        }
    }

    fun logSuccess(message: String) {
        if (isEnabled) {
            println("✅ [API] $message")
        }
    }
}

