package com.example.carware.util

import kotlinx.cinterop.ExperimentalForeignApi
import platform.EventKit.EKEntityType
import platform.EventKit.EKEvent
import platform.EventKit.EKEventStore
import platform.EventKit.EKSpan
import platform.EventKitUI.EKEventEditViewController
import platform.Foundation.NSDate
import platform.Foundation.NSURL
import platform.Foundation.dateWithTimeIntervalSince1970
import platform.UIKit.UIApplication
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue

actual class CalendarLauncher actual constructor() {
    @OptIn(ExperimentalForeignApi::class)
    actual fun openCalendarWithEvent(
        title: String,
        description: String,
        startTimeMillis: Long
    ) {
        val store = EKEventStore()
        store.requestAccessToEntityType(EKEntityType.EKEntityTypeEvent) { granted, _ ->
            if (!granted) return@requestAccessToEntityType

            val event = EKEvent.eventWithEventStore(store)
            event.title = title
            event.notes = description
            event.startDate = NSDate.dateWithTimeIntervalSince1970(startTimeMillis / 1000.0)
            event.endDate = NSDate.dateWithTimeIntervalSince1970((startTimeMillis / 1000.0) + 3600)
            event.calendar = store.defaultCalendarForNewEvents

            dispatch_async(dispatch_get_main_queue()) {
                val editVC = EKEventEditViewController()
                editVC.event = event
                editVC.eventStore = store

                val rootVC = UIApplication.sharedApplication.keyWindow?.rootViewController
                rootVC?.presentViewController(editVC, animated = true, completion = null)
            }
        }
    }
}