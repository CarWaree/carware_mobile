package com.example.carware.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import carware.composeapp.generated.resources.Res
import carware.composeapp.generated.resources.poppins_medium
import carware.composeapp.generated.resources.poppins_semibold
import com.example.carware.util.lang.AppLanguage
import com.example.carware.util.lang.LocalizedStrings
import org.jetbrains.compose.resources.ExperimentalResourceApi


@Composable
fun TestScreen(navController: NavController){
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

    @Composable
    fun OtpCodeInput(
        modifier: Modifier = Modifier,
        length: Int = 6,
        lang: AppLanguage = AppLanguage.AR
    ) {
        val strings = LocalizedStrings(lang)
        val popSemi = FontFamily(
            org.jetbrains.compose.resources.Font(Res.font.poppins_semibold) // name of your font file without extension
        )

        val popMid = FontFamily(org.jetbrains.compose.resources.Font(Res.font.poppins_medium))

        val otpState = rememberTextFieldState()

        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = strings.get("ENTER_OTP"),
                fontFamily = popSemi,
                fontSize = 20.sp,
                color = Color(30, 30, 30, 168)
            )
            val otpCode = otpState.text.toString()

            Spacer(modifier = Modifier.padding(top = 16.dp))

            BasicTextField(
                state = otpState,
                modifier = Modifier.semantics {
                    contentType = ContentType.SmsOtpCode
                },
                inputTransformation = InputTransformation.maxLength(length),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                lineLimits = TextFieldLineLimits.SingleLine,
                decorator = {
                    val otp = otpState.text.toString()

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(6) { index ->
                            Digit(
                                char = otpCode.getOrElse(index) { ' ' },
                                highlight = index == otpState.text.length
                            )
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.padding(top = 12.dp))

            Text(
                text = strings.get("RESEND_CODE"),
                fontFamily = popMid,
                fontSize = 14.sp,
                color = Color(194, 0, 0, 255),
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        // resend otp
                    }
            )
        }
    }
    @Composable
    fun OtpDigit(
        char: Char,
        isFocused: Boolean
    ) {
        val borderColor by animateColorAsState(
            if (isFocused) Color(194, 0, 0, 255)
            else Color(118, 118, 118, 255)
        )

        val borderWidth by animateDpAsState(
            if (isFocused) 2.dp else 1.dp
        )

        Box(
            modifier = Modifier
                .size(44.dp)
                .border(
                    width = borderWidth,
                    color = borderColor,
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = char.toString(),
                fontFamily = FontFamily.Default,
                fontSize = 20.sp,
                color = Color(30, 30, 30, 255)
            )
        }
    }

    Column() {
        Modifier.fillMaxSize()
        OtpCodeInput()
    }


}
