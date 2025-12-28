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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import carware.composeapp.generated.resources.Res
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
    preferencesManager: PreferencesManager,
    viewModel: NewPasswordViewModel
) {

    val state by viewModel.state.collectAsState()

    val strings = LocalStrings.current
    val popSemi = FontFamily(
        Font(Res.font.poppins_semibold) // name of your font file without extension
    )

    val popMid = FontFamily(Font(Res.font.poppins_medium))


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


    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            navController.navigate(LoginScreen) {
                popUpTo(NewPasswordScreen) { inclusive = true }
            }
        }
    }

    Column(
        m
            .fillMaxSize()
            .appGradBack()
            .padding(64.dp)
    ) {
        Column(
            m
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = state.errorMessage != null,
                enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
                modifier = Modifier
                    .padding(top = 20.dp) // Gap from the very top of the phone
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

    Spacer(modifier = m.padding(vertical = 20.dp))

    Column(modifier = m.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        Card(
            modifier = m
                .size(width = 325.dp, height = 365.dp),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(217, 217, 217, 255)
            ),

            ) {
            Spacer(modifier = m.padding(top = 32.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    strings.get("CREATE_NEW_PASSWORD"),
                    fontFamily = popSemi,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(30, 30, 30, 168)

                ) //main text
                Text(
                    strings.get("CREATE_YOUR_PASSWORD"),
                    fontFamily = popSemi,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(30, 30, 30, 168)

                ) // sec text

                Spacer(modifier = m.padding(top = 18.dp))
                OutlinedTextField(

                    modifier = m.size(280.dp, 55.dp),
                    value = state.pass,
                    onValueChange = {
                        viewModel.onPasswordChange(it)
                    },
                    placeholder = {
                        Text(
                            text = if (state.passError) strings.get("PASSWORD_REQUIRED") else strings.get(
                                "PASSWORD"
                            ),
                            fontFamily = popMid,
                            fontSize = 12.sp,
                            color = if (state.passError) Color(194, 0, 0, 255) else Color(
                                30,
                                30,
                                30,
                                168
                            )

                        )
                    },
                    isError = state.passError,

                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    colors = textFieldColors


                ) //password field
                Row(
                    m.fillMaxWidth()
                        .padding(start = 32.dp), horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        strings.get("PASSWORD_REQUIREMENTS"),
                        fontFamily = popSemi,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(30, 30, 30, 168)

                    )

                } // minimum text
                Spacer(modifier = m.padding(top = 8.dp))
                OutlinedTextField(

                    modifier = m.size(280.dp, 55.dp),
                    value = state.confPass,
                    onValueChange = {
                        viewModel.onConfirmPasswordChange(it)
                    },
                    placeholder = {
                        Text(
                            text = if (state.confPassError) strings.get("CONFIRMATION_REQUIRED") else strings.get(
                                "CONFIRM_PASSWORD"
                            ),
                            fontFamily = popMid,
                            fontSize = 12.sp,
                            color = if (state.confPassError) Color(194, 0, 0, 255) else Color(
                                30,
                                30,
                                30,
                                168
                            )

                        )
                    },
                    isError = state.confPassError,

                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    colors = textFieldColors


                ) //password match field
                Spacer(modifier = m.padding(top = 12.dp))
                Card(
                    onClick = {
                        viewModel.newPassword()
                    },
                    modifier = m

                        .size(width = 280.dp, height = 50.dp)
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
                            strings.get("CONTINUE"),
                            fontFamily = popSemi,
                            fontSize = 18.sp,
                            color = Color(217, 217, 217, 255)
                        )
                    }
                } // continue button
                Spacer(modifier = m.padding(top = 12.dp))
                Row {
                    Text(
                        strings.get("BACK_TO"), fontFamily = popMid,
                        fontSize = 12.sp,
                        color = Color(30, 30, 30, 168)
                    )
                    Text(
                        strings.get("LOGIN"), fontFamily = popMid,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(194, 0, 0, 255),
                        modifier = m.clickable { navController.navigate(LoginScreen) }
                    )
                } //back to login textbutton


            }


        }


    }
    if (state.isLoading) {
        LoadingOverlay()
    }


}
