package com.example.carware.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = "shimmer")
    
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerProgress"
    )

    val width = size.width.toFloat()
    val startX = (progress * 3 * width) - width

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFB8B5B5),
                Color(0xFFD1D1D1),
                Color(0xFFB8B5B5),
            ),
            start = Offset(startX, 0f),
            end = Offset(startX + width, size.height.toFloat())
        )
    ).onGloballyPositioned {
        size = it.size
    }
}

// --- Home Screen Shimmers ---

@Composable
fun ShimmerCarCard() {
    Box(
        modifier = Modifier
            .size(width = 305.dp, height = 255.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color(0x1A000000))
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
                Box(modifier = Modifier.size(20.dp).clip(RoundedCornerShape(4.dp)).shimmerEffect())
            }
            Spacer(Modifier.height(10.dp))
            Box(modifier = Modifier.size(230.dp, 120.dp).clip(RoundedCornerShape(8.dp)).shimmerEffect())
            
            Spacer(Modifier.height(15.dp))
            Box(modifier = Modifier.width(100.dp).height(24.dp).align(Alignment.Start).clip(RoundedCornerShape(4.dp)).shimmerEffect())
            
            Spacer(Modifier.height(15.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                repeat(3) {
                    Box(modifier = Modifier.size(60.dp, 20.dp).clip(RoundedCornerShape(4.dp)).shimmerEffect())
                }
            }
        }
    }
}

@Composable
fun DropdownShimmer(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    val shimmerAlpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(55.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(118, 118, 118, (255 * shimmerAlpha).toInt()))
    )
}
@Composable
fun ShimmerOBDCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .clip(RoundedCornerShape(15.dp))
            .shimmerEffect()
    )
}

@Composable
fun ShimmerMaintenance() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(8.dp))
            .shimmerEffect()
    )
}

// --- Schedule Screen Shimmers ---

@Composable
fun ShimmerUserCar() {
    Row(
        modifier = Modifier.fillMaxWidth().height(60.dp).padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(50.dp).clip(CircleShape).shimmerEffect())
        Spacer(modifier = Modifier.width(22.dp))
        Column(verticalArrangement = Arrangement.Center) {
            Box(modifier = Modifier.size(100.dp, 20.dp).clip(RoundedCornerShape(4.dp)).shimmerEffect())
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.size(80.dp, 16.dp).clip(RoundedCornerShape(4.dp)).shimmerEffect())
        }
    }
}

@Composable
fun ShimmerDropdown() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .clip(RoundedCornerShape(8.dp))
            .shimmerEffect()
    )
}

@Composable
fun ShimmerCalendar() {
    Box(
        modifier = Modifier
            .size(375.dp, 250.dp)
            .clip(RoundedCornerShape(16.dp))
            .shimmerEffect()
    )
}

@Composable
fun ShimmerScheduleScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(45.dp))
        Box(modifier = Modifier.size(200.dp, 30.dp).clip(RoundedCornerShape(4.dp)).shimmerEffect())
        Spacer(modifier = Modifier.height(32.dp))
        
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.size(120.dp, 24.dp).clip(RoundedCornerShape(4.dp)).shimmerEffect())
            Spacer(modifier = Modifier.height(22.dp))
            repeat(2) {
                ShimmerUserCar()
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            Spacer(modifier = Modifier.height(18.dp))
            Box(modifier = Modifier.size(140.dp, 24.dp).clip(RoundedCornerShape(4.dp)).shimmerEffect())
            Spacer(modifier = Modifier.height(12.dp))
            ShimmerDropdown()
            
            Spacer(modifier = Modifier.height(18.dp))
            Box(modifier = Modifier.size(160.dp, 24.dp).clip(RoundedCornerShape(4.dp)).shimmerEffect())
            Spacer(modifier = Modifier.height(12.dp))
            ShimmerDropdown()
            
            Spacer(modifier = Modifier.height(18.dp))
            ShimmerCalendar()
        }
    }
}

// --- History Screen Shimmers ---

@Composable
fun ShimmerHistoryCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0x1A000000))
            .padding(14.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(38.dp).clip(CircleShape).shimmerEffect())
                Spacer(Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Box(modifier = Modifier.size(60.dp, 14.dp).clip(RoundedCornerShape(2.dp)).shimmerEffect())
                    Spacer(Modifier.height(4.dp))
                    Box(modifier = Modifier.size(120.dp, 20.dp).clip(RoundedCornerShape(4.dp)).shimmerEffect())
                }
                Column(horizontalAlignment = Alignment.End) {
                    Box(modifier = Modifier.size(50.dp, 18.dp).clip(RoundedCornerShape(4.dp)).shimmerEffect())
                    Spacer(Modifier.height(4.dp))
                    Box(modifier = Modifier.size(70.dp, 12.dp).clip(RoundedCornerShape(2.dp)).shimmerEffect())
                }
            }
            Spacer(Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(100.dp, 16.dp).clip(RoundedCornerShape(2.dp)).shimmerEffect())
                Spacer(Modifier.width(16.dp))
                Box(modifier = Modifier.size(80.dp, 16.dp).clip(RoundedCornerShape(2.dp)).shimmerEffect())
            }
            Spacer(Modifier.height(12.dp))
            Box(modifier = Modifier.fillMaxWidth().height(40.dp).clip(RoundedCornerShape(10.dp)).shimmerEffect())
        }
    }
}

@Preview
@Composable
fun ShimmerPreview() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).background(Color(0xFFD9D9D9)),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShimmerCarCard()
        ShimmerMaintenance()
        ShimmerOBDCard()
    }
}

@Preview
@Composable
fun ShimmerSchedulePreview() {
    ShimmerScheduleScreen()
}

@Preview
@Composable
fun ShimmerHistoryPreview() {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        repeat(3) { ShimmerHistoryCard() }
    }
}
