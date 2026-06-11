package com.example.carware.nav.Screens

import com.example.carware.R
import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.carware.nav.ResetPasswordScreen
import com.example.carware.nav.SignUpScreen


val m = Modifier
val popSemi = FontFamily(
    Font(R.font.poppins_semibold) // name of your font file without extension
)
val popMid = FontFamily(
    Font(R.font.poppins_medium) // name of your font file without extension
)


@Composable

fun LoginScreen(navController: NavController) {

    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

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

        Column(
            modifier = m.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ElevatedCard(
                modifier = m
                    .size(width = 330.dp, height = 485.dp),
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
                        "Welcome back!",
                        fontFamily = popSemi,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(30, 30, 30, 168)

                    )
                    Text(
                        "Login and keep your car at its best",
                        fontFamily = popSemi,
                        fontSize = 13.sp,
                        color = Color(30, 30, 30, 168)

                    )
                    Spacer(modifier = m.padding(top = 16.dp))

                    OutlinedTextField(
                        modifier = m.size(280.dp, 55.dp),
                        value = email,
                        onValueChange = { email = it },
                        placeholder = {
                            Text(
                                "Email",
                                fontFamily = popMid,
                                fontSize = 12.sp,
                                color = Color(30, 30, 30, 168)

                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Email
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
                        )
                    )
                    Spacer(modifier = m.padding(vertical = 8.dp))


                    OutlinedTextField(

                        modifier = m.size(280.dp, 55.dp),
                        value = pass,
                        onValueChange = { pass = it },
                        placeholder = {
                            Text(
                                "Password",
                                fontFamily = popMid,
                                fontSize = 12.sp,
                                color = Color(30, 30, 30, 168)

                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp), trailingIcon = ({
                            Icon(
                                painter = painterResource(id = R.drawable.eye_off),
                                contentDescription = null,
                                tint = Color(118, 118, 118, 255)
                            )
                        }), colors = TextFieldDefaults.colors(
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
                        )


                    )
                    Spacer(modifier = m.padding(vertical = 6.dp))


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 20.dp),
                        horizontalArrangement = Arrangement.End
                    ) {

                        Text(
                            "Forgot password?",
                            fontFamily = popMid,
                            fontSize = 15.sp,
                            color = Color(194, 0, 0, 255),
                            modifier = m.clickable {navController.navigate(ResetPasswordScreen) }

                        )
                    }
                    Spacer(modifier = m.padding(vertical = 8.dp))

                    Card(
                        onClick = { /* Login code*/ },
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
                                "Log In",
                                fontFamily = popSemi,
                                fontSize = 18.sp,
                                color = Color(217, 217, 217, 255)
                            )
                        }
                    }

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
                                painter = painterResource(id = R.drawable.google_icon_logo_svgrepo_com),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = m.size(16.dp)
                            )
                            Text(
                                " Continue with Google", fontFamily = popSemi,
                                fontSize = 16.sp,
                                color = Color(30, 30, 30, 163)
                            )

                        }


                    }
                    Spacer(modifier = m.padding(vertical = 8.dp))
                    Row(
                        m

                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    )
                    {
                        Text(
                            "Don’t have an account? ", fontFamily = popMid,
                            fontSize = 12.sp,
                            color = Color(30, 30, 30, 168)
                        )
                        Text(
                            "Sign Up ", fontFamily = popMid,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(194, 0, 0, 255),
                            modifier = m.clickable { navController.navigate(SignUpScreen) }
                        )

                    }
                    Spacer(m.padding(vertical = 4.dp))


                    Text(
                        "By logging in, you accept our ",
                        fontSize = 12.sp,
                        fontFamily = popMid,
                        color = Color(118, 118, 118, 255)
                    )
                    Text(
                        "Terms & Conditions and Privacy Policy.",
                        fontSize = 12.sp,
                        fontFamily = popMid,
                        fontWeight = FontWeight.Bold,
                        color = Color(114, 114, 114, 255),
                        modifier = m.clickable { /*privacy and terms page*/ }
                    )


                }


            }

        }


    }


}


@Composable
fun loginDemo() {

    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

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
                    .size(width = 325.dp, height = 485.dp),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(217, 217, 217, 255)
                ),

                ) {
                Spacer(modifier = m.padding(top = 20.dp))
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Welcome back!",
                        fontFamily = popSemi,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(30, 30, 30, 168)

                    )
                    Text(
                        "Login and keep your car at its best",
                        fontFamily = popSemi,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(30, 30, 30, 168)

                    )
                    Spacer(modifier = m.padding(top = 18.dp))

                    OutlinedTextField(
                        modifier = m.size(280.dp, 55.dp),
                        value = email,
                        onValueChange = { email = it },
                        label = {
                            Text(
                                "Email",
                                fontFamily = popMid,
                                fontSize = 12.sp,
                                color = Color(30, 30, 30, 168)

                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Email
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )
                    Spacer(modifier = m.padding(vertical = 8.dp))


                    OutlinedTextField(
                        modifier = m.size(280.dp, 55.dp),
                        value = pass,
                        onValueChange = { pass = it },
                        label = {
                            Text(
                                "Password",
                                fontFamily = popMid,
                                fontSize = 12.sp,
                                color = Color(30, 30, 30, 168)

                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp), trailingIcon = ({
                            Icon(
                                painter = painterResource(id = R.drawable.eye_off),
                                contentDescription = null,
                                tint = Color(118, 118, 118, 255)
                            )
                        })
                    )
                    Spacer(modifier = m.padding(vertical = 6.dp))


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 20.dp),
                        horizontalArrangement = Arrangement.End
                    ) {

                        Text(
                            "Forgot password?",
                            fontFamily = popMid,
                            fontSize = 15.sp,
                            color = Color(194, 0, 0, 255),
                            modifier = m.clickable { }

                        )
                    }
                    Spacer(modifier = m.padding(vertical = 8.dp))

                    Card(
                        onClick = { /* handle click */ },
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
                                "Log In",
                                fontFamily = popSemi,
                                fontSize = 18.sp,
                                color = Color(217, 217, 217, 255)
                            )
                        }
                    }

                    Spacer(modifier = m.padding(vertical = 6.dp))

                    Card(
                        onClick = { /* handle click */ },
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
                                painter = painterResource(id = R.drawable.google_icon_logo_svgrepo_com),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = m.size(16.dp)
                            )
                            Text(
                                " Continue with Google", fontFamily = popSemi,
                                fontSize = 16.sp,
                                color = Color(30, 30, 30, 163)
                            )

                        }


                    }
                    Spacer(modifier = m.padding(vertical = 8.dp))

                    Text(
                        "Don’t have an account? ", fontFamily = popMid,
                        fontSize = 12.sp,
                        color = Color(30, 30, 30, 168)
                    )
                    Text(
                        "Sign Up ", fontFamily = popMid,
                        fontSize = 12.sp,
                        color = Color(194, 0, 0, 255),
                        modifier = m.clickable { }
                    )

                    Spacer(m.padding(vertical = 8.dp))



                    Text(
                        "By logging in, you accept our ",
                        fontSize = 12.sp,
                        fontFamily = popMid,
                        color = Color(118, 118, 118, 255)
                    )
                    Text(
                        "Terms & Conditions\n" +
                                " and Privacy Policy.",
                        fontSize = 12.sp,
                        fontFamily = popMid,
                        fontWeight = FontWeight.Bold,
                        color = Color(114, 114, 114, 255),
                        modifier = m.clickable { }
                    )


                }


            }

        }


    }


}


@Preview(showBackground = true)
@Composable
private fun loginPreview() {
    loginDemo()
}

//     TextButton({ navController.navigate(SignUpScreen)}) { Text("Sign Up") }


@SuppressLint("SuspiciousModifierThen")
fun Modifier.appGradBack(): Modifier = this.then(
    background(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color(0xFFC20000),
                Color(0xFF5C0000)
            )
        )
    )
)

@SuppressLint("SuspiciousModifierThen")
fun Modifier.appButtonBack(): Modifier = this.then(
    background(
        brush = Brush.horizontalGradient(
            colors = listOf(
                Color(0xFFC20000),
                Color(0xFF5C0000)
            )
        )
    )
)

