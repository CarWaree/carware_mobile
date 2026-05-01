package com.example.carware.util

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual class CalendarLauncher actual constructor() {
    actual fun openCalendarWithEvent(
        title: String,
        description: String,
        startTimeMillis: Long
    ) {
        val seconds = startTimeMillis / 1000.0
        val url = NSURL.URLWithString("calshow:$seconds")
        if (url != null) UIApplication.sharedApplication.openURL(url)
    }
}