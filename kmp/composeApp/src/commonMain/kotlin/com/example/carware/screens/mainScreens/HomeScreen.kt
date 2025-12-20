package com.example.carware.screens.mainScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import carware.composeapp.generated.resources.Res
import carware.composeapp.generated.resources.poppins_medium
import carware.composeapp.generated.resources.poppins_semibold
import com.example.carware.m
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.ExperimentalResourceApi
import carware.composeapp.generated.resources.upline
import carware.composeapp.generated.resources.person
import carware.composeapp.generated.resources.dots
import carware.composeapp.generated.resources.audi
import carware.composeapp.generated.resources.car
import carware.composeapp.generated.resources.color
import carware.composeapp.generated.resources.modelyear
import carware.composeapp.generated.resources.cuate
import carware.composeapp.generated.resources.arrow_1
import com.example.carware.screens.appButtonBack
import com.example.carware.screens.appGradBack
import com.example.carware.screens.cardGradBack


@Composable
fun HomeScreen(navController: NavController) {
    val popSemi = FontFamily(Font(Res.font.poppins_semibold))
    val popMid = FontFamily(Font(Res.font.poppins_medium))
    val scrollState = rememberScrollState()

    Column(
        m
            .fillMaxSize()
            .appGradBack()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp, vertical = 32.dp),
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
                    "Welcome Back \n username",
                    fontFamily = popSemi,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(217, 217, 217, 255)
                ) // welcome back
            }
            Spacer(modifier = m.padding(horizontal = 8.dp))

            Icon(
                painter = painterResource(Res.drawable.person),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = m
                    .size(25.dp)

            ) //notifications

        }
    }
}


