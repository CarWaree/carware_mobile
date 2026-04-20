package com.example.carware.Notification

import androidx.compose.runtime.Composable

@Composable
expect fun RequestNotificationPermission(
    onResult: (Boolean) -> Unit
)