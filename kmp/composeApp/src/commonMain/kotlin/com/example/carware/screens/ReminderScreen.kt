package com.example.carware.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import carware.composeapp.generated.resources.Res
import carware.composeapp.generated.resources.poppins_medium
import carware.composeapp.generated.resources.poppins_semibold
import com.example.carware.LocalStrings
import com.example.carware.m
import com.example.carware.network.apiResponse.schedule.Service
import com.example.carware.screens.CalenderBox
import com.example.carware.screens.LoadingOverlay
import com.example.carware.screens.SelectDateBox
import com.example.carware.screens.SelectDropdown
import com.example.carware.screens.ShimmerScheduleScreen
import com.example.carware.screens.UsersCar
import com.example.carware.screens.appButtonBack
import com.example.carware.util.storage.PreferencesManager
import com.example.carware.viewModel.schedule.screen.ScheduleScreenViewModel
import kotlinx.coroutines.awaitCancellation
import org.jetbrains.compose.resources.Font
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import carware.composeapp.generated.resources.arrow_1
import carware.composeapp.generated.resources.arrow_left
import carware.composeapp.generated.resources.home_line
import carware.composeapp.generated.resources.poppins
import carware.composeapp.generated.resources.reminder_note_resize
import carware.composeapp.generated.resources.schedule
import carware.composeapp.generated.resources.success
import org.jetbrains.compose.resources.painterResource


@Composable
fun ReminderScreen(
    navController: NavController,
    viewModel: ScheduleScreenViewModel,
    preferencesManager: PreferencesManager,

    ) {
    val pop = FontFamily(Font(Res.font.poppins))

    val primaryGradientBrush = Brush.linearGradient(
        listOf(Color(194, 0, 0, 255), Color(92, 0, 0, 255))
    )

    val state by viewModel.state.collectAsState()

    val popSemi = FontFamily(Font(Res.font.poppins_semibold))
    val popMid = FontFamily(Font(Res.font.poppins_medium))
    val pageScrollState = rememberScrollState()
    val selectCarScrollState = rememberScrollState()
    val strings = LocalStrings.current
    val lifecycleOwner = LocalLifecycleOwner.current

//    LaunchedEffect(Unit) {
//        viewModel.loadInitialData()
//        viewModel.loadInitialTimeSlots()
//    }

    if (state.isTimePickerVisible) {
        Column(m.fillMaxSize()) {
            SelectDateBox(viewModel = viewModel)
        }
    } else if (state.isInitialLoading) {
        ShimmerScheduleScreen()
    } else {
        Column(
            m
                .fillMaxSize()
                .verticalScroll(pageScrollState)
                .background(Color(217, 217, 217, 255))
                .padding(vertical = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Res.drawable.arrow_left),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { navController.popBackStack() }
                        .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                        .drawWithContent {
                            drawContent()
                            drawRect(primaryGradientBrush, blendMode = BlendMode.SrcIn)
                        },
                    tint = Color.White
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = " Add Reminder",
                    fontFamily = pop,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Medium,
                    style = TextStyle(brush = primaryGradientBrush)
                )
                Spacer(modifier = Modifier.weight(1.2f))
            }

            Spacer(m.height(32.dp))

            Column(m.fillMaxSize()) {
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
                )
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
                    strings.get("SELECT_SERVICE"),
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
                        selectedValue = state.selectedService.toString(),
                        options = state.availableServicesTypes.map { it.name },
                        onSelect = { selectedName ->
                            val serviceType =
                                state.availableServicesTypes.firstOrNull { it: Service ->
                                    it.name == selectedName
                                }
                            // ============ CHANGE: Add it.id as second parameter ============
                            serviceType?.let { viewModel.selectServiceType(it.name, it.id) }
                        },
                    )
                }

                Spacer(m.height(18.dp))

                // ============ NOTES  SECTION ============

                var note by remember { mutableStateOf("") }
                var noteBoxHeight by remember { mutableStateOf(110.dp) }
                val minHeight = 80.dp
                val maxHeight = 300.dp

// ── "Note" label ──────────────────────────────────────────────────────
                Text(
                    text = "Note",
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
                )

// ── Text field box ────────────────────────────────────────────────────
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
                        value = note,
                        onValueChange = { note = it },
                        textStyle = TextStyle(
                            fontFamily = popMid,
                            fontSize = 13.sp,
                            color = Color(0xFF3C3C3C)
                        ),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        decorationBox = { innerTextField ->
                            if (note.isEmpty()) {
                                Text(
                                    text = "Add your note",
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


                }

                // ============ CALENDAR SECTION ============
                CalenderBox(
                    viewModel,
                    preferencesManager = preferencesManager
                )

                Spacer(m.height(20.dp))

                // ============ CONFIRM BUTTON & DETAILS SECTION ============
                Column(
                    m
                        .fillMaxWidth()
                        .padding(horizontal = 26.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Display error if any
                    if (state.error != null) {
                        Text(
                            text = state.error ?: "",
                            color = Color.Red,
                            fontSize = 12.sp,
                            fontFamily = popSemi,
                            modifier = m.padding(bottom = 8.dp)
                        )
                    }

//                    // Display loading state
//                    if (state.isLoading) {
//                        Text(
//                            text = strings.get("PROCESSING"),
//                            fontFamily = popSemi,
//                            fontSize = 14.sp,
//                            color = Color(194, 0, 0, 255)
//                        )
//                        Spacer(m.height(8.dp))
//                    }

                    // Confirm Button
                    Card(
                        onClick = {
                            if (viewModel.isAppointmentValid()) {
                                viewModel.confirmAppointment()
                            }
                        },
                        modifier = m
                            .fillMaxWidth(0.8f)
                            .height(45.dp)
                            .border(
                                width = 1.dp,
                                color = Color(30, 30, 30, 110),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clip(shape = RoundedCornerShape(8.dp))
                            .appButtonBack(),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                    ) {
                        Row(
                            modifier = m.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Add Reminder",
                                fontFamily = popSemi,
                                fontSize = 18.sp,
                                color = Color(217, 217, 217, 255)
                            )
                        }
                    }



                    Spacer(m.height(4.dp))

                }
            }
        }
    }
}