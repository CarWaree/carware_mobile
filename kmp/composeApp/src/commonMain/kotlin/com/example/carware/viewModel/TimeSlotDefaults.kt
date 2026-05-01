package com.example.carware.viewModel

import com.example.carware.viewModel.schedule.screen.TimeSlot

 fun defaultSlots() = listOf(
    TimeSlot("10:00 AM", isAvailable = false),
    TimeSlot("10:30 AM"),
    TimeSlot("11:00 AM"),
    TimeSlot("11:30 AM", isAvailable = false),
    TimeSlot("12:00 PM"),
    TimeSlot("12:30 PM"),
    TimeSlot("1:30 PM"),
    TimeSlot("2:00 PM", isAvailable = false),
    TimeSlot("3:00 PM"),
    TimeSlot("3:30 PM"),
    TimeSlot("4:00 PM"),
    TimeSlot("4:30 PM"),
    TimeSlot("5:00 PM", isAvailable = false),
    TimeSlot("5:30 PM"),
    TimeSlot("6:00 PM", isAvailable = false),
    TimeSlot("6:30 PM"),
    TimeSlot("7:00 PM"),
    TimeSlot("7:30 PM"),
    TimeSlot("8:00 PM"),
    TimeSlot("8:30 PM", isAvailable = false),
    TimeSlot("9:00 PM"),
    TimeSlot("9:30 PM"),
    TimeSlot("10:00 PM", isAvailable = false),
    TimeSlot("10:30 PM"),
    TimeSlot("11:00 PM"),
    TimeSlot("11:30 PM", isAvailable = false),
    TimeSlot("12:00 AM"),
)
