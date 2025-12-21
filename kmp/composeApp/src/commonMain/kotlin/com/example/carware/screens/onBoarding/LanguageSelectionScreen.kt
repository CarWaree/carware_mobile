package com.example.carware.screens.onBoarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import carware.composeapp.generated.resources.Res
import carware.composeapp.generated.resources.ar_flag
import carware.composeapp.generated.resources.carware
import carware.composeapp.generated.resources.check_onboard
import carware.composeapp.generated.resources.en_flag
import carware.composeapp.generated.resources.line_1
import carware.composeapp.generated.resources.poppins_semibold
import com.example.carware.LocalStrings
import com.example.carware.m
import com.example.carware.navigation.LanguageSelectionScreen
import com.example.carware.navigation.OnboardingScreen
import com.example.carware.screens.appGradBack
import com.example.carware.util.lang.AppLanguage
import com.example.carware.util.storage.PreferencesManager
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource


@Composable
fun LanguageSelectionScreen(
    navController: NavController,
    preferencesManager: PreferencesManager,
    onLangChange: (AppLanguage) -> Unit
) {
    val strings = LocalStrings.current
    val currentLang = AppLanguage.fromCode(preferencesManager.getLanguageCode())

    val popSemi = FontFamily(Font(Res.font.poppins_semibold))

    Column(
        modifier = m
            .fillMaxSize()
            .appGradBack()
            .padding(top = 60.dp, bottom = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Icon(
            painter = painterResource(Res.drawable.line_1),
            contentDescription = null,
            tint = Color(211, 203, 203, 255)
        )
        Spacer(modifier = m.padding(vertical = 4.dp))
        Icon(
            painter = painterResource(Res.drawable.carware),
            contentDescription = null,
            tint = Color(211, 203, 203, 255)
        )
        Column(
            m.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(0.6f)) // pushes content below to bottom

            Text(
                strings.get("CHOOSE_LANG"),
                color = Color(245, 245, 245, 255),
                fontSize = 26.sp,
                fontFamily = popSemi,
                fontWeight = FontWeight.W700,
                textAlign = TextAlign.Center,

                )
            Text(
                strings.get("LANGUAGE_SELECTION_SUBTITLE"),
                color = Color(245, 245, 245, 255),
                fontSize = 16.sp,
                fontFamily = popSemi,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Center,

                )
            Spacer(modifier = m.padding(vertical = 12.dp))
            Card(
                onClick = {
                    onLangChange(AppLanguage.EN)
                },
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(2.dp, Color(217, 217, 217, 75)),
                colors = CardDefaults.cardColors(
                    containerColor = Color(217, 217, 217, 75),
                ),
                modifier = m.size(300.dp, 55.dp)
            ) {

                Row(
                    modifier = m
                        .padding(horizontal = 20.dp)
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(painter = painterResource(Res.drawable.en_flag)
                        ,null,
                        tint = Color.Unspecified,
                    )
                    Spacer(modifier = m.padding(horizontal = 8.dp))
                    Text(
                        strings.get("ENGLISH"),
                        fontFamily = popSemi,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W500,
                        color = Color(245, 245, 245, 255)
                    )
                    Spacer(modifier = Modifier.weight(1f)) // pushes content below to bottom


                    Box(m.size(20.dp) // Set the size of the circle
                        .clip(CircleShape) // This makes it a circle
                        .border(1.dp, Color(217, 217, 217, 255)
                            , CircleShape))
                    {

                        Icon(painter = painterResource(Res.drawable.check_onboard),
                            null,
                            tint = if (currentLang == AppLanguage.EN)
                                Color(217, 217, 217, 75)
                            else Color.Transparent,

                        )


                    }
                }
            } //English button
            Spacer(modifier = m.padding(vertical = 12.dp))
            Card(
                onClick = {
                    onLangChange(AppLanguage.AR)

                },
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(2.dp, Color(217, 217, 217, 75)),
                colors = CardDefaults.cardColors(
                    containerColor = Color(217, 217, 217, 75),
                ),
                modifier = m.size(300.dp, 55.dp)
            ) {

                Row(
                    modifier = m
                        .padding(horizontal = 20.dp)
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(painter = painterResource(Res.drawable.ar_flag)
                        ,null,
                        tint = Color.Unspecified,
                    )
                    Spacer(modifier = m.padding(horizontal = 8.dp))
                    Text(
                        strings.get("ARABIC"),
                        fontFamily = popSemi,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W500,
                        color = Color(245, 245, 245, 255)
                    )
                    Spacer(modifier = Modifier.weight(1f)) // pushes content below to bottom


                    Box(m.size(20.dp) // Set the size of the circle
                        .clip(CircleShape) // This makes it a circle
                        .border(1.dp, Color(217, 217, 217, 255)
                            , CircleShape))
                    {

                        Icon(painter = painterResource(Res.drawable.check_onboard),
                            null,
                            tint = if (currentLang == AppLanguage.AR)
                                Color(217, 217, 217, 75)
                            else Color.Transparent,

                            )


                    }
                }


            } //Arabic button

            Spacer(modifier = Modifier.weight(1f))

            Card(
                onClick = {
                    // 1. Mark that the language selection is done
                    // This ensures the next time they open the app, it doesn't show this screen
                    preferencesManager.setLanguageSelected(true)

                    // 2. Navigate to the Onboarding Slides (the Pager)
                    navController.navigate(OnboardingScreen) {
                        // This removes the Language screen from the "Back" history
                        popUpTo(LanguageSelectionScreen) { inclusive = true }
                    }
                },
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(2.dp, Color(30, 30, 30, 110)),
                colors = CardDefaults.cardColors(
                    containerColor = Color(217, 217, 217, 255)
                ),
                modifier = m
                    .size(320.dp, 50.dp)

            ) {

                Row(
                    modifier = m.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(
                        strings.get("CONTINUE"),
                        fontFamily = popSemi,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(55, 71, 79, 255)
                    )
                }
            } //cont button
            Text(
                strings.get("CHANGE_LANG"),
                color = Color(245, 245, 245, 255),
                fontSize = 15.sp,
                fontFamily = popSemi,
                fontWeight = FontWeight.W300,
                textAlign = TextAlign.Center,

                )


        }

    }
}