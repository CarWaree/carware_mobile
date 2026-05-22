package com.example.carware.Notification

import kotlinx.serialization.Serializable

@Serializable
data class SavedNotification(
    val id: String,
    val title: String,
    val body: String,
    val timestamp: Long,
    val read: Boolean = false
)
