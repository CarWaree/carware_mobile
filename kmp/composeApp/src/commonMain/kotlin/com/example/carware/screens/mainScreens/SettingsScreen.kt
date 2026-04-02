package com.example.carware.screens.mainScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import carware.composeapp.generated.resources.Res
import carware.composeapp.generated.resources.clander_right_arrow
import carware.composeapp.generated.resources.poppins_medium
import carware.composeapp.generated.resources.poppins_semibold
import carware.composeapp.generated.resources.settings_profile
import com.example.carware.LocalStrings
import com.example.carware.m
import com.example.carware.navigation.ProfileScreen
import com.example.carware.util.lang.AppLanguage
import com.example.carware.util.storage.PreferencesManager
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun SettingsScreen(
    navController: NavController, preferencesManager: PreferencesManager,
    onLangChange: (AppLanguage) -> Unit
) {

    val strings = LocalStrings.current
    val currentLang = AppLanguage.fromCode(preferencesManager.getLanguageCode())
    val popMid = FontFamily(Font(Res.font.poppins_medium))

    Column(
        m
            .background(Color(217, 217, 217, 255))
            .fillMaxSize()
            .padding(vertical = 32.dp, horizontal = 26.dp),
    ) {
        Box(m.fillMaxWidth(),
            contentAlignment = Alignment.Center) {
            Text(
                text = "Settings",
                fontFamily = popMid,
                fontSize = 26.sp,
                fontWeight = FontWeight.W500,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        listOf(Color(194, 0, 0, 255), Color(92, 0, 0, 255))
                    )
                ),
            ) }
        Spacer(m.height(8.dp))
        HorizontalDivider(
            color = Color(102, 102, 102, 51),
            thickness = 1.dp
        )
        Spacer(m.height(18.dp))
        Text(
            text = "Account",
            fontFamily = popMid,
            fontSize = 18.sp,
            fontWeight = FontWeight.W500,
            style = TextStyle(
                brush = Brush.linearGradient(
                    listOf(Color(194, 0, 0, 255), Color(92, 0, 0, 255))
                )
            ),)
        Spacer(m.height(2.dp))

        SettingsRowButton(
            icon = painterResource(Res.drawable.settings_profile),
            label = "Edit Profile",
            onClick ={navController.navigate(ProfileScreen) },
        )
    }

}
@Composable
fun SettingsRowButton(
    icon: Painter,
    label: String,
    onClick: () -> Unit = {},
    trailingContent: @Composable () -> Unit = {
        Icon(
            painter = painterResource(Res.drawable.clander_right_arrow),
            contentDescription = null,
            tint = Color.Red,
            modifier = m.size(16.dp)
        )
    }
) {
    val popSemi = FontFamily(Font(Res.font.poppins_semibold))

    Row(
        modifier = m
            .height(62.dp)
            .clip(RoundedCornerShape(size = 8.dp))
            .background(Color(204, 204, 204, 191))
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = m.size(30.dp)
        )
        Spacer(m.padding(horizontal = 2.dp))
        Text(
            label,
            fontFamily = popSemi,
            fontSize = 18.sp,
            fontWeight = FontWeight.W400,
            color = Color(118, 118, 118, 191),
        )
        Spacer(m.weight(1f))  // ← cleaner than fillMaxWidth(0.9f)

        trailingContent()
    }
}


//Row(verticalAlignment = Alignment.CenterVertically) {
//    Text("العربية")
//    Switch(
//        checked = currentLang == AppLanguage.AR,
//        onCheckedChange = { isAr ->
//            val selected = if (isAr) AppLanguage.AR else AppLanguage.EN
//            onLangChange(selected)
//        }
//    )
//}