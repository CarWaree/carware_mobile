package com.example.carware.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import carware.composeapp.generated.resources.Res
import carware.composeapp.generated.resources.*
import com.example.carware.viewModel.profile.ProfileScreenState
import com.example.carware.viewModel.profile.ProfileScreenViewModel
import kotlinx.coroutines.awaitCancellation
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: ProfileScreenViewModel
) {

    val popSemi = FontFamily(Font(Res.font.poppins_semibold))
    val popMid = FontFamily(Font(Res.font.poppins_medium))
    val pop = FontFamily(Font(Res.font.poppins))

    val primaryGradientBrush = Brush.linearGradient(
        listOf(Color(194, 0, 0, 255), Color(92, 0, 0, 255))
    )

    val state by viewModel.state.collectAsState()
    val editState by viewModel.editState.collectAsState()

//    val lifecycleOwner = LocalLifecycleOwner.current
//    LaunchedEffect(lifecycleOwner) {
//        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
//            viewModel.loadProfile()
//            awaitCancellation()    // 👈 stops the loop
//
//        }
//    }

    when (state) {
        is ProfileScreenState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is ProfileScreenState.Error -> {
            val message = (state as ProfileScreenState.Error).message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = message)
            }
        }

        is ProfileScreenState.Success -> {
            val profile = (state as ProfileScreenState.Success).profile

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFD9D9D9))
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 100.dp)
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
                        contentDescription = "Back",
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
                        text = "Edit Profile",
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
                        text = profile.fullName,
                        fontFamily = pop,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        style = TextStyle(brush = primaryGradientBrush)
                    )

                    Text(
                        text = "Change profile photo",
                        fontFamily = pop,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray,
                        modifier = Modifier.clickable { /* Action */ }
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Input Fields
                Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                    EditProfileField(
                        label = "Full Name",
                        placeholder = profile.fullName,
                        value = editState.fullName,
                        onValueChange = { viewModel.onFullNameChange(it) },
                        icon = Res.drawable.contact,
                        fontFamily = popMid,
                        primaryGradientBrush = primaryGradientBrush
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    EditProfileField(
                        label = "Email Address",
                        placeholder = profile.email,
                        value = editState.email,
                        onValueChange = { viewModel.onEmailChange(it) },
                        icon = Res.drawable.email,
                        fontFamily = popMid,
                        primaryGradientBrush = primaryGradientBrush
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    profile.phoneNumber?.let {
                        EditProfileField(
                            label = it,
                            placeholder = profile.phoneNumber,
                            value = editState.phone,
                            onValueChange = { viewModel.onPhoneChange(it) },
                            icon = Res.drawable.phone,
                            fontFamily = popMid,
                            primaryGradientBrush = primaryGradientBrush
                        )
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))

                // Action Buttons
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = { viewModel.saveProfile() },
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .height(55.dp)
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp))
                            .background(primaryGradientBrush, RoundedCornerShape(12.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (state is ProfileScreenState.Loading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {


                            Text(
                                text = "Save Changes",
                                fontFamily = pop,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        }
                    }

                    Text(
                        text = "Discard Changes",
                        fontFamily = pop,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        style = TextStyle(brush = primaryGradientBrush),
                        modifier = Modifier.clickable { navController.popBackStack() }
                    )
                }
            }
        }
    }

}

@Composable
fun EditProfileField(
    label: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    icon: DrawableResource,
    fontFamily: FontFamily,
    primaryGradientBrush: Brush
) {

    Column {
        Text(
            text = label,
            fontFamily = fontFamily,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            style = TextStyle(brush = primaryGradientBrush),
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
        )
        OutlinedTextField(
            placeholder = { // Add this block
                Text(
                    text = placeholder,
                    color = Color(118, 118, 118, 191),
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.W400
                )
            },
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(
                    Color(0xBFCCCCCC),
                    RoundedCornerShape(12.dp)
                ),
            leadingIcon = {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                        .drawWithContent {
                            drawContent()
                            drawRect(primaryGradientBrush, blendMode = BlendMode.SrcIn)
                        },
                    tint = Color.White
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(12.dp),
            textStyle = LocalTextStyle.current.copy(
                fontFamily = fontFamily,
                fontSize = 16.sp,
                color = Color.Gray
            ),
            singleLine = true,

        )
    }
}

//@Preview
//@Composable
//fun EditProfileScreenPreview() {
//    EditProfileScreen(navController = rememberNavController())
//}
