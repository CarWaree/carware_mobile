package com.example.carware.screens.reminder

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import carware.composeapp.generated.resources.Res
import carware.composeapp.generated.resources.arrow_left
import carware.composeapp.generated.resources.history
import carware.composeapp.generated.resources.poppins
import carware.composeapp.generated.resources.poppins_medium
import carware.composeapp.generated.resources.poppins_semibold
import carware.composeapp.generated.resources.reminder_note_resize
import com.example.carware.LocalStrings
import com.example.carware.m
import com.example.carware.navigation.ReminderHistoryScreen
import com.example.carware.network.apiResponse.schedule.Service
import com.example.carware.screens.CalenderBox
import com.example.carware.screens.ConfirmSchedule
import com.example.carware.screens.SelectDateBox
import com.example.carware.screens.SelectDropdown
import com.example.carware.screens.ShimmerScheduleScreen
import com.example.carware.screens.ToastMessage
import com.example.carware.screens.UsersCar
import com.example.carware.screens.appButtonBack
import com.example.carware.util.storage.PreferencesManager
import com.example.carware.viewModel.reminder.reminderScreen.ReminderScreenViewModel
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource


@Composable
fun ReminderScreen(
    navController: NavController,
    viewModel: ReminderScreenViewModel,
    preferencesManager: PreferencesManager,

    ) {
    val pop = FontFamily(Font(Res.font.poppins))

    val primaryGradientBrush = Brush.linearGradient(
        listOf(Color(194, 0, 0, 255), Color(92, 0, 0, 255))
    )

    val state by viewModel.state.collectAsState()

    val textFieldColors = TextFieldDefaults.colors(

        unfocusedTextColor = Color.DarkGray,
        errorTextColor = Color(194, 0, 0, 255),

        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,


        cursorColor = Color(194, 0, 0, 255),
        focusedIndicatorColor = Color(
            118, 118, 118, 255
        ),    // underline/border when focused
        unfocusedIndicatorColor = Color(
            118, 118, 118, 255
        ),  // underline/border when not focused
        errorIndicatorColor = Color(194, 0, 0, 255),
        focusedTextColor = Color(0, 0, 0, 255)


    )

    val popSemi = FontFamily(Font(Res.font.poppins_semibold))
    val popMid = FontFamily(Font(Res.font.poppins_medium))
    val pageScrollState = rememberScrollState()
    val selectCarScrollState = rememberScrollState()
    val strings = LocalStrings.current
    var showConfirmDialog by remember { mutableStateOf(false) }

// Selected Car
    val carName = state.availableCars.find { it.id == state.selectedCarId }?.let { car ->
        "${car.brandName} ${car.modelName} ${car.year}"
    } ?: ""

    val selectedService = state.selectedServiceName.toString()
    val months = listOf(
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    )
    val summaryDate = if (state.selectedDay != null && state.selectedTime != null) {
        "${months[state.currentMonthIndex]} ${state.selectedDay}, ${state.currentYear} at ${state.selectedTime}"
    } else null
    var selectRepeatCountExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(state.error) {
        if (state.error != null) {
            delay(3000)
            viewModel.clearErrorMessage()
        }
    }
    LaunchedEffect(state.isBookingSuccessMessage) {
        if (state.isBookingSuccessMessage != null) {

            delay(3000)
            viewModel.clearErrorMessage()
        }
    }


    if (state.isTimePickerVisible) {
        Column(m.fillMaxSize()) {
            SelectDateBox(
                availableSlots = state.availableSlots,
                onSlotClick = { viewModel.selectTimeSlot(it) },
                onConfirm = { viewModel.confirmTimeSelection() }
            )
        }
    } else if (state.isLoading) {
        ShimmerScheduleScreen()
    } else if (showConfirmDialog) {
        ConfirmSchedule(
            reminderViewmodel = viewModel,
            onDismiss = { showConfirmDialog = false },
            carName = carName,
            selectedService = selectedService,
            selectedDate = "$summaryDate",
            selectedRepeatUnit = state.repeatUnit,
            selectedRepeatCount = state.repeatCount.toString(),
            selectedRepeatInterval = state.repeatInterval.toString(),
            onConfirm = { viewModel.setReminder() }
        )
    } else {

        Column(
            m
                .fillMaxSize()
                .background(Color(217, 217, 217, 255))
                .padding(vertical = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            AnimatedVisibility(
                visible = state.error != null,
                enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
                modifier = Modifier.padding(top = 20.dp)
            ) {
                state.error?.let { msg ->
                    ToastMessage(message = msg, state = false)

                }
            }
            AnimatedVisibility(
                visible = state.isBookingSuccessMessage != null,
                enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
                modifier = Modifier.padding(top = 20.dp)
            ) {
                state.isBookingSuccessMessage?.let { msg ->
                    ToastMessage(message = "${state.isBookingSuccessMessage}", state = true)


                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(10.dp),

                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = painterResource(Res.drawable.arrow_left),
                    contentDescription = strings.get("BACK"),
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { navController.popBackStack() }
                        .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                        .drawWithContent {
                            drawContent()
                            drawRect(primaryGradientBrush, blendMode = BlendMode.SrcIn)
                        },
                    tint = Color.White
                ) //return arrow
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = strings.get("ADD_REMINDER"),
                    fontFamily = pop,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Medium,
                    style = TextStyle(brush = primaryGradientBrush)
                ) //top screen title
                Spacer(modifier = Modifier.weight(1.2f))

                Icon(
                    painter = painterResource(Res.drawable.history),
                    contentDescription = strings.get("HISTORY"),
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { navController.navigate(ReminderHistoryScreen) }
                        .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                        .drawWithContent {
                            drawContent()
                            drawRect(primaryGradientBrush, blendMode = BlendMode.SrcIn)
                        },
                    tint = Color.Unspecified
                ) //reminder history
            }

            Spacer(m.height(32.dp))

            Column(
                m.fillMaxSize()
                    .verticalScroll(pageScrollState)
            ) {
                // ============ SELECT CAR SECTION ============
                Text(
                    strings.get("SELECT_CAR"),
                    fontFamily = popSemi,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            listOf(
                                Color(194, 0, 0, 255),
                                Color(92, 0, 0, 255)
                            )
                        ),
                    ),
                    modifier = m.padding(horizontal = 16.dp)
                ) //select car
                Spacer(m.height(22.dp))
                Column(
                    m
                        .height(140.dp)
                        .verticalScroll(selectCarScrollState)
                ) {
                    state.availableCars.forEach { car ->
                        UsersCar(
                            brand = car.brandName,
                            model = car.modelName,
                            isSelected = state.selectedCarId == car.id,
                            onClick = {
                                viewModel.selectVehicle(car.id)
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                Spacer(m.height(18.dp))

                // ============ SELECT SERVICES SECTION ============
                Text(
                    strings.get("SELECT_SERVICES"),
                    fontFamily = popSemi,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            listOf(
                                Color(194, 0, 0, 255),
                                Color(92, 0, 0, 255)
                            )
                        ),
                    ),
                    modifier = m.padding(horizontal = 16.dp)
                )
                Spacer(m.height(6.dp))

                Box(m.padding(horizontal = 26.dp)) {
                    SelectDropdown(
                        label = strings.get("SELECT_SERVICE"),
                        selectedValue = state.selectedServiceName ?: "",
                        options = state.availableServicesTypes.map { it.name },
                        onSelect = { selectedName ->
                            val serviceType =
                                state.availableServicesTypes.firstOrNull { it: Service ->
                                    it.name == selectedName
                                }
                            // ============ CHANGE: Add it.id as second parameter ============
                            serviceType?.let { viewModel.selectServiceType(it.id, it.name) }
                        },
                    )
                }

                Spacer(m.height(18.dp))
                val intervals = listOf(3, 6, 9)
                val units = listOf("Day", "Month", "Year")
                Text(
                    "Repeat Every :",
                    fontFamily = popSemi,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            listOf(
                                Color(194, 0, 0, 255),
                                Color(92, 0, 0, 255)
                            )
                        ),
                    ), modifier = m.padding(horizontal = 16.dp),)
                Spacer(m.height(6.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .size(45.dp),
                        value = state.repeatInterval.toString(),
                        onValueChange = { viewModel.setRepeatInterval(it) },
                        keyboardOptions = KeyboardOptions.run {
                            Default.copy(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = textFieldColors
                    )

                    Box {
                        OutlinedButton(
                            modifier = Modifier
                                .width(90.dp),
                            onClick = { selectRepeatCountExpanded = true },
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            Text(state.repeatUnit?.ifEmpty { "Select Unit" } ?: "Select Unit")
                        }
                        DropdownMenu(
                            containerColor = Color(217, 217, 217).copy(alpha = 0.8f),
                            shape = RoundedCornerShape(8.dp),
                            expanded = selectRepeatCountExpanded,
                            onDismissRequest = { selectRepeatCountExpanded = false }
                        ) {
                            units.forEach { unit ->
                                DropdownMenuItem(
                                    text = { Text(unit,
                                        fontFamily = popSemi,
                                        fontSize = 14.sp,
                                        color = Color(0, 0, 0, 255),
                                        fontWeight = FontWeight.W400,
                                    ) },
                                    onClick = {
                                        viewModel.setRepeatUnit(unit)
                                        selectRepeatCountExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Text(
                        "for",
                        fontFamily = popSemi,
                        fontSize = 14.sp,
                        color = Color(0, 0, 0, 255),
                        fontWeight = FontWeight.W400,
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .size(45.dp),
                        value = state.repeatCount.toString(),
                        onValueChange = { viewModel.setRepeatCount(it) },
                        keyboardOptions = KeyboardOptions.run {
                            Default.copy(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = textFieldColors
                    )

                    Text(
                        "Times",
                        fontFamily = popSemi,
                        fontSize = 12.sp,
                        color = Color(0, 0, 0, 255),
                        fontWeight = FontWeight.W400,
                    )
                }

//
                Spacer(m.height(18.dp))
                // ============ NOTES  SECTION ============

                var noteBoxHeight by remember { mutableStateOf(110.dp) }
                val minHeight = 80.dp
                val maxHeight = 300.dp

                Text(
                    text = strings.get("NOTE_LABEL"),
                    fontFamily = popSemi,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            listOf(
                                Color(0xFFC20000),
                                Color(0xFF5C0000)
                            )
                        )
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                ) // notes label


                val density = LocalDensity.current

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(noteBoxHeight)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFE0E0E0))
                        .border(
                            width = 1.dp,
                            color = Color(0xFFBDBDBD),
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    BasicTextField(
                        value = state.note,
                        onValueChange = { viewModel.updateNote(it) },
                        textStyle = TextStyle(
                            fontFamily = popMid,
                            fontSize = 13.sp,
                            color = Color(0xFF3C3C3C)
                        ),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        decorationBox = { innerTextField ->
                            if (state.note.isEmpty()) {
                                Text(
                                    text = strings.get("ADD_NOTE_PLACEHOLDER"),
                                    fontFamily = popMid,
                                    fontSize = 13.sp,
                                    color = Color(0xFF9E9E9E)
                                )
                            }
                            innerTextField()
                        }
                    )

                    // ── Resize handle icon (bottom-right) — drag vertically to resize ──

                    Icon(
                        painter = painterResource(Res.drawable.reminder_note_resize),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(8.dp)
                            .size(16.dp)
                            .pointerInput(Unit) {
                                detectVerticalDragGestures { _, dragAmount ->
                                    val deltaDp = with(density) { dragAmount.toDp() }
                                    noteBoxHeight = (noteBoxHeight + deltaDp)
                                        .coerceIn(minHeight, maxHeight)
                                }
                            },
                    )


                } //Text field box

                // ============ CALENDAR SECTION ============
                Column(
                    m.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CalenderBox(
                        currentMonthIndex = state.currentMonthIndex,
                        currentYear = state.currentYear,
                        selectedDay = state.selectedDay,
                        preferencesManager = preferencesManager,
                        onChangeMonth = { viewModel.changeMonth(it) },
                        onChangeYear = { viewModel.changeYear(it) },
                        onSelectDay = { viewModel.selectDay(it) }
                    )
                }

                Spacer(m.height(20.dp))

                // ============ CONFIRM BUTTON & DETAILS SECTION ============


                // Confirm Button
                Card(
                    onClick = {
                        if (viewModel.isValid()) {
                            showConfirmDialog = true
                        } else {
                            state.error
                        }

                    },
                    modifier = m
                        .fillMaxWidth(0.8f)
                        .height(45.dp)
                        .align(Alignment.CenterHorizontally)
                        .border(
                            width = 1.dp,
                            color = Color(30, 30, 30, 110),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clip(shape = RoundedCornerShape(8.dp))
                        .appButtonBack()
                        .padding(horizontal = 26.dp),

                    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                ) {
                    Row(
                        modifier = m.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = strings.get("ADD_REMINDER"),
                            fontFamily = popSemi,
                            fontSize = 18.sp,
                            color = Color(217, 217, 217, 255)
                        )
                    }
                }

                Spacer(m.height(100.dp))


            }
        }
    }
}
