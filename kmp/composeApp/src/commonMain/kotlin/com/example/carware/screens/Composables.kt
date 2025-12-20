package com.example.carware.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import carware.composeapp.generated.resources.Res
import carware.composeapp.generated.resources.poppins_medium
import carware.composeapp.generated.resources.poppins_semibold
import com.example.carware.m
import com.example.carware.util.navBar.TabItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


fun Modifier.appGradBack(): Modifier = this.then(
    background(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color(0xFFC20000),
                Color(0xFF5C0000)
            )
        )
    )
)

fun Modifier.appButtonBack(): Modifier = this.then(
    background(
        brush = Brush.horizontalGradient(
            colors = listOf(
                Color(0xFFC20000),
                Color(0xFF5C0000)
            )
        )
    )
)

fun Modifier.cardGradBack(): Modifier = this.then(
    background(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color(0xFF949697),
                Color(0xFFC9CDCF)
            )
        )
    )
)


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomNavBar(
    pagerState: PagerState,
    scope: CoroutineScope,
    tabs: List<TabItem>,
    modifier: Modifier = Modifier
) {
    val popSemi = FontFamily(Font(Res.font.poppins_semibold))

    val popMid = FontFamily(Font(Res.font.poppins_medium))



    // OUTER HEIGHT ONLY FOR SPACING
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        contentAlignment = Alignment.Center
    ) {

        // ACTUAL NAV BAR CONTAINER
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .size(350.dp,55.dp)
                .padding(horizontal = 10.dp)
                .background(
                    color = Color(204, 204, 204, 255),
                    shape = RoundedCornerShape(50.dp)
                )
        ) {

            // ---- MEASUREMENTS ----
            val barWidth = maxWidth
            val tabWidth = barWidth / tabs.size

            val pillWidth = 80.dp     // edit size here
            val pillHeight = 40.dp

            // Calculates X position for the pill
            val indicatorOffset by animateDpAsState(
                targetValue = (tabWidth * pagerState.currentPage) +
                        (tabWidth / 2) - (pillWidth / 2),
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                ),
                label = "indicator"
            )

            // ---- MOVING PILL (BEHIND CONTENT) ----
            Box(
                modifier = Modifier
                    .offset(x = indicatorOffset)
                    .align(Alignment.CenterStart)   // <-- ALWAYS CENTER VERTICALLY
                    .size(pillWidth, pillHeight)
                    .clip(RoundedCornerShape(50.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFC20000),
                                Color(0xFF5C0000)
                            )
                        )
                        )
                    // deep red like screenshot
            )

            // ---- NAV ITEMS ----
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                tabs.forEach { tab ->

                    val isSelected = pagerState.currentPage == tab.index

                    // Each tab occupies equal width
                    Box(
                        modifier = Modifier
                            .width(tabWidth)
                            .fillMaxHeight()
                            .clickable {
                                scope.launch {
                                    pagerState.animateScrollToPage(tab.index)
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            // ICON
                            Icon(
                                painter = painterResource(tab.icon),
                                contentDescription = tab.title,
                                tint = if (isSelected) Color(204, 204, 204, 255) else Color.Unspecified,
                                modifier = Modifier.size(22.dp)
                            )

                            // TITLE
                            if (isSelected) {
                                Spacer(modifier = Modifier.width(6.dp))

                                Text(
                                    text = tab.title,
                                    fontFamily = popSemi,
                                    color = Color(204, 204, 204, 255),
                                    fontSize = 12.sp,
//                                    fontWeight = FontWeight.SemiBold
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
fun OBDCard(
    modifier: Modifier = Modifier,
    onMoreDetailsClick: () -> Unit = {}
) {


    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .appButtonBack()
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left side - Connection diagram
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    ConnectionDiagram()
                }

                // Right side - OBD text and more details
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "OBD",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // More details button
                    TextButton(
                        onClick = onMoreDetailsClick,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "More details",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Arrow",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ConnectionDiagram() {
    Box(
        modifier = Modifier.size(150.dp),
        contentAlignment = Alignment.Center
    ) {
        // Central WiFi hub
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color(0xFFFFA000)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Wifi,
                contentDescription = "WiFi",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }

        // Surrounding icons in a circle
        val icons = listOf(
            Icons.Default.Settings to -45f,
            Icons.Default.Bluetooth to 0f,
            Icons.Default.Speed to 45f,
            Icons.Default.Build to 90f,
            Icons.Default.LocalGasStation to 135f,
            Icons.Default.AcUnit to 180f
        )

        icons.forEach { (icon, angle) ->
            SatelliteIcon(
                icon = icon,
                angle = angle,
                radius = 60.dp
            )
        }

        // Connection dots
        listOf(20f, 70f, 120f, 170f, 220f, 270f, 320f).forEach { angle ->
            ConnectionDot(angle = angle, radius = 35.dp)
        }
    }
}

@Composable
fun SatelliteIcon(
    icon: ImageVector,
    angle: Float,
    radius: androidx.compose.ui.unit.Dp
) {
    val radiusPx = with(androidx.compose.ui.platform.LocalDensity.current) { radius.toPx() }
    val angleRad = Math.toRadians(angle.toDouble())
    val x = (radiusPx * kotlin.math.cos(angleRad)).dp
    val y = (radiusPx * kotlin.math.sin(angleRad)).dp

    Box(
        modifier = Modifier
            .offset(x = x, y = y)
            .size(36.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.9f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF8B0000),
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun ConnectionDot(
    angle: Float,
    radius: androidx.compose.ui.unit.Dp
) {
    val radiusPx = with(androidx.compose.ui.platform.LocalDensity.current) { radius.toPx() }
    val angleRad = Math.toRadians(angle.toDouble())
    val x = (radiusPx * kotlin.math.cos(angleRad)).dp
    val y = (radiusPx * kotlin.math.sin(angleRad)).dp

    Box(
        modifier = Modifier
            .offset(x = x, y = y)
            .size(8.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.6f))
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun OBDCardPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            OBDCard(
                onMoreDetailsClick = {
                    // Preview click action
                }
            )
        }
    }
}