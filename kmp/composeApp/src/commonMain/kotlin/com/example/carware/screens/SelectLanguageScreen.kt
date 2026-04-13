package com.example.carware.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import carware.composeapp.generated.resources.Res
import carware.composeapp.generated.resources.ar_flag
import carware.composeapp.generated.resources.arrow_left
import carware.composeapp.generated.resources.check_onboard
import carware.composeapp.generated.resources.en_flag
import carware.composeapp.generated.resources.poppins
import com.example.carware.LocalStrings
import com.example.carware.m
import com.example.carware.navigation.LanguageSelectionScreen
import com.example.carware.navigation.OnboardingScreen
import com.example.carware.util.lang.AppLanguage
import com.example.carware.util.storage.PreferencesManager
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun SelectLanguageScreen(
    navController: NavController,
    preferencesManager: PreferencesManager,
    onLangChange: (AppLanguage) -> Unit
) {

    val primaryGradientBrush = Brush.linearGradient(
        listOf(Color(194, 0, 0, 255), Color(92, 0, 0, 255))
    )

    val strings = LocalStrings.current
    val pop = FontFamily(Font(Res.font.poppins))
    val currentLang = AppLanguage.fromCode(preferencesManager.getLanguageCode())
    Column(
        m
            .fillMaxSize()
            .backgroundColor()
            .padding(top = 32.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(Res.drawable.arrow_left),
                contentDescription = "Back",
                modifier = Modifier
                    .size(28.dp)
                    .clickable { navController.popBackStack() }
                    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                    .drawWithContent {
                        drawContent()
                        drawRect(primaryGradientBrush, blendMode = BlendMode.SrcIn)
                    },
                tint = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = strings.get("HEADER_TITLE"),
                fontFamily = pop,
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium,
                style = TextStyle(brush = primaryGradientBrush)
            )
            Spacer(modifier = Modifier.weight(1.2f))
        }

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            thickness = 0.8.dp,
            color = Color(0x33666666)
        )

        Spacer(modifier = Modifier.height(16.dp))
    Column(m.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

        Text(
            text = strings.get("CHOOSE_LANG"),
            fontFamily = pop,
            fontSize = 30.sp,
            fontWeight = FontWeight.W600    ,
            style = TextStyle(brush = primaryGradientBrush)
        )
        Text(
            text = strings.get("LANGUAGE_SELECTION_SUBTITLE"),
            fontFamily = pop,
            fontSize = 17.sp,
            fontWeight = FontWeight.W400    ,
            color = Color(30, 30, 30, 153)

        )
        Spacer(modifier = Modifier.height(36.dp))

        Card(
            onClick = {
                onLangChange(AppLanguage.EN)
            },
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(2.dp, Color(204, 204, 204, 191)),
            colors = CardDefaults.cardColors(
                containerColor = Color(204, 204, 204, 191),
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
                    fontFamily = pop,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W500,
                    color = Color(118, 118, 118, 191)
                )
                Spacer(modifier = Modifier.weight(1f)) // pushes content below to bottom


                Box(m.size(20.dp) // Set the size of the circle
                    .clip(CircleShape) // This makes it a circle
                    .border(1.dp, Color(0, 0, 0, 255)
                        , CircleShape))
                {

                    Icon(painter = painterResource(Res.drawable.check_onboard),
                        null,
                        tint = if (currentLang == AppLanguage.EN)
                            Color(32, 144, 0, 255)
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
            border = BorderStroke(2.dp, Color(204, 204, 204, 191)),
            colors = CardDefaults.cardColors(
                containerColor = Color(204, 204, 204, 191),
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
                    fontFamily = pop,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W500,
                    color = Color(118, 118, 118, 191)
                )
                Spacer(modifier = Modifier.weight(1f)) // pushes content below to bottom


                Box(m.size(20.dp) // Set the size of the circle
                    .clip(CircleShape) // This makes it a circle
                    .border(1.dp, Color(0, 0, 0, 255)
                        , CircleShape))
                {

                    Icon(painter = painterResource(Res.drawable.check_onboard),
                        null,
                        tint = if (currentLang == AppLanguage.AR)
                            Color(32, 144, 0, 255)
                        else Color.Transparent,

                        )


                }
            }


        } //Arabic button
        Spacer(modifier = m.padding(vertical = 48.dp))

        Card(
            onClick = {
                preferencesManager.setLanguageSelected(true)
                navController.popBackStack()
                      },
            modifier = m
                .size(300.dp, 55.dp)
                .border(
                    width = 1.dp,
                    color = Color(135, 135, 135, 255),
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(shape = RoundedCornerShape(8.dp))
                .appGradBack(),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),

            ) {

            Row(
                modifier = m.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    strings.get("SAVE_CHANGES"),
                    fontFamily = pop,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500,
                    color = Color(217, 217, 217, 255)
                )
            }
        } // save changes
        Spacer(modifier = m.padding(vertical = 18.dp))
        Text(
            text = "Discard Changes",
            fontFamily = pop,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            style = TextStyle(brush = primaryGradientBrush),
            modifier = Modifier.clickable {
                navController.popBackStack()
            }
        ) // discard changes



    }
    }

}


//Row(m.fillMaxSize(),
//verticalAlignment = Alignment.CenterVertically) {
//    Text("العربية")
//    Switch(
//        checked = currentLang == AppLanguage.AR,
//        onCheckedChange = { isAr ->
//            val selected = if (isAr) AppLanguage.AR else AppLanguage.EN
//            onLangChange(selected)
//        }
//    )
//}