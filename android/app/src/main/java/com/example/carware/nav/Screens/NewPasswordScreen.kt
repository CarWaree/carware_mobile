package com.example.carware.nav.Screens

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.carware.R
import com.example.carware.nav.LoginScreen
import com.example.carware.nav.VerficationCodeScreen

@Composable
fun NewPasswordScreen(navController: NavController) {

    var pass by remember { mutableStateOf("") }
    var pass2 by remember { mutableStateOf("") }


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
                painter = painterResource(id = R.drawable.line_1),
                contentDescription = null,
                tint = Color(211, 203, 203, 255)
            )

        }
        Spacer(modifier = m.padding(vertical = 4.dp))
        Box(
            modifier = m.padding(start = 95.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.carware),
                contentDescription = null,
                tint = Color(211, 203, 203, 255)
            )


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
                        "Create New Password",
                        fontFamily = popSemi,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(30, 30, 30, 168)

                    ) //main text
                    Text(
                        "Create your Password",
                        fontFamily = popSemi,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(30, 30, 30, 168)

                    ) // sec text

                    Spacer(modifier = m.padding(top = 18.dp))
                    OutlinedTextField(
                        modifier = m.size(280.dp, 55.dp),
                        value = pass,
                        onValueChange = {
                            pass = it
                        },
                        placeholder = {
                            Text(
                                "New Password",
                                fontFamily = popMid,
                                fontSize = 12.sp,
                                color = Color(30, 30, 30, 168)

                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color(
                                30,
                                30,
                                30,
                                168
                            ),   // green border when focused
                            unfocusedIndicatorColor = Color(
                                30,
                                30,
                                30,
                                168
                            ), // gray border when not focused
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.DarkGray,
                            cursorColor = Color.Black
                        ),

                        ) //new pass field
                    Row (m.fillMaxWidth()
                        .padding(start = 32.dp)
                        , horizontalArrangement = Arrangement.Start
                    ){  Text(
                        "minimum 8 characters,letters and numbers",
                        fontFamily = popSemi,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(30, 30, 30, 168)

                    )

                    } // minimum text
                    Spacer(modifier = m.padding(top = 8.dp))
                    OutlinedTextField(
                        modifier = m.size(280.dp, 55.dp),
                        value = pass2,
                        onValueChange = {
                            pass2 = it
                        },
                        placeholder = {
                            Text(
                                "Confirm Password",
                                fontFamily = popMid,
                                fontSize = 12.sp,
                                color = Color(30, 30, 30, 168)

                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color(
                                30,
                                30,
                                30,
                                168
                            ),   // green border when focused
                            unfocusedIndicatorColor = Color(
                                30,
                                30,
                                30,
                                168
                            ), // gray border when not focused
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.DarkGray,
                            cursorColor = Color.Black
                        ),

                        ) //conf pass field
                    Spacer(modifier = m.padding(top = 12.dp))
                    Card(
                        onClick = { /* handle click */ },
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
                                "Continue",
                                fontFamily = popSemi,
                                fontSize = 18.sp,
                                color = Color(217, 217, 217, 255)
                            )
                        }
                    } // continue button
                    Spacer(modifier = m.padding(top = 12.dp))
                    Row {
                        Text(
                            "Back to ", fontFamily = popMid,
                            fontSize = 12.sp,
                            color = Color(30, 30, 30, 168)
                        )
                        Text(
                            "Log in ", fontFamily = popMid,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(194, 0, 0, 255),
                            modifier = m.clickable {navController.navigate(LoginScreen) }
                        )
                    } //back to login textbutton




                }


            }


        }
    }
}


