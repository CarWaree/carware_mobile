package com.example.carware.Notification

import androidx.compose.runtime.Composable
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNAuthorizationStatusAuthorized
import platform.UserNotifications.UNUserNotificationCenter

@Composable
actual fun RequestNotificationPermission(
    onResult: (Boolean) -> Unit
) {
    val center = UNUserNotificationCenter.currentNotificationCenter()
    center.requestAuthorizationWithOptions(
        UNAuthorizationOptionAlert or UNAuthorizationOptionSound or UNAuthorizationOptionBadge
    ) { granted, _ ->
        onResult(granted)
    }
}

actual fun openNotificationSettings(context: Any?) {
    val url = NSURL(string = UIApplicationOpenSettingsURLString)
    UIApplication.sharedApplication.openURL(url, options = emptyMap<Any?, Any?>()) {}
}

actual fun areNotificationsEnabled(context: Any?): Boolean {
    var enabled = false
    UNUserNotificationCenter.currentNotificationCenter()
        .getNotificationSettingsWithCompletionHandler { settings ->
            enabled = settings?.authorizationStatus == UNAuthorizationStatusAuthorized
        }
    return enabled
}
