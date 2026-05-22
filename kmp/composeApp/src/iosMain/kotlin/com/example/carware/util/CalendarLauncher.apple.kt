package com.example.carware.util

import io.ktor.client.request.invoke
import kotlinx.cinterop.ExperimentalForeignApi
import platform.EventKit.EKEntityType
import platform.EventKit.EKEvent
import platform.EventKit.EKEventStore
import platform.EventKit.EKRecurrenceEnd
import platform.EventKit.EKSpan
import platform.EventKit.EKRecurrenceFrequency
import platform.EventKit.EKRecurrenceRule
import platform.EventKitUI.EKEventEditViewAction
import platform.EventKitUI.EKEventEditViewController
import platform.EventKitUI.EKEventEditViewDelegateProtocol
import platform.Foundation.NSDate
import platform.Foundation.NSURL
import platform.Foundation.dateWithTimeIntervalSince1970
import platform.UIKit.UIApplication
import platform.darwin.NSObject
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue

private class EventEditDelegate : NSObject(), EKEventEditViewDelegateProtocol {
    override fun eventEditViewController(
        controller: EKEventEditViewController,
        didCompleteWithAction: EKEventEditViewAction
    ) {
        controller.dismissViewControllerAnimated(true, completion = null)
    }
}

actual class CalendarLauncher actual constructor() {
    @OptIn(ExperimentalForeignApi::class)

    private var delegate: EventEditDelegate? = null

    actual fun openCalendarWithEvent(
        title: String,
        description: String,
        startTimeMillis: Long,
        repeatInterval: Int,
        repeatUnit: String,
        repeatCount: Int
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

            if (repeatCount > 0) {
                val frequency = when (repeatUnit.lowercase()) {
                    "day" -> EKRecurrenceFrequency.EKRecurrenceFrequencyDaily
                    "month" -> EKRecurrenceFrequency.EKRecurrenceFrequencyMonthly
                    "year" -> EKRecurrenceFrequency.EKRecurrenceFrequencyYearly
                    else -> EKRecurrenceFrequency.EKRecurrenceFrequencyDaily
                }

                val recurrenceEnd = EKRecurrenceEnd.recurrenceEndWithEndDate(
                    NSDate.dateWithTimeIntervalSince1970(startTimeMillis / 1000.0 + (repeatInterval * repeatCount * 86400.0))
                )
                val recurrenceRule = EKRecurrenceRule(
                    recurrenceWithFrequency = frequency,
                    interval = repeatInterval.toLong(),
                    end = recurrenceEnd
                )
                event.addRecurrenceRule(recurrenceRule)
            }

            dispatch_async(dispatch_get_main_queue()) {
                val editVC = EKEventEditViewController()
                editVC.event = event
                editVC.eventStore = store

                delegate = EventEditDelegate()
                editVC.editViewDelegate = delegate

                val rootVC = UIApplication.sharedApplication.keyWindow?.rootViewController
                rootVC?.presentViewController(editVC, animated = true, completion = null)
            }
        }
    }
}