package com.example.carware.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import carware.composeapp.generated.resources.Res
import carware.composeapp.generated.resources.arrow_left
import carware.composeapp.generated.resources.poppins
import carware.composeapp.generated.resources.poppins_medium
import com.example.carware.LocalStrings
import com.example.carware.m
import com.example.carware.navigation.MyCarsScreen
import com.example.carware.viewModel.mycars.MyCarsScreenState
import com.example.carware.viewModel.mycars.MyCarsScreenViewModel
import com.example.carware.viewModel.profile.ProfileScreenState
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
fun MyCarsScreen(
    navController: NavController,
    viewModel: MyCarsScreenViewModel
) {
    val strings = LocalStrings.current
    val popMid = FontFamily(Font(Res.font.poppins_medium))
    val pop = FontFamily(Font(Res.font.poppins))

    val state by viewModel.state.collectAsState()

    val primaryGradientBrush = Brush.linearGradient(
        listOf(Color(194, 0, 0, 255), Color(92, 0, 0, 255))
    )

    when (state) {
        is MyCarsScreenState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is MyCarsScreenState.Error -> {
            val message = (state as MyCarsScreenState.Error).message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = message)
            }
        }

        is MyCarsScreenState.Success -> {
            val cars = (state as MyCarsScreenState.Success).cars
            val primaryCarId = viewModel.getPrimaryCarId()
            val primaryCar = cars.find { it.id == primaryCarId } ?: cars.firstOrNull()
            val otherCars = cars.filter { it.id != primaryCar?.id }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFD9D9D9))
                    .padding(top = 32.dp)

            ) {
                // Top Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
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
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "My Cars",
                        fontFamily = pop,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Medium,
                        style = TextStyle(brush = primaryGradientBrush)
                    )
                    Spacer(modifier = Modifier.weight(1.2f))
                }

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    thickness = 0.8.dp,
                    color = Color(0x33666666)
                )

                Column(
                    m.padding(horizontal = 20.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = strings.get("MY_PRIMARY_VEHICLE"),
                        fontFamily = pop,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        style = TextStyle(
                            brush = primaryGradientBrush
                        )
                    ) // primary text

                    Spacer(modifier = Modifier.height(12.dp))

                    if (primaryCar != null) {
                        PrimaryCarCard(
                            modelName = primaryCar.modelName,
                            brandName = primaryCar.brandName,
                            modelYear = primaryCar.year.toString(),
                            color = primaryCar.color,
                            isPrimary = true
                        )

                    }
                }
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    thickness = 1.dp,
                    color = Color(0x33666666)
                )
                Column(
                    m
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp)
                        .fillMaxSize()

                ) {
                    otherCars.forEach { car ->
                        PrimaryCarCard(
                            modelName = car.modelName,
                            brandName = car.brandName,
                            modelYear = car.year.toString(),
                            color = car.color,
                            isPrimary = false,
                            onMakePrimary = {
                                viewModel.setPrimaryVehicle(car.id)
                                viewModel.loadCars()
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                    }
                }


            }

        }

    }
}

