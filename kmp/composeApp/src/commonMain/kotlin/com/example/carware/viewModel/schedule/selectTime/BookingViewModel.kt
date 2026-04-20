//package com.example.carware.viewModel.schedule.selectTime
//
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewmodel.compose.viewModel
//
//class BookingViewModel : ViewModel() {
//    // --- Calendar State ---
//    var currentMonthIndex by mutableStateOf(10) // November
//    var currentYear by mutableStateOf(2025)
//    var selectedDay by mutableStateOf<String?>(null)
//
//    var isTimePickerVisible by mutableStateOf(false)
//
//    val months = listOf(
//        "January", "February", "March", "April", "May", "June",
//        "July", "August", "September", "October", "November", "December"
//    )
//
//    // --- Time Slot State ---
//    val morningSlots = mutableStateListOf<SelectTimeState>()
//    val eveningSlots = mutableStateListOf<SelectTimeState>()
//
//    init {
//        // Initialize your time slots
//        loadInitialTimeSlots()
//    }
//
//    private fun loadInitialTimeSlots() {
//        morningSlots.clear()
//        morningSlots.addAll(
//            listOf(
//                SelectTimeState("10:00 AM"),
//                SelectTimeState("10:30 AM"),
//                SelectTimeState("11:00 AM"),
//                SelectTimeState("11:30 AM"),
//                SelectTimeState("12:00 PM"),
//                SelectTimeState("12:30 PM"),
//                SelectTimeState("1:30 PM"),
//                SelectTimeState("2:00 PM"),
//                SelectTimeState("3:00 PM"),
//                SelectTimeState("3:30 PM"),
//                SelectTimeState("4:00 PM"),
//                SelectTimeState("4:30 PM"),
//                SelectTimeState("5:00 PM"),
//                SelectTimeState("5:30 PM"),
//
//                )
//        )
//
//        eveningSlots.clear()
//        eveningSlots.addAll(
//            listOf(
//                SelectTimeState("6:00 PM"),
//                SelectTimeState("6:30 PM"),
//                SelectTimeState("7:00 PM"),
//                SelectTimeState("7:30 PM"),
//                SelectTimeState("8:00 PM"),
//                SelectTimeState("8:30 PM"),
//                SelectTimeState("9:00 PM"),
//                SelectTimeState("9:30 PM"),
//                SelectTimeState("10:00 PM"),
//                SelectTimeState("10:30 PM"),
//                SelectTimeState("11:00 PM"),
//                SelectTimeState("11:30 PM"),
//                SelectTimeState("12:00 AM"),
//            )
//        )
//    }
//
//    // --- Actions ---
//    fun onDayClick(day: String) {
//        selectedDay = day
//        isTimePickerVisible = true  // This opens the pop-up
//    }
//
//    fun onTimeClick(time: String) {
//        updateSelection(morningSlots, time)
//        updateSelection(eveningSlots, time)
//    }
//
//    private fun updateSelection(list: MutableList<SelectTimeState>, time: String) {
//        for (i in list.indices) {
//            list[i] = list[i].copy(isSelected = list[i].time == time)
//        }
//    }
//
//    fun changeMonth(forward: Boolean) {
//        if (forward) {
//            if (currentMonthIndex < 11) currentMonthIndex++ else { currentMonthIndex = 0; currentYear++ }
//        } else {
//            if (currentMonthIndex > 0) currentMonthIndex-- else { currentMonthIndex = 11; currentYear-- }
//        }
//    }
//
//    fun getSelectedTime(): String? {
//        val morningSelection = morningSlots.find { it.isSelected }?.time
//        val eveningSelection = eveningSlots.find { it.isSelected }?.time
//        return morningSelection ?: eveningSelection
//    }
//
//    fun getFinalSelectionString(): String? {
//        val time = getSelectedTime()
//        return if (selectedDay != null && time != null) {
//            // Example format: "November 15, 2025 at 10:30 AM"
//            "${months[currentMonthIndex]} $selectedDay, $currentYear at $time"
//
//        } else {
//            null
//        }
//
//    }
//
//    fun confirmTimeSelection() {
//        if (getSelectedTime() != null) {
//            isTimePickerVisible = false // This collapses the pop-up
//        }
//    }
//}
//
//
//
