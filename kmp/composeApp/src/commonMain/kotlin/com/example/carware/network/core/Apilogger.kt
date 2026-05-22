package com.example.carware.network.core

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

