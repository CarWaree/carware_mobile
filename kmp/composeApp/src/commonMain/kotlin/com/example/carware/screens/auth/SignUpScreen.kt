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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import carware.composeapp.generated.resources.Res
import carware.composeapp.generated.resources.eye_off
import carware.composeapp.generated.resources.eyee
import carware.composeapp.generated.resources.google_icon_logo_svgrepo_com
import carware.composeapp.generated.resources.new_logo
import carware.composeapp.generated.resources.poppins_medium
import carware.composeapp.generated.resources.poppins_semibold
import com.example.carware.LocalStrings
import com.example.carware.m
import com.example.carware.navigation.EmailVerificationScreen
import com.example.carware.navigation.LoginScreen
import com.example.carware.navigation.SignUpScreen
import com.example.carware.screens.LoadingOverlay
import com.example.carware.screens.ToastMessage
import com.example.carware.screens.appButtonBack
import com.example.carware.screens.appGradBack
import com.example.carware.util.lang.AppLanguage
import com.example.carware.util.storage.PreferencesManager
import com.example.carware.viewModel.auth.signUp.SignUpViewModel
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun SignUpScreen(
    navController: NavController,
    preferencesManager: PreferencesManager,
    viewModel: SignUpViewModel
) {
    val strings = LocalStrings.current
    val currentLang = AppLanguage.fromCode(preferencesManager.getLanguageCode())

    val state by viewModel.state.collectAsState()


    val popSemi = FontFamily(
        Font(Res.font.poppins_semibold) // name of your font file without extension
    )

    val popMid = FontFamily(Font(Res.font.poppins_medium))

    var isPassVisible by remember { mutableStateOf(false) }


    val scrollState = rememberScrollState()
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

    LaunchedEffect(state.needsEmailVerification) {
        if (state.needsEmailVerification) {
            navController.navigate(EmailVerificationScreen) {  // Create this screen
                popUpTo(SignUpScreen) { inclusive = true }
            }
        }
    }



    Column(modifier = m.verticalScroll(scrollState)) {
        Column(
            modifier = m
                .fillMaxSize()
                .appGradBack()
                .padding(top = 54.dp)


        ) {
            Column(
                m
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (state.needsEmailVerification) {
                    ToastMessage(message = "Check your email to verify your account", state = true)
                }
                if (state.errorMessage!=null) {
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
                }else {
                    Icon(
                        painter = painterResource(Res.drawable.new_logo),
                        contentDescription = null,
                        tint = Color(211, 203, 203, 255),
                        modifier = m.scale(0.7f)
                    )
                }

            }


            Column(
                modifier = m
                    .fillMaxSize(),
                //                .verticalScroll(scrollState)
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Card(
                    modifier = m
                        .padding(horizontal = 14.dp)
                        .padding(vertical = 15.dp)
                        .clip(RoundedCornerShape(16.dp)),

                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(217, 217, 217, 255)
                    ),

                    ) {
                    Spacer(modifier = m.padding(top = 24.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            strings.get("WELCOME_MESSAGE"),
                            fontFamily = popSemi,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(30, 30, 30, 168)

                        )  //welcome
                        Text(
                            strings.get("MARKETING_SLOGAN"),
                            fontFamily = popSemi,
                            fontSize = 12.sp,
                            color = Color(30, 30, 30, 168)

                        )  // 2ndline
//                        Text(
//                            " CarWare today .",
//                            fontFamily = popSemi,
//                            fontSize = 12.sp,
//                            color = Color(30, 30, 30, 168)
//
//                        )  // 3rd line
                        Spacer(modifier = m.padding(vertical = 10.dp))

                        Row(
                            m
                                .fillMaxWidth()
                                .size(280.dp, 50.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {


                            OutlinedTextField(
                                modifier = m.size(125.dp, 55.dp),
                                value = state.firstName,
                                onValueChange = { viewModel.onFirstNameChange(it) },
                                placeholder = {
                                    Text(
                                        strings.get("FIRST_NAME"),
                                        fontFamily = popMid,
                                        fontSize = 12.sp,
                                        color = Color(30, 30, 30, 168)
                                    )
                                },
                                singleLine = true,
                                shape = RoundedCornerShape(8.dp),
                                colors = textFieldColors
                            ) //Fname field
                            Spacer(modifier = m.padding(horizontal = 12.dp))
                            OutlinedTextField(
                                modifier = m.size(130.dp, 55.dp),
                                value = state.lastName,
                                onValueChange = { viewModel.onLastNameChange(it) },
                                placeholder = {
                                    Text(
                                        strings.get("LAST_NAME"),
                                        fontFamily = popMid,
                                        fontSize = 12.sp,
                                        color = Color(30, 30, 30, 168)
                                    )
                                },
                                singleLine = true,
                                shape = RoundedCornerShape(8.dp),
                                colors = textFieldColors
                            ) //Lname field

                        } // fname &lname

                        Spacer(modifier = m.padding(vertical = 8.dp))
                        OutlinedTextField(
                            modifier = m.size(280.dp, 55.dp),
                            value = state.userName,
                            onValueChange = {
                                viewModel.onUserNameChange(it)
                            },
                            placeholder = {
                                Text(
                                    text = if (state.userNameError) strings.get("USERNAME_REQUIRED")
                                    else strings.get("USERNAME"),
                                    fontFamily = popMid,
                                    fontSize = 12.sp,
                                    color = if (state.userNameError) Color(
                                        194,
                                        0,
                                        0,
                                        255
                                    ) else Color(
                                        30,
                                        30,
                                        30,
                                        168
                                    )
                                )
                            },
                            isError = state.userNameError,

                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            colors = textFieldColors

                        ) //user name field
                        Spacer(modifier = m.padding(vertical = 8.dp))
                        OutlinedTextField(
                            modifier = m.size(280.dp, 55.dp),
                            value = state.email,
                            onValueChange = {
                                viewModel.onEmailChange(it)
                            },
                            placeholder = {
                                Text(
                                    text = if (state.emailError) strings.get("EMAIL_REQUIRED")
                                    else strings.get("EMAIL"),
                                    fontFamily = popMid,
                                    fontSize = 12.sp,
                                    color = if (state.emailError) Color(194, 0, 0, 255) else Color(
                                        30,
                                        30,
                                        30,
                                        168
                                    )
                                )
                            },
                            isError = state.emailError,
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
                            isError = state.passError,
                            visualTransformation = if (isPassVisible) VisualTransformation.None
                            else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Password
                            ),
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            trailingIcon = ({
                                val icon =
                                    if (isPassVisible) Res.drawable.eyee else Res.drawable.eye_off
                                IconButton(onClick = { isPassVisible = !isPassVisible }) {
                                    Icon(
                                        painter = painterResource(icon),
                                        contentDescription = null,
                                        tint = Color(118, 118, 118, 255),
                                        modifier = m.size(24.dp)
                                    )
                                }
                            }), colors = textFieldColors


                        ) //password field
                        Text(
                            strings.get("PASSWORD_REQUIREMENTS"),
                            fontFamily = popMid,
                            fontSize = 10.sp,
                            color = Color(30, 30, 30, 168)

                        )  // min pass text
                        Spacer(modifier = m.padding(vertical = 4.dp))
                        OutlinedTextField(

                            modifier = m.size(280.dp, 55.dp),
                            value = state.confPass,
                            onValueChange = {
                                viewModel.onConfirmPasswordChange(it)
                            },
                            placeholder = {
                                Text(
                                    text = if (state.confPassError) strings.get("CONFIRM_PASSWORD_REQUIRED") else strings.get(
                                        "CONFIRM_PASSWORD"
                                    ),
                                    fontFamily = popMid,
                                    fontSize = 12.sp,
                                    color = if (state.confPassError) Color(
                                        194,
                                        0,
                                        0,
                                        255
                                    ) else Color(
                                        30,
                                        30,
                                        30,
                                        168
                                    )

                                )
                            },
                            isError = state.confPassError,
                            visualTransformation = if (isPassVisible) VisualTransformation.None
                            else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Password
                            ),
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            trailingIcon = ({
                                val icon =
                                    if (isPassVisible) Res.drawable.eyee else Res.drawable.eye_off
                                IconButton(onClick = { isPassVisible = !isPassVisible }) {
                                    Icon(
                                        painter = painterResource(icon),
                                        contentDescription = null,
                                        tint = Color(118, 118, 118, 255),
                                        modifier = m.size(24.dp)
                                    )
                                }
                            }), colors = textFieldColors


                        ) //password match field
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            RadioButton(
                                selected = state.agreeTerms,
                                onClick = { viewModel.onToggleAgree(!state.agreeTerms) },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color(0xFFC20000),
                                    unselectedColor = Color.Gray,
                                )
                            )
                            Text(
                                strings.get("AGREE_TERMS"),
                                fontFamily = popMid,
                                fontSize = 12.sp,
                                color = Color(30, 30, 30, 168)

                            )
                        } //terms &conditions

                        Spacer(modifier = m.padding(vertical = 8.dp))
                        //
                        Card(

                            onClick = {
                                viewModel.signup()
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
                                    strings.get("CREATE_ACCOUNT"),
                                    fontFamily = popSemi,
                                    fontSize = 18.sp,
                                    color = Color(217, 217, 217, 255)
                                )
                            }
                        } //sign up button

                        Spacer(modifier = m.padding(vertical = 8.dp))

                        Card(
                            onClick = { /* handle click */ },
                            modifier = m
                                .size(width = 280.dp, height = 50.dp)
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
                                ) //CONT with google text

                            }


                        } //google button
                        Spacer(modifier = m.padding(vertical = 8.dp))
                        Row() {
                            Text(
                                strings.get("ALREADY_HAVE_ACCOUNT"), fontFamily = popMid,
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
                        }
                        Spacer(Modifier.padding(vertical = 8.dp))


                    }


                }

            }


        }
    }

    if (state.isLoading) {
        LoadingOverlay()
    }

}



