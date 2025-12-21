package com.example.carware.screens.mainScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import carware.composeapp.generated.resources.Res
import carware.composeapp.generated.resources.poppins_medium
import carware.composeapp.generated.resources.poppins_semibold
import com.example.carware.m
import com.example.carware.network.apiResponse.schedule.Service
import com.example.carware.screens.SelectDropdown
import com.example.carware.screens.UsersCar
import com.example.carware.viewModel.schedule.ScheduleScreenViewModel
import org.jetbrains.compose.resources.Font

@Composable
fun ScheduleScreen(
    navController: NavController,
    viewModel: ScheduleScreenViewModel
) {

    val state by viewModel.state.collectAsState()

    val popSemi = FontFamily(Font(Res.font.poppins_semibold))
    val popMid = FontFamily(Font(Res.font.poppins_medium))
    val pageScrollState = rememberScrollState()
    val selectCarScrollState = rememberScrollState()


    Column(
        m
            .fillMaxSize()
            .background(Color(217, 217, 217, 255)),
    )
    {
        Text(
            "Schedule Service",
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

            ) // schedule service text
        Spacer(m.height(32.dp))
        Text(
            "Select Your Car",
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
            ), modifier = m.padding(horizontal = 16.dp)
        ) // select car text
        Spacer(m.height(22.dp))
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
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

        Text(
            "Select Services",
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
            ), modifier = m.padding(horizontal = 16.dp)
        ) //select services text
        Spacer(m.height(6.dp))
        Box(m.padding(horizontal = 26.dp)) {
            SelectDropdown(
                label = "select your Service",
                selectedValue = state.selectedService,
                options = state.availableServicesTypes.map { it.name },
                onSelect = { selectedName ->
                    // Specify (it: Service) here as well
                    val serviceType = state.availableServicesTypes.firstOrNull { it: Service ->
                        it.name == selectedName
                    }
                    serviceType?.let { viewModel.selectServiceType(it.name) }
                },
            )
        } // select service  dropdown
        Spacer(m.height(18.dp))
        Text(
            "Select Provider",
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
            ), modifier = m.padding(horizontal = 16.dp)
        ) //Select Provider text
        Spacer(m.height(6.dp))
        Box(m.padding(horizontal = 26.dp)) {
            SelectDropdown(
                label = "select your Provider",
                selectedValue = state.selectedCenter,
                options = state.availableCenters.mapNotNull { it.name } ,
                onSelect = { name ->
                    state.availableCenters.firstOrNull { it.name == name }?.let {
                        viewModel.selectCenter(it)
                    }
                }
            )

        }     // select Center dropdown





    }
}

//@Preview
//@Composable
//fun Prev() {
//    ScheduleScreen()
//}
