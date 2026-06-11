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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.carware.R
import com.example.carware.nav.LoginScreen

@Composable
fun SignUpScreen(navController: NavController) {

    var fName by remember { mutableStateOf("") }
    var lName by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var num by remember { mutableStateOf("") }

    var confPass by remember { mutableStateOf("") }
    var userNameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passError by remember { mutableStateOf(false) }
    var numError by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    var agreed by remember { mutableStateOf(false) }





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
                modifier = m
                    .fillMaxSize(),
                //                .verticalScroll(scrollState)
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Card(
                    modifier = m
                        .padding(horizontal = 14.dp)
                        .padding(vertical = 15.dp)
                        .clip(RoundedCornerShape(16.dp)), // 👈 important

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
                            "Welcome!",
                            fontFamily = popSemi,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(30, 30, 30, 168)

                        )  //welcome
                        Text(
                            "Drive with confidence.Enroll in smarter",
                            fontFamily = popSemi,
                            fontSize = 12.sp,
                            color = Color(30, 30, 30, 168)

                        )  // 2ndline
                        Text(
                            " CarWare today .",
                            fontFamily = popSemi,
                            fontSize = 12.sp,
                            color = Color(30, 30, 30, 168)

                        )  // 3rd line
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
                                        "First Name",
                                        fontFamily = popMid,
                                        fontSize = 12.sp,
                                        color = Color(30, 30, 30, 168)
                                    )
                                },
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
                            ) //Fname field
                            Spacer(modifier = m.padding(horizontal = 12.dp))
                            OutlinedTextField(
                                modifier = m.size(130.dp, 55.dp),
                                value = lName,
                                onValueChange = { lName = it },
                                placeholder = {
                                    Text(
                                        "Last Name",
                                        fontFamily = popMid,
                                        fontSize = 12.sp,
                                        color = Color(30, 30, 30, 168)
                                    )
                                },
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
                                    "User Name",
                                    fontFamily = popMid,
                                    fontSize = 12.sp,
                                    color = Color(30, 30, 30, 168)

                                )
                            },
                            isError = userNameError,

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
                                    "Email",
                                    fontFamily = popMid,
                                    fontSize = 12.sp,
                                    color = Color(30, 30, 30, 168)

                                )
                            },
                            isError = emailError,
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
                                cursorColor = Color.Black,
                            ),


                            ) //email field
                        Spacer(modifier = m.padding(vertical = 8.dp))
                        OutlinedTextField(

                            modifier = m.size(280.dp, 55.dp),
                            value = num,
                            onValueChange = { num = it },
                            placeholder = {
                                Text(
                                    "Phone number",
                                    fontFamily = popMid,
                                    fontSize = 12.sp,
                                    color = Color(30, 30, 30, 168)

                                )
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Phone
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


                        )//number field
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


                        ) //password field
                        Text(
                            "minimum 8 characters,letters and numbers       ",
                            fontFamily = popMid,
                            fontSize = 10.sp,
                            color = Color(30, 30, 30, 168)

                        )  // min pass text
                        Spacer(modifier = m.padding(vertical = 4.dp))
                        OutlinedTextField(

                            modifier = m.size(280.dp, 55.dp),
                            value = confPass,
                            onValueChange = { confPass = it },
                            placeholder = {
                                Text(
                                    " Confirm Password",
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
                                    unselectedColor = Color.Gray
                                )
                            )
                            Text(
                                "I agree to terms and conditions ",
                                fontFamily = popMid,
                                fontSize = 12.sp,
                                color = Color(30, 30, 30, 168)

                            )
                        } //terms &conditions

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
                                    "Sign Up",
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


                        } //google button
                        Spacer(modifier = m.padding(vertical = 8.dp))
                        Row() {
                            Text(
                                "Already have an account?", fontFamily = popMid,
                                fontSize = 12.sp,
                                color = Color(30, 30, 30, 168)
                            )
                            Text(
                                " Log in", fontFamily = popMid,
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


}



@Composable
fun SignUpDemo() {

    var fName by remember { mutableStateOf("") }
    var lName by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var num by remember { mutableStateOf("") }

    var confPass by remember { mutableStateOf("") }
    var userNameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passError by remember { mutableStateOf(false) }
    var numError by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    var agreed by remember { mutableStateOf(false) }





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
                modifier = m
                    .fillMaxSize(),
                //                .verticalScroll(scrollState)
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Card(
                    modifier = m
                        .padding(horizontal = 14.dp)
                        .padding(vertical = 15.dp)
                        .clip(RoundedCornerShape(16.dp)), // 👈 important

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
                            "Welcome!",
                            fontFamily = popSemi,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(30, 30, 30, 168)

                        )  //welcome
                        Text(
                            "Drive with confidence.Enroll in smarter",
                            fontFamily = popSemi,
                            fontSize = 12.sp,
                            color = Color(30, 30, 30, 168)

                        )  // 2ndline
                        Text(
                            " CarWare today .",
                            fontFamily = popSemi,
                            fontSize = 12.sp,
                            color = Color(30, 30, 30, 168)

                        )  // 3rd line
                        Spacer(modifier = m.padding(vertical = 10.dp))

                        Row(
                            m
                                .fillMaxWidth()
                                .size(280.dp, 50.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            OutlinedTextField(
                                modifier = m.size(125.dp, 50.dp),
                                value = fName,
                                onValueChange = { fName = it },
                                placeholder = {
                                    Text(
                                        "First Name",
                                        fontFamily = popMid,
                                        fontSize = 12.sp,
                                        color = Color(30, 30, 30, 168)
                                    )
                                },
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
                            ) //Fname field
                            Spacer(modifier = m.padding(horizontal = 12.dp))

                            OutlinedTextField(
                                modifier = m.size(130.dp, 50.dp),
                                value = lName,
                                onValueChange = { lName = it },
                                placeholder = {
                                    Text(
                                        "Last Name",
                                        fontFamily = popMid,
                                        fontSize = 12.sp,
                                        color = Color(30, 30, 30, 168)
                                    )
                                },
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
                            ) //Lname field

                        } // fname &lname

                        Spacer(modifier = m.padding(vertical = 8.dp))
                        OutlinedTextField(
                            modifier = m.size(280.dp, 50.dp),
                            value = userName,
                            onValueChange = {
                                userName = it
                                userNameError = false
                            },
                            placeholder = {
                                Text(
                                    "User Name",
                                    fontFamily = popMid,
                                    fontSize = 12.sp,
                                    color = Color(30, 30, 30, 168)

                                )
                            },
                            isError = userNameError,

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

                            ) //user name field
                        Spacer(modifier = m.padding(vertical = 8.dp))
                        OutlinedTextField(
                            modifier = m.size(280.dp, 50.dp),
                            value = email,
                            onValueChange = {
                                email = it
                                emailError = false
                            },
                            placeholder = {
                                Text(
                                    "Email",
                                    fontFamily = popMid,
                                    fontSize = 12.sp,
                                    color = Color(30, 30, 30, 168)

                                )
                            },
                            isError = emailError,
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
                            ),

                            ) //email field
                        Spacer(modifier = m.padding(vertical = 8.dp))
                        OutlinedTextField(

                            modifier = m.size(280.dp, 50.dp),
                            value = num,
                            onValueChange = { num = it },
                            placeholder = {
                                Text(
                                    "Phone number",
                                    fontFamily = popMid,
                                    fontSize = 12.sp,
                                    color = Color(30, 30, 30, 168)

                                )
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Phone
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


                        )//number field
                        Spacer(modifier = m.padding(vertical = 8.dp))
                        OutlinedTextField(

                            modifier = m.size(280.dp, 50.dp),
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


                        ) //password field
                        Text(
                            "minimum 8 characters,letters and numbers       ",
                            fontFamily = popMid,
                            fontSize = 10.sp,
                            color = Color(30, 30, 30, 168)

                        )  // min pass text
                        Spacer(modifier = m.padding(vertical = 4.dp))
                        OutlinedTextField(

                            modifier = m.size(280.dp, 50.dp),
                            value = confPass,
                            onValueChange = { confPass = it },
                            placeholder = {
                                Text(
                                    " Confirm Password",
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
                                    unselectedColor = Color.Gray
                                )
                            )
                            Text(
                                "I agree to terms and conditions ",
                                fontFamily = popMid,
                                fontSize = 12.sp,
                                color = Color(30, 30, 30, 168)

                            )
                        } //terms &conditions

                        Spacer(modifier = m.padding(vertical = 8.dp))

                        Card(
                            onClick = { /* handle click */ },
                            modifier = m
                                .size(width = 280.dp, height = 55.dp)
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
                                    "Sign Up",
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
                                .size(width = 280.dp, height = 55.dp)
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


                        } //google button
                        Spacer(modifier = m.padding(vertical = 8.dp))
                        Row() {
                            Text(
                                "Already have an account?", fontFamily = popMid,
                                fontSize = 12.sp,
                                color = Color(30, 30, 30, 168)
                            )
                            Text(
                                " Log in", fontFamily = popMid,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(194, 0, 0, 255),
                                modifier = m.clickable { }
                            )
                        }
                        Spacer(Modifier.padding(vertical = 8.dp))


                    }


                }

            }


        }
    }


}

@Preview
@Composable
private fun orev() {
    SignUpDemo()

}