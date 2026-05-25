package com.example.carware.Notification

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.app.NotificationManagerCompat

@Composable
actual fun RequestNotificationPermission(onResult: (Boolean) -> Unit) {
    val launcher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestPermission()
) { granted ->
    onResult(granted)
}

LaunchedEffect(Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
    } else {
        onResult(true)
    }
}
}


@RequiresApi(Build.VERSION_CODES.O)
actual fun openNotificationSettings(context: Any?) {
    val context = context as Context

    val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
        putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
    }
    context.startActivity(intent)
}
actual fun areNotificationsEnabled(context: Any?): Boolean {
    val context = context as Context

    return NotificationManagerCompat.from(context).areNotificationsEnabled()
}