package com.example.carware.screens.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import carware.composeapp.generated.resources.Res
import carware.composeapp.generated.resources.carware
import carware.composeapp.generated.resources.eye_off
import carware.composeapp.generated.resources.eyee
import carware.composeapp.generated.resources.google_icon_logo_svgrepo_com
import carware.composeapp.generated.resources.line_1
import carware.composeapp.generated.resources.new_logo
import carware.composeapp.generated.resources.poppins_medium
import carware.composeapp.generated.resources.poppins_semibold
import com.example.carware.LocalStrings
import com.example.carware.m
import com.example.carware.navigation.AddCarScreen
import com.example.carware.navigation.EmailVerificationScreen
import com.example.carware.navigation.HomeScreen
import com.example.carware.navigation.LoginScreen
import com.example.carware.navigation.ResetPasswordScreen
import com.example.carware.navigation.SignUpScreen
import com.example.carware.network.apiRequests.auth.LoginRequest
import com.example.carware.network.api.loginUser
import com.example.carware.screens.LoadingOverlay
import com.example.carware.screens.ToastMessage
import com.example.carware.screens.appButtonBack
import com.example.carware.screens.appGradBack
import com.example.carware.util.lang.AppLanguage
import com.example.carware.util.storage.PreferencesManager
import com.example.carware.viewModel.auth.logIn.LogInViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LoginScreen(
    navController: NavController,
    preferencesManager: PreferencesManager,
    viewModel: LogInViewModel
) {
    val state by viewModel.state.collectAsState()

    val strings = LocalStrings.current
    val popSemi = FontFamily(
        Font(Res.font.poppins_semibold) // name of your font file without extension
    )

    val popMid = FontFamily(Font(Res.font.poppins_medium))


    var isPassVisible by remember { mutableStateOf(false) }


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
        navController.navigate(HomeScreen) {
            popUpTo(LoginScreen) { inclusive = true }
        }
    }
}

    Column (m
        .fillMaxSize()
        .appGradBack()
        .padding(64.dp)
    ){
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



    Column(
        modifier = m.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        ElevatedCard(
            modifier = m
                .size(width = 330.dp, height = 500.dp), // change height to 485.dp
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(217, 217, 217, 255)
            ),
        )
        {
            Spacer(modifier = m.padding(top = 24.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = strings.get("WELCOME_BACK"),
                    fontFamily = popSemi,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(30, 30, 30, 168)

                ) // welcome text
                Text(
                    text = strings.get("KEEP_YOUR_CAR"),
                    fontFamily = popSemi,
                    fontSize = 13.sp,
                    color = Color(30, 30, 30, 168)

                ) // login text
                Spacer(modifier = m.padding(top = 16.dp))


                OutlinedTextField(
                    modifier = m.size(280.dp, 55.dp),
                    value = state.emailOrUsername,
                    onValueChange = {
                        viewModel.onEmailOrUsernameChange(it)
                    },
                    placeholder = {
                        Text(
                            text = if (state.emailOrUsernameError) strings.get("USERNAME_REQUIRED")
                            else
                                strings.get("EMAIL"),
                            fontFamily = popMid,
                            fontSize = 12.sp,
                            color = if (state.emailOrUsernameError) Color(194, 0, 0, 255)
                            else Color(
                                30,
                                30,
                                30,
                                168
                            )
                        )
                    },
                    isError = state.emailOrUsernameError,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    colors = textFieldColors

                ) //email field

                Spacer(modifier = m.padding(vertical = 8.dp))
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
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password
                    ),
                    isError = state.passError,
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


                ) // pass field

                Spacer(modifier = m.padding(vertical = 6.dp))


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp),
                    horizontalArrangement = Arrangement.End
                ) {

                    Text(
                        strings.get("FORGOT_PASSWORD"),
                        fontFamily = popMid,
                        fontSize = 15.sp,
                        color = Color(194, 0, 0, 255),
                        modifier = m.clickable { navController.navigate(ResetPasswordScreen) }

                    )
                } // forget pass text
                Spacer(modifier = m.padding(vertical = 8.dp))




                Card(
                    onClick = { viewModel.login() },
                    modifier = m

                        .size(width = 280.dp, height = 45.dp)
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
                            strings.get("LOGIN"),
                            fontFamily = popSemi,
                            fontSize = 18.sp,
                            color = Color(217, 217, 217, 255)
                        )
                    }
                } // login button

                Spacer(modifier = m.padding(vertical = 6.dp))

                Card(
                    onClick = { /*Google sign up code */ },
                    modifier = m
                        .size(width = 280.dp, height = 45.dp)
                        .border(
                            width = 1.dp,
                            color = Color(30, 30, 30, 110),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clip(shape = RoundedCornerShape(8.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent),

                    ) {
                    Row(
                        modifier = m
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.google_icon_logo_svgrepo_com),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = m.size(16.dp)
                        )
                        Text(
                            strings.get("CONTINUE_GOOGLE"), fontFamily = popSemi,
                            fontSize = 16.sp,
                            color = Color(30, 30, 30, 163)
                        )

                    }


                } // google sign in button
                Spacer(modifier = m.padding(vertical = 8.dp))
                Row(
                    m

                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        strings.get("DONT_HAVE_ACCOUNT"), fontFamily = popMid,
                        fontSize = 12.sp,
                        color = Color(30, 30, 30, 168)
                    )
                    Text(
                        strings.get("SIGN_IN"), fontFamily = popMid,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(194, 0, 0, 255),
                        modifier = m.clickable { navController.navigate(SignUpScreen) }
                    )

                } // forget pass textbutton
                Spacer(m.padding(vertical = 4.dp))
                Text(
                    strings.get("BY_LOGGING_IN"),
                    fontSize = 12.sp,
                    fontFamily = popMid,
                    color = Color(118, 118, 118, 255)
                )
                // terms and conditions
                Text(
                    strings.get("TERMS_AND_CONDITIONS"),
                    fontSize = 12.sp,
                    fontFamily = popMid,
                    fontWeight = FontWeight.Bold,
                    color = Color(114, 114, 114, 255),
                    modifier = m.clickable { /*privacy and terms page*/ }
                )


            }


        }


    }

    if (state.isLoading) {
        LoadingOverlay()
    }

}





