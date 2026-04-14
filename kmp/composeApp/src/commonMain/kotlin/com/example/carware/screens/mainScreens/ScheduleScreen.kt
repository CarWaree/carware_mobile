package com.example.carware.screens.mainScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

@Composable
fun ScheduleScreen(
    navController: NavController,
    viewModel: ScheduleScreenViewModel,
    preferencesManager: PreferencesManager,

    ) {

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
                .background(Color(217, 217, 217, 255)),
        ) {
            Text(
                strings.get("SCHEDULE_SERVICE"),
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
                modifier = m
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
                    .padding(top = 45.dp),
            )
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

                // ============ SELECT PROVIDER SECTION ============
                Text(
                    strings.get("SELECT_YOUR_PROVIDER"),
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

                Column(m.padding(horizontal = 26.dp)) {
                    SelectDropdown(
                        label = strings.get("SELECT_YOUR_PROVIDER"),
                        selectedValue = state.selectedCenter.toString(),
                        options = state.availableCenters.mapNotNull { it.name },
                        onSelect = { name ->
                            state.availableCenters.firstOrNull { it.name == name }?.let {
                                viewModel.selectCenter(it)
                            }
                        }
                    )
                }

                Spacer(m.height(18.dp))

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

                    // Display loading state
                    if (state.isLoading) {
                        Text(
                            text = strings.get("PROCESSING"),
                            fontFamily = popSemi,
                            fontSize = 14.sp,
                            color = Color(194, 0, 0, 255)
                        )
                        Spacer(m.height(8.dp))
                    }

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
                                strings.get("CONFIRM_RESERVATION"),
                                fontFamily = popSemi,
                                fontSize = 18.sp,
                                color = Color(217, 217, 217, 255)
                            )
                        }
                    }

                    Spacer(m.height(16.dp))

                    // Reservation Details
                    Text(
                        strings.get("RESERVATION_DETAILS"),
                        fontFamily = popSemi,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W600,
                        color = Color(194, 0, 0, 255)
                    )

                    Spacer(m.height(8.dp))

                    // Date & Time
                    viewModel.getFinalSelectionString()?.let {
                        Text(
                            text = it,
                            fontFamily = popMid,
                            fontSize = 12.sp,
                            color = Color.DarkGray
                        )
                    } ?: run {
                        Text(
                            text = strings.get("no_date/time_selected"),
                            fontFamily = popMid,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                    Spacer(m.height(4.dp))


                    // Selected Service
                    state.selectedService?.let {
                        Text(
                            text = strings.get("SERVICE") + "$it",
                            fontFamily = popMid,
                            fontSize = 12.sp,
                            color = Color.DarkGray
                        )
                    } ?: run {
                        Text(
                            text = strings.get("SERVICE_NOT_SELECTED"),
                            fontFamily = popMid,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                    Spacer(m.height(4.dp))

                    // Selected Center
                    state.selectedCenter?.let {
                        Text(
                            text = strings.get("PROVIDER") + "$it",
                            fontFamily = popMid,
                            fontSize = 12.sp,
                            color = Color.DarkGray
                        )
                    } ?: run {
                        Text(
                            text = strings.get("PROVIDER_NOT_SELECTED"),
                            fontFamily = popMid,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                    Spacer(m.height(4.dp))

                    // Selected Car
                    state.availableCars.find { it.id == state.selectedCarId }?.let { car ->
                        Text(
                            text = "Car: ${car.brandName} ${car.modelName}",
                            fontFamily = popMid,
                            fontSize = 12.sp,
                            color = Color.DarkGray
                        )
                    } ?: run {
                        Text(
                            text = strings.get("CAR_NOT_SELECTED"),
                            fontFamily = popMid,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }

                Spacer(m.height(120.dp))
            }
        }

    }
    if (state.isLoading) {
        LoadingOverlay()
    }
}