package com.example.carware.Notification

import androidx.compose.runtime.Composable

@Composable
expect fun RequestNotificationPermission(
    onResult: (Boolean) -> Unit
)
expect fun openNotificationSettings(context: Any?)
expect fun areNotificationsEnabled(context: Any?): Boolean
