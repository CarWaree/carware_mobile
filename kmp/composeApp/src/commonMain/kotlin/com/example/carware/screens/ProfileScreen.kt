package com.example.carware.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.compose.rememberNavController
import carware.composeapp.generated.resources.Res
import carware.composeapp.generated.resources.*
import com.example.carware.m
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

// Helper modifier to apply gradient to icons
fun Modifier.iconGradient(brush: Brush): Modifier = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush, blendMode = BlendMode.SrcIn)
    }

@Composable
fun ProfileScreen(navController: NavController) {
    val popSemi = FontFamily(Font(Res.font.poppins_semibold))
    val popMid = FontFamily(Font(Res.font.poppins_medium))
    val primaryRed = Color(194, 0, 0, 255)
    val lightGrey = Color(0xFFE0E0E0)
    val redWithAlpha = Color(0x80C20000) // #C2000080

    val primaryGradientBrush = Brush.linearGradient(
        listOf(Color(194, 0, 0, 255), Color(92, 0, 0, 255))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .verticalScroll(rememberScrollState())
            .padding(bottom = 100.dp)
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
                contentDescription = "Back",
                modifier = Modifier
                    .size(28.dp)
                    .clickable { navController.popBackStack() }
                    .iconGradient(primaryGradientBrush),
                tint = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Profile",
                fontFamily = popSemi,
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium,
                style = TextStyle(
                    brush = primaryGradientBrush
                )
            )
            Spacer(modifier = Modifier.weight(1.2f))
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 16.dp),
            thickness = 0.8.dp,
            color = Color(0x33666666)
        )
        // Profile Picture Section
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(contentAlignment = Alignment.BottomEnd) {
                Image(
                    painter = painterResource(Res.drawable.pp),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )
                // The edit icon already contains the background and white pencil
                Icon(
                    painter = painterResource(Res.drawable.edit),
                    contentDescription = "Edit",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(34.dp)
                        .offset(x = (-4).dp, y = (-4).dp) // Adjust position slightly
                        .clickable { /* Action */ }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Zyad Osama",
                fontFamily = popSemi,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                style = TextStyle(
                    brush = primaryGradientBrush
                )
            )
            Text(
                text = "Member since July 2025",
                fontFamily = popMid,
                fontSize = 17.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0x767676CC)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // My Primary Vehicle Section
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Text(
                text = "My Primary Vehicle",
                fontFamily = popSemi,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                style = TextStyle(
                    brush = primaryGradientBrush
                )
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Primary Vehicle Card
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(204, 204, 204, 191))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Image(
                        painter = painterResource(Res.drawable.audi),
                        contentDescription = null,
                        modifier = Modifier.size(171.dp, 128.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "AUDI",
                        fontFamily = popSemi,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp), // Adjust space from the car image
                        horizontalArrangement = Arrangement.SpaceBetween, // This pushes Left, Center, and Right
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(Res.drawable.car),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp).iconGradient(primaryGradientBrush),
                                tint = Color.White
                            )
                            Spacer(Modifier.width(4.dp))
                            Text("A6", fontFamily = popMid, fontSize = 14.sp)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(Res.drawable.modelyear),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp).iconGradient(primaryGradientBrush),
                                tint = Color.White
                            )
                            Spacer(Modifier.width(4.dp))
                            Text("2025", fontFamily = popMid, fontSize = 14.sp)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(Res.drawable.color),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp).iconGradient(primaryGradientBrush),
                                tint = Color.White
                            )
                            Spacer(Modifier.width(4.dp))
                            Text("Black", fontFamily = popMid, fontSize = 14.sp)
                        }
                    }
                }

            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Menu Items
        Column(modifier = Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // My Cars Item
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(204, 204, 204, 191))
                    .clickable { }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Res.drawable.car),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp).iconGradient(primaryGradientBrush),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "My Cars",
                    fontFamily = popSemi,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    style = TextStyle(
                        brush = primaryGradientBrush
                    ),
                    modifier = Modifier.weight(1f)
                )
                Icon(painterResource(Res.drawable.keyboard_arrow_right), null, tint = Color.Gray)
            }

            // Payment Card Item
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(204, 204, 204, 191))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.visa),
                    contentDescription = null,
                    modifier = Modifier.size(45.dp, 28.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Visa ending in 2030",
                        fontFamily = popSemi,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Medium,
                        style = TextStyle(
                            brush = primaryGradientBrush
                        )

                    )
                    Text("Expires 12/26", fontFamily = popMid, fontSize = 12.sp, color = Color.Gray)
                }
                Icon(
                    painter = painterResource(Res.drawable.check_onboard),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp).iconGradient(primaryGradientBrush),
                    tint = Color.White
                )
            }

            // Add new method
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(Res.drawable.add_new),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp),
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Add new method",
                        fontFamily = popSemi,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        style = TextStyle(
                            brush = primaryGradientBrush
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Logout Button
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .align(Alignment.CenterHorizontally)
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = redWithAlpha),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painterResource(Res.drawable.settings_logout), null, Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text("log out", fontFamily = popSemi, fontSize = 20.sp, color = Color.White)
            }
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(navController = rememberNavController())
}
