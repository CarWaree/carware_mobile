package com.example.carware.util

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import okhttp3.internal.platform.PlatformRegistry.applicationContext
import org.koin.mp.KoinPlatform

actual class CalendarLauncher actual constructor(){
    actual fun openCalendarWithEvent(
        title: String,
        description: String,
        startTimeMillis: Long,
        repeatInterval: Int,
        repeatUnit: String,
        repeatCount: Int,
    ) {
        val context = KoinPlatform.getKoin().get<Context>()

        val rruleFreq = when (repeatUnit.lowercase()) {
            "day" -> "DAILY"
            "month" -> "MONTHLY"
            "year" -> "YEARLY"
            else -> "DAILY"
        }

        val rrule = if (repeatCount > 0) {
            "FREQ=$rruleFreq;INTERVAL=$repeatInterval;COUNT=$repeatCount"
        } else {
            null
        }

        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, title)
            putExtra(CalendarContract.Events.DESCRIPTION, description)
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTimeMillis)
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, startTimeMillis + 3600000L)
            if (rrule != null) {
                putExtra(CalendarContract.Events.RRULE, rrule)
            }
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}