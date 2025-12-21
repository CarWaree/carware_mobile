package com.example.carware.screens.auth

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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import carware.composeapp.generated.resources.poppins_medium
import carware.composeapp.generated.resources.poppins_semibold
import com.example.carware.LocalStrings
import com.example.carware.m
import com.example.carware.navigation.AddCarScreen
import com.example.carware.navigation.LoginScreen
import com.example.carware.navigation.SignUpScreen
import com.example.carware.network.Api.loginUser
import com.example.carware.network.apiRequests.auth.SignUpRequest
import com.example.carware.network.Api.signupUser
import com.example.carware.screens.appButtonBack
import com.example.carware.screens.appGradBack
import com.example.carware.util.lang.AppLanguage
import com.example.carware.util.storage.PreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun SignUpScreen(
    navController: NavController,
    preferencesManager: PreferencesManager,
    onLangChange: (AppLanguage) -> Unit // Add this
) {
    val strings = LocalStrings.current
    val currentLang = AppLanguage.fromCode(preferencesManager.getLanguageCode())


    val popSemi = FontFamily(
        Font(Res.font.poppins_semibold) // name of your font file without extension
    )

    val popMid = FontFamily(Font(Res.font.poppins_medium))

    var fName by remember { mutableStateOf("") }
    var lName by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var num by remember { mutableStateOf("") }

    var isPassVisible by remember { mutableStateOf(false) }

    var confPass by remember { mutableStateOf("") }
    var userNameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passError by remember { mutableStateOf(false) }
    var numError by remember { mutableStateOf(false) }
    var confPassError by remember { mutableStateOf(false) }
    var agreed by remember { mutableStateOf(false) }

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

//    val token = preferencesManager.getToken()


    Column(modifier = m.verticalScroll(scrollState)) {
        Column(
            modifier = m
                .fillMaxSize()
                .appGradBack()
                .padding(top = 100.dp)


        ) {
            Box(
                modifier = m
                    .padding(start = 95.dp),
            ) {
                Icon(
                    painter = painterResource(Res.drawable.line_1),
                    contentDescription = null,
                    tint = Color(211, 203, 203, 255)
                )

            }
            Spacer(modifier = m.padding(vertical = 4.dp))

            Box(
                modifier = m.padding(start = 95.dp),
            ) {
                Icon(
                    painter = painterResource(Res.drawable.carware),
                    contentDescription = null,
                    tint = Color(211, 203, 203, 255)
                )


            }
            Spacer(modifier = m.padding(vertical = 20.dp))

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
                                value = fName,
                                onValueChange = { fName = it },
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
                                value = lName,
                                onValueChange = { lName = it },
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
                            value = userName,
                            onValueChange = {
                                userName = it
                                userNameError = false
                            },
                            placeholder = {
                                Text(
                                    text = if (userNameError) strings.get("USERNAME_REQUIRED")
                                    else strings.get("USERNAME"),
                                    fontFamily = popMid,
                                    fontSize = 12.sp,
                                    color = if (userNameError) Color(194, 0, 0, 255) else Color(
                                        30,
                                        30,
                                        30,
                                        168
                                    )
                                )
                            },
                            isError = userNameError,

                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            colors = textFieldColors

                        ) //user name field
                        Spacer(modifier = m.padding(vertical = 8.dp))
                        OutlinedTextField(
                            modifier = m.size(280.dp, 55.dp),
                            value = email,
                            onValueChange = {
                                email = it
                                emailError = false
                            },
                            placeholder = {
                                Text(
                                    text = if (emailError) strings.get("EMAIL_REQUIRED")
                                    else strings.get("EMAIL"),
                                    fontFamily = popMid,
                                    fontSize = 12.sp,
                                    color = if (emailError) Color(194, 0, 0, 255) else Color(
                                        30,
                                        30,
                                        30,
                                        168
                                    )
                                )
                            },
                            isError = emailError,
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
                            value = num,
                            onValueChange = {
                                num = it
                                passError = false
                            },
                            placeholder = {
                                Text(
                                    text = if (numError) strings.get("PHONE_REQUIRED") else strings.get("PHONE"),
                                    fontFamily = popMid,
                                    fontSize = 12.sp,
                                    color = if (numError) Color(194, 0, 0, 255) else Color(
                                        30,
                                        30,
                                        30,
                                        168
                                    )
                                )
                            },
                            isError = numError,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Phone
                            ),
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            colors = textFieldColors


                        )//number field
                        Spacer(modifier = m.padding(vertical = 8.dp))
                        OutlinedTextField(

                            modifier = m.size(280.dp, 55.dp),
                            value = pass,
                            onValueChange = {
                                pass = it
                                passError = false
                            },
                            placeholder = {
                                Text(
                                    text = if (passError) strings.get("PASSWORD_REQUIRED") else strings.get("PASSWORD"),
                                    fontFamily = popMid,
                                    fontSize = 12.sp,
                                    color = if (passError) Color(194, 0, 0, 255) else Color(
                                        30,
                                        30,
                                        30,
                                        168
                                    )

                                )
                            },
                            isError = passError,
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
                            value = confPass,
                            onValueChange = {
                                confPass = it
                                confPassError = false
                            },
                            placeholder = {
                                Text(
                                    text = if (confPassError) strings.get("CONFIRM_PASSWORD_REQUIRED") else strings.get("CONFIRM_PASSWORD"),
                                    fontFamily = popMid,
                                    fontSize = 12.sp,
                                    color = if (passError) Color(194, 0, 0, 255) else Color(
                                        30,
                                        30,
                                        30,
                                        168
                                    )

                                )
                            },
                            isError = confPassError,
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
                                selected = agreed,
                                onClick = { agreed = !agreed },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color(0xFFC20000),
                                    unselectedColor = Color.Gray,
                                )
                            )
                            Text(strings.get("AGREE_TERMS"),
                                fontFamily = popMid,
                                fontSize = 12.sp,
                                color = Color(30, 30, 30, 168)

                            )
                        } //terms &conditions

                        Spacer(modifier = m.padding(vertical = 8.dp))
                        //
                        Card(

                            onClick = {
                                // 1️⃣ Validate fields
                                val emailEmpty = email.isBlank()
                                val passwordEmpty = pass.isBlank()
                                val userEmpty = userName.isBlank()
                                val confPassEmpty = confPass.isBlank()
                                val numEmpty = num.isBlank()
                                val passMismatch = pass != confPass
                                val agreedEmpty = !agreed

                                // 2️⃣ Show errors
                                emailError = emailEmpty
                                passError = passwordEmpty
                                userNameError = userEmpty
                                confPassError = confPassEmpty || passMismatch
                                numError = numEmpty

                                if (!emailEmpty && !passwordEmpty && !userEmpty && !confPassEmpty && !numEmpty && !passMismatch && agreed) {
                                    try {
                                        // 3️⃣ Create signup request
                                        val request = SignUpRequest(
                                            firstName = fName,
                                            lastName = lName,
                                            userName = userName,
                                            email = email,
                                            password = pass,
                                            confirmPassword = confPass
                                        )
                                        CoroutineScope(Dispatchers.Default).launch {
                                            try {
                                                val response = signupUser(request)

                                                val token = response.data?.token
                                                    ?: throw IllegalStateException("Token missing in response")

                                                // ✅ Save token (this replaces LoginManager)
                                                preferencesManager.saveToken(token)

                                                withContext(Dispatchers.Main) {
                                                    navController.navigate(AddCarScreen) {
                                                        popUpTo(SignUpScreen) { inclusive = true }
                                                    }
                                                }

                                            } catch (e: Exception) {
                                                withContext(Dispatchers.Main) {
                                                    // show error to user
                                                    println("Login failed: ${e.message}")
                                                }
                                            }

                                        }


                                    } catch (e: Exception) {
                                        // This should rarely happen unless request creation fails
                                        println("Request creation failed: ${e.message}")
                                    }
                                }
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
                                    strings.get("SIGN_IN"),
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
                                strings.get("ALREADY_HAVE_ACCOUNT")
                                , fontFamily = popMid,
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

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("العربية")
                            Switch(
                                checked = currentLang == AppLanguage.AR,
                                onCheckedChange = { isAr ->
                                    val selected = if (isAr) AppLanguage.AR else AppLanguage.EN
                                    onLangChange(selected)
                                }
                            )
                        }


                    }


                }

            }


        }
    }


}



