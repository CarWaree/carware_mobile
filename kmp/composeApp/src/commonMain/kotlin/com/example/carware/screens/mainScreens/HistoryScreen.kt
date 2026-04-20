package com.example.carware.screens.mainScreens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import carware.composeapp.generated.resources.Res
import carware.composeapp.generated.resources.audi
import carware.composeapp.generated.resources.history_date
import carware.composeapp.generated.resources.history_filter
import carware.composeapp.generated.resources.history_location
import carware.composeapp.generated.resources.history_visa
import carware.composeapp.generated.resources.poppins_medium
import carware.composeapp.generated.resources.poppins_semibold
import carware.composeapp.generated.resources.arrow_1
import com.example.carware.LocalStrings
import com.example.carware.m
import com.example.carware.screens.ShimmerHistoryCard
import com.example.carware.viewModel.history.HistoryScreenState
import com.example.carware.viewModel.history.HistoryScreenViewModel
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun HistoryScreen(
    navController: NavController,
    viewModel: HistoryScreenViewModel,
) {
    val strings = LocalStrings.current
    val popSemi = FontFamily(Font(Res.font.poppins_semibold))
    val popMid = FontFamily(Font(Res.font.poppins_medium))

    val pageScrollState = rememberScrollState()
    val _state by viewModel.state.collectAsState()
    val state = _state

    Column(
        m.fillMaxSize()
            .verticalScroll(pageScrollState)
            .background(Color(217, 217, 217, 255)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(217, 217, 217, 255))
                .padding(horizontal = 28.dp)
                .padding(top = 36.dp)
        ) {
            Text(
                strings.get("SERVICE_HISTORY"),
                fontFamily = popMid,
                fontSize = 26.sp,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        listOf(Color(194, 0, 0, 255), Color(92, 0, 0, 255))
                    )
                ),
                fontWeight = FontWeight.W500,
                modifier = Modifier.align(Alignment.Center)
            )

            Icon(
                painter = painterResource(Res.drawable.history_filter),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(26.dp)
                    .align(Alignment.CenterEnd)
            )
        }
        Spacer(m.height(8.dp))

        Text(
            strings.get("HISTORY_SUBTITLE"),
            fontFamily = popMid,
            fontSize = 12.sp,
            color = Color(102, 102, 102, 204),
            fontWeight = FontWeight.W500,
        )
        Spacer(m.height(4.dp))

        HorizontalDivider(
            color = Color(102, 102, 102, 51),
            thickness = 1.dp
        )

        Spacer(m.height(32.dp))
        
        Column(m.fillMaxSize()) {
            when (state) {
                is HistoryScreenState.Loading -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        repeat(5) {
                            ShimmerHistoryCard()
                        }
                    }
                }

                is HistoryScreenState.Error -> {
                    Spacer(modifier = m.padding(vertical = 50.dp))
                    Text("Error: ${state.message}", color = Color.Red, modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                is HistoryScreenState.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        state.historyItems.forEach { item ->
                            ServiceHistoryCard(
                                carName = item.carName,
                                serviceName = item.providerName,
                                date = item.date,
                                location = "BNU",
                                cost = item.totalPrice,
                                paymentMethod = item.paymentMethod,
                                onClick = {
                                    // navController.navigate("serviceRecord/${item.id}")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ServiceHistoryCard(
    carName: String,
    serviceName: String,
    date: String,
    location: String,
    cost: String,
    paymentMethod: String,
    onClick: () -> Unit
) {
    val popSemi = FontFamily(Font(Res.font.poppins_semibold))
    val popMid = FontFamily(Font(Res.font.poppins_medium))
    val strings = LocalStrings.current

    val gradientBrush = Brush.linearGradient(
        listOf(Color(194, 0, 0, 255), Color(92, 0, 0, 255))
    )

    Column(
        modifier = m
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(210, 210, 210))
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                m.size(38.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color(30, 30, 30, 51), CircleShape)
            ) {
                Image(
                    painter = painterResource(Res.drawable.audi),
                    contentDescription = "Car Logo",
                    modifier = m.size(32.dp)
                        .align(Alignment.Center)
                )
            }

            Spacer(m.width(10.dp))

            Column(modifier = m.weight(1f)) {
                Text(
                    text = carName,
                    fontFamily = popMid,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W300,
                    color = Color(102, 102, 102, 255)
                )
                Text(
                    text = serviceName,
                    fontFamily = popSemi,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500,
                    color = Color(102, 102, 102, 255)
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = cost,
                    fontFamily = popSemi,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500,
                    color = Color(102, 102, 102, 255)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(Res.drawable.history_visa),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(Modifier.width(3.dp))
                    Text(
                        text = paymentMethod,
                        fontFamily = popMid,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W300,
                        color = Color(102, 102, 102, 255)
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(Res.drawable.history_date),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(15.dp)
            )
            Spacer(Modifier.width(6.dp))
            val shortDate = LocalDateTime.parse(date)
            Text(
                text = "${shortDate.dayOfMonth}/${shortDate.monthNumber}/${shortDate.year}",
                fontFamily = popMid,
                fontSize = 16.sp,
                fontWeight = FontWeight.W300,
                color = Color(102, 102, 102, 255)
            )
            Spacer(Modifier.width(16.dp))
            Icon(
                painter = painterResource(Res.drawable.history_location),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(15.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = location,
                fontFamily = popMid,
                fontWeight = FontWeight.W300,
                fontSize = 16.sp,
                color = Color(102, 102, 102, 255)
            )
        }

        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .border(
                    width = 1.5.dp,
                    brush = gradientBrush,
                    shape = RoundedCornerShape(10.dp)
                )
                .clickable { onClick() }
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = strings.get("VIEW_DETAILS"),
                fontFamily = popMid,
                fontSize = 14.sp,
                style = TextStyle(brush = gradientBrush),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ServiceRecordScreen(
    navController: NavController,
) {
    val popSemi = FontFamily(Font(Res.font.poppins_semibold))
    val popMid = FontFamily(Font(Res.font.poppins_medium))
    val strings = LocalStrings.current

    val gradientBrush = Brush.linearGradient(
        listOf(Color(194, 0, 0, 255), Color(92, 0, 0, 255))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(217, 217, 217, 255))
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 45.dp, start = 18.dp, end = 16.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.arrow_1),
                contentDescription = "Back",
                tint = Color.Red,
                modifier = Modifier
                    .rotate(180f)
                    .align(Alignment.CenterStart)
                    .size(24.dp)
                    .clickable { navController.popBackStack() }
            )
            Text(
                text = strings.get("SERVICE_RECORD"),
                fontFamily = popSemi,
                fontSize = 24.sp,
                fontWeight = FontWeight.W500,
                style = TextStyle(brush = gradientBrush),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = "carName",
            fontFamily = popSemi,
            fontSize = 24.sp,
            fontWeight = FontWeight.W500,
            style = TextStyle(brush = gradientBrush),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(16.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            ServiceRecordRow(
                label = strings.get("SERVICE_LABEL"),
                value = "serviceType",
                popMid = popMid
            )
            ServiceRecordRow(
                label = strings.get("DATE_LABEL"),
                value = "date",
                popMid = popMid
            )
            ServiceRecordRow(
                label = strings.get("PROVIDER_LABEL"),
                value = "provider",
                popMid = popMid
            )
            ServiceRecordRow(
                label = strings.get("COST_LABEL"),
                value = "cost",
                popMid = popMid
            )
            ServiceRecordRow(
                label = strings.get("PAYMENT_LABEL"),
                value = "paymentMethod",
                popMid = popMid,
                showPaymentIcon = true
            )
        }

        Spacer(Modifier.height(24.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = strings.get("SERVICE_DETAILS"),
                fontFamily = popSemi,
                fontSize = 24.sp,
                fontWeight = FontWeight.W400,
                color = Color(102, 102, 102, 255)
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
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W400,
                    color = Color(102, 102, 102, 191),
                    lineHeight = 22.sp
                )
            }
        }

        Spacer(Modifier.height(100.dp))
    }
}

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
                fontWeight = FontWeight.W400,
                fontSize = 24.sp,
                color = Color(102, 102, 102, 255),
                modifier = Modifier.weight(1f)
            )
            if (showPaymentIcon) {
                Icon(
                    painter = painterResource(Res.drawable.history_visa),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(20.dp)
                        .padding(end = 4.dp)
                )
            }
            Text(
                text = value,
                fontWeight = FontWeight.W500,
                fontFamily = popMid,
                fontSize = 22.sp,
                color = Color(102, 102, 102, 255)
            )
        }
        HorizontalDivider(
            color = Color(102, 102, 102, 128),
            thickness = 1.dp
        )
    }
}
