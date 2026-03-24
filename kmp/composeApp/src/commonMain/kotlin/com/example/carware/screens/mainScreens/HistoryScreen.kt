package com.example.carware.screens.mainScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import carware.composeapp.generated.resources.Res
import carware.composeapp.generated.resources.arrow_1
import carware.composeapp.generated.resources.eyee
import carware.composeapp.generated.resources.home
import carware.composeapp.generated.resources.new_logo
import carware.composeapp.generated.resources.poppins_medium
import carware.composeapp.generated.resources.poppins_semibold
import carware.composeapp.generated.resources.success
import com.example.carware.m
import com.example.carware.screens.ServiceHistoryCard
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HistoryScreen(navController: NavController) {


    val popSemi = FontFamily(Font(Res.font.poppins_semibold))
    val popMid = FontFamily(Font(Res.font.poppins_medium))
    val pageScrollState = rememberScrollState()
    val selectCarScrollState = rememberScrollState()
    val filterOptions = listOf("All", "Oil Change", "Tires", "Maintenance", "Brakes")


    Column(
        m
            .fillMaxSize()
            .verticalScroll(pageScrollState)
            .background(Color(217, 217, 217, 255)),
    )
    {
        Spacer(m.height(35.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(217, 217, 217, 255))
                .padding(horizontal = 32.dp, vertical = 36.dp)
        ) {
            // Back icon at the start


            // Centered text
            Text(
                "Service History",
                fontFamily = popMid,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(130, 21, 21, 255),
                modifier = Modifier.align(Alignment.Center)
            )

            Icon(
                painter = painterResource(Res.drawable.eyee),
                contentDescription = null,
                tint = Color(255, 0, 0, 255),
                modifier = Modifier
                    .size(26.dp)
                    .rotate(180f)
                    .align(Alignment.CenterEnd)
            )
        }


//        Row( modifier = m
//            .fillMaxWidth()
//            .padding(top = 45.dp, start = 16.dp, end = 16.dp),
//
//        )
//        {
//            Text(
//                "service history",
//                fontFamily = popSemi,
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold,
//                style = TextStyle(
//                    brush = Brush.linearGradient(
//                        listOf(
//                            Color(194, 0, 0, 255),
//                            Color(92, 0, 0, 255)
//                        )
//                    ),
//                ),
//                modifier = m
//                    .fillMaxWidth(0.5f)
//
//                )
//            Icon(
//                painter = painterResource(Res.drawable.home),
//                contentDescription = null,
//                tint = Color(211, 203, 203, 255),
//                modifier = m.scale(0.7f)
//
//            )
//        }
//   ////////////////////// schedule service text


        Spacer(m.height(32.dp))
        Column(
            m
                .fillMaxSize()
        )
        {

            Spacer(m.height(150.dp))


        }
    }
}

// ServiceRecordScreen.kt

@Composable
fun ServiceRecordScreen(
    navController: NavController,

) {


    val popSemi = FontFamily(Font(Res.font.poppins_semibold))
    val popMid = FontFamily(Font(Res.font.poppins_medium))


    val gradientBrush = Brush.linearGradient(
        listOf(Color(194, 0, 0, 255), Color(92, 0, 0, 255))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(217, 217, 217, 255))
            .verticalScroll(rememberScrollState())
    ) {

        // ── Top Bar ──────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 45.dp, start = 16.dp, end = 16.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.arrow_1),
                contentDescription = "Back",
                tint = Color(25, 25, 25),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(24.dp)

            )
            Text(
                text = "Service Record",
                fontFamily = popSemi,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(brush = gradientBrush),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(Modifier.height(24.dp))

        // ── Car Name ─────────────────────────────────────────
        Text(
            text = "carName",
            fontFamily = popSemi,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(brush = gradientBrush),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(16.dp))

        // ── Info Rows ─────────────────────────────────────────
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {

            ServiceRecordRow(
                label = "Service",
                value = "serviceType",
                popMid = popMid
            )
            ServiceRecordRow(
                label = "Date",
                value = "date",
                popMid = popMid
            )
            ServiceRecordRow(
                label = "Provider",
                value = "provider",
                popMid = popMid
            )
            ServiceRecordRow(
                label = "Cost",
                value = "cost",
                popMid = popMid
            )
            ServiceRecordRow(
                label = "Payment",
                value = "paymentMethod",
                popMid = popMid,
                showPaymentIcon = true
            )
        }

        Spacer(Modifier.height(24.dp))

        // ── Service Details ───────────────────────────────────
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "Service Details",
                fontFamily = popSemi,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(25, 25, 25)
            )

            Spacer(Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(200, 200, 200, 255))
                    .padding(14.dp)
            ) {
                Text(
                    text = "serviceDetails",
                    fontSize = 14.sp,
                    color = Color(60, 60, 60),
                    lineHeight = 22.sp
                )
            }
        }

        Spacer(Modifier.height(100.dp))
    }
}

// ── Reusable Row ──────────────────────────────────────────────────
@Composable
fun ServiceRecordRow(
    label: String,
    value: String,
    popMid: FontFamily,
    showPaymentIcon: Boolean = false
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                fontFamily = popMid,
                fontSize = 15.sp,
                color = Color(80, 80, 80),
                modifier = Modifier.weight(1f)
            )
            if (showPaymentIcon) {
                Icon(
                    painter = painterResource(Res.drawable.success),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(20.dp)
                        .padding(end = 4.dp)
                )
            }
            Text(
                text = value,
                fontFamily = popMid,
                fontSize = 15.sp,
                color = Color(25, 25, 25)
            )
        }
        // Divider
        HorizontalDivider(
            color = Color(180, 180, 180),
            thickness = 1.dp
        )
    }
}
@Preview
@Composable
fun ServiceRecordScreenPreview() {
    ServiceRecordScreen(
        navController = rememberNavController(),

    )
}