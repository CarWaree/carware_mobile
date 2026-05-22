package com.example.carware.util

expect class CalendarLauncher() {
    fun openCalendarWithEvent(
        title: String,
        description: String,
        startTimeMillis: Long,
        repeatInterval: Int ,
        repeatUnit: String ,
        repeatCount: Int ,
    )
}