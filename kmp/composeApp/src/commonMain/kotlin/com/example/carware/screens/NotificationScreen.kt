package com.example.carware.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import carware.composeapp.generated.resources.poppins
import carware.composeapp.generated.resources.poppins_medium
import com.example.carware.LocalStrings
import com.example.carware.m
import com.example.carware.viewModel.notification.NotificationViewModel
import com.example.carware.viewModel.notification.NotificationsUiState
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Composable
fun NotificationScreen(
    navController: NavController,
    viewModel: NotificationViewModel

) {
    val strings = LocalStrings.current
    val popMid = FontFamily(Font(Res.font.poppins_medium))
    val pop = FontFamily(Font(Res.font.poppins))

    val primaryGradientBrush = Brush.linearGradient(
        listOf(Color(194, 0, 0, 255), Color(92, 0, 0, 255))
    )
    LaunchedEffect(Unit) {
        viewModel.loadNotifications()
    }

    LaunchedEffect(Unit) {
        viewModel.markAllAsRead()
    }
    val uiState by viewModel.notificationsUiState.collectAsState()

    when (uiState) {
        is NotificationsUiState.Loading -> { /* show loading */ }
        is NotificationsUiState.Error -> { /* show error */ }
        is NotificationsUiState.Success -> {
            val (notifications, unreadCount) = (uiState as NotificationsUiState.Success)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFD9D9D9))
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 100.dp)
                    .padding(top = 32.dp)
                    .padding(horizontal = 14.dp)


            ) {
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
                        text = "Notifications",
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

                Spacer(modifier = Modifier.height(16.dp))



                notifications.forEach { notification ->
                    NotificationCard(
                        title = notification.title,
                        body = notification.body,
                        timeStamp = notification.timestamp,

                        )
                    Spacer(modifier = Modifier.height(8.dp))

                }
            }

        }


    }

}



@OptIn(ExperimentalTime::class)
@Composable
fun NotificationCard(
    title: String,
    body: String,
    timeStamp: Long
) {
    val popMid = FontFamily(Font(Res.font.poppins_medium))


    fun formatTimestamp(timestamp: Long): String {
        val now = Clock.System.now().toEpochMilliseconds()
        val diffMillis = now - timestamp

        val hours = diffMillis / (1000 * 60 * 60)
        val days = hours / 24

        return when {
            days > 0 -> "$days days ago"
            hours > 0 -> "$hours hours ago"
            else -> "Just now"
        }
    }


    Column(
        modifier = m
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(210, 210, 210))
            .padding(14.dp)
    ) {
        Text(
            title,
            fontFamily = popMid,
            fontWeight = FontWeight.W600,
            fontSize = 22.sp,
            color = Color(102, 102, 102, 255)
        )
        Row(
            m.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                body,
                fontFamily = popMid,
                fontWeight = FontWeight.W400,
                fontSize = 18.sp,
                color = Color(102, 102, 102, 255)
            )

            Text(
                formatTimestamp(timeStamp),
                fontFamily = popMid,
                fontWeight = FontWeight.W300,
                fontSize = 12.sp,
                color = Color(102, 102, 102, 255)
            )
        }


    }
}