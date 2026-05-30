package com.example.carware.screens.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import carware.composeapp.generated.resources.Res
import carware.composeapp.generated.resources.arrow_left
import carware.composeapp.generated.resources.change_pins_1
import carware.composeapp.generated.resources.eye_off
import carware.composeapp.generated.resources.eyee
import carware.composeapp.generated.resources.history_filter
import carware.composeapp.generated.resources.poppins_medium
import com.example.carware.LocalStrings
import com.example.carware.m
import com.example.carware.screens.LoadingOverlay
import com.example.carware.screens.NoInternetDialog
import com.example.carware.screens.ToastMessage
import com.example.carware.screens.appButtonBack
import com.example.carware.screens.settings.profile.iconGradient
import com.example.carware.viewModel.auth.changePass.ChangePassViewModel
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun ChangePassScreen(
    navController: NavController,
    viewModel: ChangePassViewModel
) {
    val state by viewModel.state.collectAsState()

    val strings = LocalStrings.current
    val popMid = FontFamily(Font(Res.font.poppins_medium))
    val pageScrollState = rememberScrollState()
    val textFieldColors = TextFieldDefaults.colors(

        unfocusedTextColor = Color.DarkGray,
        errorTextColor = Color(194, 0, 0, 255),

        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,


        cursorColor = Color(194, 0, 0, 255),
        focusedIndicatorColor = Color(
            118,
            118,
            118,
            255
        ),    // underline/border when focused
        unfocusedIndicatorColor = Color(
            118,
            118,
            118,
            255
        ),  // underline/border when not focused
        errorIndicatorColor = Color(194, 0, 0, 255),
        focusedTextColor = Color(0, 0, 0, 255)


    )
    var isPassVisible by remember { mutableStateOf(false) }
    val primaryGradientBrush = Brush.linearGradient(
        listOf(Color(194, 0, 0, 255), Color(92, 0, 0, 255))
    )

    val isConnected by viewModel.isConnected.collectAsStateWithLifecycle()
    var showNoInternetDialog by remember { mutableStateOf(false) }

    LaunchedEffect(isConnected) {
        if (isConnected) showNoInternetDialog = false
        else if (!isConnected) showNoInternetDialog = true
    }
    var confirmButtonPressed by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                if (showNoInternetDialog && confirmButtonPressed) {
                    renderEffect = BlurEffect(
                        radiusX = 10f,
                        radiusY = 10f,
                    )
                }
            }
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
            Row(
                modifier = m
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(Res.drawable.arrow_left),
                    contentDescription = strings.get("BACK"),
                    modifier = m
                        .size(28.dp)
                        .clickable { navController.popBackStack() }
                        .iconGradient(primaryGradientBrush),
                    tint = Color.White
                )
                Spacer(modifier = m.weight(1f))
                Text(
                    text = strings.get("PROFILE"),
                    fontFamily = popMid,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Medium,
                    style = TextStyle(brush = primaryGradientBrush)
                )
                Spacer(modifier = m.weight(1.2f))
            }

        }
        Spacer(m.height(6.dp))

        HorizontalDivider(
            color = Color(102, 102, 102, 51),
            thickness = 1.dp
        )

        val toastMessage = state.errorMessage ?: state.successMessage.takeIf { state.isSuccess }
        val isSuccess = state.errorMessage == null && state.isSuccess

        AnimatedVisibility(
            visible = toastMessage != null,
            enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
            modifier = Modifier.padding(top = 20.dp)
        ) {
            toastMessage?.let { msg ->
                ToastMessage(message = msg, state = isSuccess)

                LaunchedEffect(msg) {
                    delay(3000)
                    viewModel.clearMessage()

                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(pageScrollState)
                .padding(horizontal = 18.dp),
        ) {

            Icon(
                painter = painterResource(Res.drawable.change_pins_1),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = m.align(Alignment.CenterHorizontally)
            )
            Spacer(m.height(24.dp))

            Text(
                text = strings.get("OLD_PASS"),
                fontFamily = popMid,
                fontSize = 18.sp,
                fontWeight = FontWeight.W500,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        listOf(Color(194, 0, 0, 255), Color(92, 0, 0, 255))
                    )
                ),
            )
            OutlinedTextField(
                modifier = m.fillMaxWidth(),
                value = state.oldPass,
                onValueChange = {
                    viewModel.onPassChange(it)
                },
                placeholder = {
                    Text(
                        text = if (state.oldPassError) strings.get("PASSWORD_REQUIRED") else strings.get(
                            "PASSWORD"
                        ),
                        fontFamily = popMid,
                        fontSize = 12.sp,
                        color = if (state.oldPassError) Color(194, 0, 0, 255) else Color(
                            30,
                            30,
                            30,
                            168
                        )
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next

                ),
                isError = state.oldPassError,
                visualTransformation = if (isPassVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                trailingIcon = ({
                    val icon =
                        if (isPassVisible) Res.drawable.eyee else Res.drawable.eye_off
                    IconButton(onClick = { isPassVisible = !isPassVisible })
                    {
                        Icon(
                            painter = painterResource(icon), contentDescription = null,
                            tint = Color(118, 118, 118, 255),
                            modifier = m.size(24.dp)
                        )
                    }
                }),

                colors = textFieldColors


            ) // old pass field
            Spacer(m.height(28.dp))
            Text(
                text = strings.get("NEW_PASS"),
                fontFamily = popMid,
                fontSize = 18.sp,
                fontWeight = FontWeight.W500,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        listOf(Color(194, 0, 0, 255), Color(92, 0, 0, 255))
                    )
                ),
            )
            OutlinedTextField(
                modifier = m.fillMaxWidth(),
                value = state.newPass,
                onValueChange = {
                    viewModel.onNewPassChange(it)
                },
                placeholder = {
                    Text(
                        text = if (state.newPassError) strings.get("PASSWORD_REQUIRED") else strings.get(
                            "PASSWORD"
                        ),
                        fontFamily = popMid,
                        fontSize = 12.sp,
                        color = if (state.newPassError) Color(194, 0, 0, 255) else Color(
                            30,
                            30,
                            30,
                            168
                        )
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next

                ),
                isError = state.newPassError,
                visualTransformation = if (isPassVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                trailingIcon = ({
                    val icon =
                        if (isPassVisible) Res.drawable.eyee else Res.drawable.eye_off
                    IconButton(onClick = { isPassVisible = !isPassVisible })
                    {
                        Icon(
                            painter = painterResource(icon), contentDescription = null,
                            tint = Color(118, 118, 118, 255),
                            modifier = m.size(24.dp)
                        )
                    }
                }),

                colors = textFieldColors


            )
            Spacer(m.height(28.dp))
            Text(
                text = strings.get("CONF_NEW_PASS"),
                fontFamily = popMid,
                fontSize = 18.sp,
                fontWeight = FontWeight.W500,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        listOf(Color(194, 0, 0, 255), Color(92, 0, 0, 255))
                    )
                ),
            )
            OutlinedTextField(
                modifier = m.fillMaxWidth(),
                value = state.confNewPass,
                onValueChange = {
                    viewModel.onConfNewPassChange(it)
                },
                placeholder = {
                    Text(
                        text = if (state.confNewPassError) strings.get("PASSWORD_REQUIRED") else strings.get(
                            "PASSWORD"
                        ),
                        fontFamily = popMid,
                        fontSize = 12.sp,
                        color = if (state.confNewPassError) Color(194, 0, 0, 255) else Color(
                            30,
                            30,
                            30,
                            168
                        )
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done

                ),
                isError = state.confNewPassError,
                visualTransformation = if (isPassVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                trailingIcon = ({
                    val icon =
                        if (isPassVisible) Res.drawable.eyee else Res.drawable.eye_off
                    IconButton(onClick = { isPassVisible = !isPassVisible })
                    {
                        Icon(
                            painter = painterResource(icon), contentDescription = null,
                            tint = Color(118, 118, 118, 255),
                            modifier = m.size(24.dp)
                        )
                    }
                }),

                colors = textFieldColors


            )
            Spacer(m.height(64.dp))
            Card(
                onClick = {
                    if (!isConnected) {
                        showNoInternetDialog = true
                        confirmButtonPressed = true
                    } else {
                        viewModel.changePass()
                    }
                },
                modifier = m
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color(30, 30, 30, 110),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(shape = RoundedCornerShape(8.dp))
                    .appButtonBack(),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),

                ) {

                Row(
                    modifier = m.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        strings.get("CONF_BTTN_PASS"),
                        fontFamily = popMid,
                        fontSize = 22.sp,
                        color = Color(245, 245, 245, 255)
                    )
                }
            } // confirm button
            Spacer(m.height(64.dp))


        }
    }
    if (showNoInternetDialog && confirmButtonPressed) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
                .blur(10.dp)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            awaitPointerEvent()
                        }
                    }
                }
        )
        NoInternetDialog {
            showNoInternetDialog = false
            confirmButtonPressed = false
        }
    }

    if (state.isLoading) {
        LoadingOverlay()
    }
}