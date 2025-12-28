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
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.carware.m
import com.example.carware.util.lang.AppLanguage
import com.example.carware.util.lang.LocalizedStrings
import com.example.carware.util.storage.PreferencesManager
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font



@Composable
fun Digit(
    char: Char,
    highlight: Boolean = false,
    isError: Boolean = false // New parameter to match your first fun
) {
    // 1. Define your specific colors from the first snippet
    val errorRed = Color(194, 0, 0, 255)
    val focusedGray = Color(118, 118, 118, 255)
    val unfocusedGray = Color(30, 30, 30, 75)

    val borderSize by animateDpAsState(
        targetValue = if (highlight || isError) 2.dp else 1.dp
    )

    // 2. Logic: Error color takes priority, then Highlight, then Default
    val targetColor = when {
        isError -> errorRed
        highlight -> focusedGray
        else -> unfocusedGray
    }

    val borderColor by animateColorAsState(targetValue = targetColor)

    Box(
        modifier = Modifier
            .size(40.dp) // Size from your original modifier: m.size(280.dp, 50.dp) divided by 6
            .border(borderSize, borderColor, RoundedCornerShape(8.dp))
            .background(Color(217, 217, 217, 255), RoundedCornerShape(8.dp))
    ) {
        Text(
            text = char.toString(),
            fontFamily = FontFamily(Font(Res.font.poppins_semibold)), // Using popSemi from your fun
            fontSize = 20.sp,
            color = if (isError) errorRed else Color.Black,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
@Composable
fun OtpCodeInput(length: Int = 6,preferencesManager: PreferencesManager) {
    val strings = LocalizedStrings(preferencesManager)
    val popMid = FontFamily(Font(Res.font.poppins_medium))

    // 1. State for the OTP and Error tracking
    val otpState = rememberTextFieldState()
    var isErrorOTP by remember { mutableStateOf(false) }

    // 2. Define your colors logic
    val errorColor = Color(194, 0, 0, 255)
    val normalBorderColor = Color(118, 118, 118, 255)
    val placeholderColor = Color(30, 30, 30, 168)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val otpCode = otpState.text.toString()

        // Trigger error logic if needed (e.g., if code is too short when submitting)
        // For now, mirroring your first fun's logic

        BasicTextField(
            state = otpState,
            // replaces if (it.length <= 6)
            inputTransformation = InputTransformation.maxLength(length),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
            lineLimits = TextFieldLineLimits.SingleLine,
            decorator = {
                Row(
                    m.fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(length) { index ->
                        val char = otpCode.getOrNull(index)

                        // 3. Modifying the Digit to accept your color logic
                        Digit(
                            char = char ?: ' ',
                            isError = isErrorOTP,
                            // Highlight if it's the current active box
                            highlight = index == otpCode.length,
                        )
                    }
                }
                // Hidden field that handles the actual typing
            }
        )


        // 4. Placeholder/Error Text below or as a label
//        Text(
//            text = if (isErrorOTP) strings.get("OTP_REQUIRED") else strings.get("OTP"),
//            fontFamily = popMid,
//            fontSize = 12.sp,
//            color = if (isErrorOTP) errorColor else placeholderColor
//        )

//        Spacer(modifier = Modifier.padding(top = 12.dp))

//        Text(
//            text = strings.get("RESEND_CODE"),
//            fontFamily = popMid,
//            fontSize = 14.sp,
//            color = errorColor,
//            modifier = Modifier
//                .align(Alignment.End)
//                .clickable { /* resend otp */ }
//        )
    }
}

@Composable
fun TestScreen(navController: NavController, preferencesManager: PreferencesManager) {



    Column(
        modifier = Modifier
            .background(Color(217, 217, 217, 255))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OtpCodeInput(6,preferencesManager)
    }
}