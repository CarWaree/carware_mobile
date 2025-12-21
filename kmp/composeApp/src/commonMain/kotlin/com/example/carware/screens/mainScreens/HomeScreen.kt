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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import carware.composeapp.generated.resources.Res
import carware.composeapp.generated.resources.audi
import carware.composeapp.generated.resources.notification
import carware.composeapp.generated.resources.person
import carware.composeapp.generated.resources.poppins_medium
import carware.composeapp.generated.resources.poppins_semibold
import com.example.carware.m
import com.example.carware.network.apiResponse.vehicle.Vehicles
import com.example.carware.screens.CarCard
import com.example.carware.screens.OBDCard
import com.example.carware.screens.UpcomingMaintenance
import com.example.carware.screens.appGradBack
import com.example.carware.viewModel.home.HomeScreenState
import com.example.carware.viewModel.home.HomeScreenViewModel
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource


@Composable
fun HomeScreen(navController: NavController, viewModel: HomeScreenViewModel) {
    val popSemi = FontFamily(Font(Res.font.poppins_semibold))
    val popMid = FontFamily(Font(Res.font.poppins_medium))
    val scrollState = rememberScrollState()

    val _state by viewModel.state.collectAsState()
    val state = _state

    val username = when (state) {
        is HomeScreenState.Success -> state.cars.firstOrNull()?.userName ?: "User"
        else -> "Guest"
    }

    Column(
        m
            .fillMaxSize()
            .appGradBack()

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 50.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(Res.drawable.person),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = m.size(25.dp)
                ) //profile icon
                Spacer(modifier = m.padding(horizontal = 4.dp))
                Text(
                    "Welcome Back \n $username",
                    fontFamily = popSemi,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(217, 217, 217, 255)
                ) // welcome back
            }
            Spacer(modifier = m.padding(horizontal = 8.dp))

            Icon(
                painter = painterResource(Res.drawable.notification),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = m
                    .size(25.dp)

            ) //notifications


        }
        Column(
            m.fillMaxSize()
                .clip(RoundedCornerShape(70.dp, 70.dp, 0.dp, 0.dp))
                .verticalScroll(scrollState)
                .background(Color(217, 217, 217, 255)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {when (val currentState = state) {
                is HomeScreenState.Loading -> {
                    Spacer(modifier = m.padding(vertical = 50.dp))
                    Text("Loading Car Data...")
                }
                is HomeScreenState.Error -> {
                    Spacer(modifier = m.padding(vertical = 50.dp))
                    Text("Error: ${currentState.message}", color = Color.Red)
                }
                is HomeScreenState.Success -> {
                    // 🚨 LOGIC CHANGE: Pass the whole list to the Pager content
                    SuccessCarPagerContent(currentState.cars)
                }
            }
            }
            Spacer(modifier = m.padding(vertical = 16.dp))
            UpcomingMaintenance()
            Spacer(modifier = m.padding(vertical = 12.dp))
            Row(m.padding(horizontal = 12.dp))

            { OBDCard(onClick = {/* more details logic*/ }) }

            Spacer(modifier = m.padding(vertical = 64.dp))


        }
    }
}

@Composable
fun SuccessCarPagerContent(cars: List<Vehicles>) {
    // Initialize pager state with the number of cars
    val pagerState = rememberPagerState(pageCount = { cars.size })

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = m.padding(vertical = 16.dp))

        HorizontalPager(
            state = pagerState,
            modifier = m.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) { page ->
            val car = cars[page]
            Box(modifier = m.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CarCard(
                    brand = car.brandName,
                    model = car.modelName,
                    modelYear = car.year.toString(),
                    color = car.color,
                    image = Res.drawable.audi // Keeping your static image as requested
                )
            }
        }

        //  page indicator
        Row(
            Modifier.fillMaxWidth().padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(cars.size) { iteration ->
                val color = if (pagerState.currentPage == iteration)
                    Color(194, 0, 0, 255)
                else Color.Transparent
                Box(
                    modifier = m
                        .padding(4.dp)
                        .clip(shape= CircleShape)
                        .background(color).size(10.dp)
                        .border(shape= CircleShape, width = 1.dp, color =  Color(194, 0, 0, 255))
                )
            }
        }
    }
}