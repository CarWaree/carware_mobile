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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import carware.composeapp.generated.resources.Res
import carware.composeapp.generated.resources.carware
import carware.composeapp.generated.resources.line_1
import carware.composeapp.generated.resources.poppins_medium
import carware.composeapp.generated.resources.poppins_semibold
import com.example.carware.LocalStrings
import com.example.carware.m
import com.example.carware.navigation.LoginScreen
import com.example.carware.navigation.NewPasswordScreen
import com.example.carware.network.apiRequests.auth.OTPRequest
import com.example.carware.network.api.otpVerificationUser
import com.example.carware.screens.appButtonBack
import com.example.carware.screens.appGradBack
import com.example.carware.util.lang.AppLanguage
import com.example.carware.util.storage.PreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun VerificationCodeScreen(navController: NavController,
                           preferencesManager: PreferencesManager ,
) {
    val strings = LocalStrings.current
    val currentLang = AppLanguage.fromCode(preferencesManager.getLanguageCode())
    val popSemi = FontFamily(
        Font(Res.font.poppins_semibold) // name of your font file without extension
    )

    val popMid = FontFamily(Font(Res.font.poppins_medium))

    val previousEntry = navController.previousBackStackEntry
    val previousRoute = previousEntry?.destination?.route

    val cameFromSignup = previousRoute?.contains("SignUpScreen") == true
    val cameFromReset = previousRoute?.contains("ResetPasswordScreen") == true

    var OTP by remember { mutableStateOf("") }
    var isErrorOTP by remember { mutableStateOf(false) }

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

        Column(modifier = m.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

            Card(
                modifier = m
                    .size(width = 325.dp, height = 350.dp),
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
                        strings.get("ENTER_CODE"),
                        fontFamily = popSemi,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(30, 30, 30, 168),


                        )
                    Text(
                        strings.get("OTP_EMAIL"),
                        fontFamily = popSemi,
                        fontSize = 12.sp,
                        color = Color(30, 30, 30, 168)

                    )
                    Spacer(modifier = m.padding(vertical = 8.dp))
                    Text(
                        strings.get("ENTER_CODE_SUBTITLE"),
                        fontFamily = popSemi,
                        fontSize = 12.sp,
                        color = Color(30, 30, 30, 168)

                    )
                    OutlinedTextField(
                        modifier = m.size(280.dp, 50.dp),
                        value = OTP,
                        onValueChange = {
                            if (it.length <= 6) OTP = it
                            isErrorOTP
                        },
                        placeholder = {
                            Text(
                                text = if (isErrorOTP) strings.get("OTP_REQUIRED") else strings.get("OTP"),
                                fontFamily = popMid,
                                fontSize = 12.sp,
                                color = if (isErrorOTP) Color(194, 0, 0, 255)
                                else Color(
                                    30,
                                    30,
                                    30,
                                    168
                                )
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.NumberPassword
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = textFieldColors
                    ) // otp


                    Row(
                        m.fillMaxWidth()
                            .padding(start = 32.dp), horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            strings.get("CODE_EXPIRED"),
                            fontFamily = popSemi,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(30, 30, 30, 168)

                        )

                    }
                    Spacer(modifier = m.padding(vertical = 8.dp))

                    Card(
                        //                    when {
//                        cameFromSignup -> navController.navigate(HomeScreen)
//                        cameFromReset -> navController.navigate(NewPasswordScreen)
//                        else -> navController.popBackStack() // fallback
                        //}
                        onClick = {
                            isErrorOTP = OTP.isBlank()
                            if (!isErrorOTP) {
                                try {
                                    // 3️⃣ Create signup request
                                    val request = OTPRequest(
                                        otp = OTP
                                    )

                                    // 4️⃣ Launch coroutine to call API
                                    CoroutineScope(Dispatchers.Default).launch {
                                        try {
                                            val response = 
                                                otpVerificationUser(request)

                                            withContext(Dispatchers.Main) {
                                                // Use performLogin to save the token and potentially the user ID if available
                                                preferencesManager.performLogin(
                                                    token = response.data.token,
                                                )
                                                navController.navigate(NewPasswordScreen)
                                            }


                                        } catch (e: Exception) {
                                            withContext(Dispatchers.Main) {
                                                //  Handle error
                                                println("email  failed: ${e.message}")
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
                                strings.get("CONTINUE"),
                                fontFamily = popSemi,
                                fontSize = 18.sp,
                                color = Color(217, 217, 217, 255)
                            )
                        }
                    } // continue button

                    Spacer(modifier = m.padding(vertical = 8.dp))
                    Row {
                        Text(
                            strings.get("OTP_NOT_SENT"), fontFamily = popMid,
                            fontSize = 12.sp,
                            color = Color(30, 30, 30, 168)
                        )
                        Text(
                            strings.get("SEND_AGAIN"), fontFamily = popMid,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(194, 0, 0, 255),
                            modifier = m.clickable {/*same as cont*/ }
                        )
                    }

                    Spacer(modifier = m.padding(vertical = 8.dp))

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
                    }

                    Spacer(m.padding(vertical = 8.dp))

                }


            }

        }


    }


}


