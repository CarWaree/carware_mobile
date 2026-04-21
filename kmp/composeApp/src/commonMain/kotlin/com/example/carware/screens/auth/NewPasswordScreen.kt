package com.example.carware.screens.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import carware.composeapp.generated.resources.Res
import carware.composeapp.generated.resources.eye_off
import carware.composeapp.generated.resources.eyee
import carware.composeapp.generated.resources.new_logo
import carware.composeapp.generated.resources.poppins_medium
import carware.composeapp.generated.resources.poppins_semibold
import com.example.carware.LocalStrings
import com.example.carware.m
import com.example.carware.navigation.LoginScreen
import com.example.carware.navigation.NewPasswordScreen
import com.example.carware.screens.LoadingOverlay
import com.example.carware.screens.ToastMessage
import com.example.carware.screens.appButtonBack
import com.example.carware.screens.appGradBack
import com.example.carware.util.lang.AppLanguage
import com.example.carware.util.storage.PreferencesManager
import com.example.carware.viewModel.auth.newPassword.NewPasswordViewModel
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun NewPasswordScreen(
    navController: NavController,
    viewModel: NewPasswordViewModel
) {
    val state by viewModel.state.collectAsState()

    val strings = LocalStrings.current
    val popSemi = FontFamily(Font(Res.font.poppins_semibold))
    val popMid = FontFamily(Font(Res.font.poppins_medium))

    var isPassVisible by remember { mutableStateOf(false) }
    var isConfPassVisible by remember { mutableStateOf(false) }

    val textFieldColors = TextFieldDefaults.colors(
        unfocusedTextColor = Color.DarkGray,
        errorTextColor = Color(194, 0, 0, 255),
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,
        cursorColor = Color(194, 0, 0, 255),
        focusedIndicatorColor = Color(118, 118, 118, 255),
        unfocusedIndicatorColor = Color(118, 118, 118, 255),
        errorIndicatorColor = Color(194, 0, 0, 255),
        focusedTextColor = Color(0, 0, 0, 255)
    )

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            navController.navigate(LoginScreen) {
                popUpTo(NewPasswordScreen) { inclusive = true }
            }
        }
    }

    // Layer 1: background + logo (mirrors login exactly)
    Column(
        m
            .fillMaxSize()
            .appGradBack()
            .padding(64.dp)
    ) {
        Column(
            m.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
                , verticalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(
                visible = state.errorMessage != null,
                enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
                modifier = Modifier.padding(top = 20.dp)
            ) {
                state.errorMessage?.let { msg ->
                    ToastMessage(message = msg, state = false)
                    LaunchedEffect(msg) {
                        delay(3000)
                        viewModel.clearErrorMessage()
                    }
                }
            }

            Icon(
                painter = painterResource(Res.drawable.new_logo),
                contentDescription = null,
                tint = Color(211, 203, 203, 255),
                modifier = m.scale(0.8f)
            )
        }
    }

    // Layer 2: centered card (mirrors login exactly)
    Column(
        modifier = m.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ElevatedCard(
            modifier = m.size(width = 330.dp, height = 390.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(217, 217, 217, 255)
            )
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = strings.get("CREATE_NEW_PASSWORD"),
                    fontFamily = popSemi,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(30, 30, 30, 168)
                )
                Text(
                    text = strings.get("CREATE_YOUR_PASSWORD"),
                    fontFamily = popSemi,
                    fontSize = 13.sp,
                    color = Color(30, 30, 30, 168)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password field
                OutlinedTextField(
                    modifier = m.size(280.dp, 55.dp),
                    value = state.pass,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    placeholder = {
                        Text(
                            text = if (state.passError) strings.get("PASSWORD_REQUIRED")
                            else strings.get("PASSWORD"),
                            fontFamily = popMid,
                            fontSize = 12.sp,
                            color = if (state.passError) Color(194, 0, 0, 255)
                            else Color(30, 30, 30, 168)
                        )
                    },
//                    keyboardOptions = KeyboardOptions.run {
//                        Default.copy(
//                                        keyboardType = KeyboardType.Password,
//                                        imeAction = ImeAction.Next
//                                    )
//                    },
                    isError = state.passError,
                    visualTransformation = if (isPassVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    trailingIcon = {
                        val icon = if (isPassVisible) Res.drawable.eyee else Res.drawable.eye_off
                        IconButton(onClick = { isPassVisible = !isPassVisible }) {
                            Icon(
                                painter = painterResource(icon),
                                contentDescription = null,
                                tint = Color(118, 118, 118, 255),
                                modifier = m.size(24.dp)
                            )
                        }
                    },
                    colors = textFieldColors
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Password requirement hint — left aligned, matches login pattern
                Row(
                    modifier = m
                        .width(280.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = strings.get("PASSWORD_REQUIREMENTS"),
                        fontFamily = popMid,
                        fontSize = 10.sp,
                        color = Color(118, 118, 118, 255)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Confirm password field
                OutlinedTextField(
                    modifier = m.size(280.dp, 55.dp),
                    value = state.confPass,
                    onValueChange = { viewModel.onConfirmPasswordChange(it) },
                    placeholder = {
                        Text(
                            text = if (state.confPassError) strings.get("CONFIRMATION_REQUIRED")
                            else strings.get("CONFIRM_PASSWORD"),
                            fontFamily = popMid,
                            fontSize = 12.sp,
                            color = if (state.confPassError) Color(194, 0, 0, 255)
                            else Color(30, 30, 30, 168)
                        )
                    },
//                    keyboardOptions = KeyboardOptions.Default.copy(
//                        keyboardType = KeyboardType.Password,
//                        imeAction = ImeAction.Done
//                    ),
                    isError = state.confPassError,
                    visualTransformation = if (isConfPassVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    trailingIcon = {
                        val icon = if (isConfPassVisible) Res.drawable.eyee else Res.drawable.eye_off
                        IconButton(onClick = { isConfPassVisible = !isConfPassVisible }) {
                            Icon(
                                painter = painterResource(icon),
                                contentDescription = null,
                                tint = Color(118, 118, 118, 255),
                                modifier = m.size(24.dp)
                            )
                        }
                    },
                    colors = textFieldColors
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Continue button
                Card(
                    onClick = { viewModel.newPassword() },
                    modifier = m
                        .size(width = 280.dp, height = 45.dp)
                        .border(
                            width = 1.dp,
                            color = Color(30, 30, 30, 110),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clip(shape = RoundedCornerShape(8.dp))
                        .appButtonBack(),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {
                    Row(
                        modifier = m.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            strings.get("CONTINUE"),
                            fontFamily = popSemi,
                            fontSize = 18.sp,
                            color = Color(217, 217, 217, 255)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Back to login
                Row {
                    Text(
                        strings.get("BACK_TO"),
                        fontFamily = popMid,
                        fontSize = 12.sp,
                        color = Color(30, 30, 30, 168)
                    )
                    Text(
                        strings.get("LOGIN"),
                        fontFamily = popMid,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(194, 0, 0, 255),
                        modifier = m.clickable { navController.navigate(LoginScreen) }
                    )
                }
            }
        }
    }

    if (state.isLoading) {
        LoadingOverlay()
    }
}
