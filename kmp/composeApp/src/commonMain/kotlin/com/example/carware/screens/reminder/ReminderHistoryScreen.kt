package com.example.carware.screens.reminder

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
import carware.composeapp.generated.resources.audi
import carware.composeapp.generated.resources.history_filter
import carware.composeapp.generated.resources.poppins_medium
import carware.composeapp.generated.resources.poppins_semibold
import com.example.carware.LocalStrings
import com.example.carware.m
import com.example.carware.screens.ShimmerHistoryCard
import com.example.carware.viewModel.reminder.reminderHistory.ReminderHistoryState
import com.example.carware.viewModel.reminder.reminderHistory.ReminderHistoryViewModel
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ReminderHistoryScreen(
    navController: NavController,
    viewModel: ReminderHistoryViewModel
) {
    val strings = LocalStrings.current
    val popMid = FontFamily(Font(Res.font.poppins_medium))

    val pageScrollState = rememberScrollState()
    val state by viewModel.reminderState.collectAsState()
    val currentState = state  // add this
    val primaryGradientBrush = Brush.linearGradient(
        listOf(Color(194, 0, 0, 255), Color(92, 0, 0, 255))
    )
    Column(
        m.fillMaxSize()
            .background(Color(217, 217, 217, 255)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(217, 217, 217, 255))
                .padding(horizontal = 20.dp)
                .padding(top = 42.dp)
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
            Text(
                strings.get("REMINDER_HISTORY"),
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
            strings.get("REMINDER_SUBTITLE"),
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

        Column(m.fillMaxSize()
            .verticalScroll(pageScrollState)
        ) {
            when (currentState) {
                is ReminderHistoryState.Loading -> {
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

                is ReminderHistoryState.Error -> {
                    Spacer(modifier = m.padding(vertical = 50.dp))
                    Text(
                        "Error: ${currentState.message}",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                is ReminderHistoryState.Success -> {
                    currentState.historyItems.forEach { item ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()

                                .padding( 16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            currentState.historyItems.forEach { item ->
                                ReminderHistoryCard(
                                    item.notificationDate,
                                    item.typeName,
                                    item.vehicleName
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ReminderHistoryCard(
    notificationDate: String,
    typeName: String,
    vehicleName: String,
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
            .padding(all = 14.dp)
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
                    text = vehicleName,
                    fontFamily = popMid,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W300,
                    color = Color(102, 102, 102, 255)
                )
                Text(
                    text = typeName,
                    fontFamily = popSemi,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500,
                    color = Color(102, 102, 102, 255)
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                val shortDate = LocalDateTime.parse(notificationDate)
                Text(
                    text = "Due Date: ${shortDate.dayOfMonth}/${shortDate.monthNumber}/${shortDate.year}",
                    fontFamily = popSemi,
                    fontSize = 14.sp, // matched to cost text size in ServiceHistoryCard
                    fontWeight = FontWeight.W500,
                    color = Color(102, 102, 102, 255)
                )
            }
        }

        Spacer(Modifier.height(4.dp))
    }
}

@Preview
@Composable
fun PreviewREm(


) {
    ReminderHistoryCard(
        notificationDate = "TODO()",
        typeName = " TODO()",
        vehicleName = "TODO()"
    )
}