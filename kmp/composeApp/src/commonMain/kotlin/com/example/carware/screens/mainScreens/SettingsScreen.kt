package com.example.carware.screens.mainScreens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import carware.composeapp.generated.resources.settings_clock
import carware.composeapp.generated.resources.settings_lang
import carware.composeapp.generated.resources.settings_lock
import carware.composeapp.generated.resources.settings_logout
import carware.composeapp.generated.resources.settings_profile
import carware.composeapp.generated.resources.settings_update
import com.example.carware.LocalStrings
import com.example.carware.m
import com.example.carware.navigation.ProfileScreen
import com.example.carware.navigation.SelectLanguageScreen
import com.example.carware.util.storage.PreferencesManager
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun SettingsScreen(
    navController: NavController,
    preferencesManager: PreferencesManager
) {
    val strings = LocalStrings.current
    val popMid = FontFamily(Font(Res.font.poppins_medium))
    val scrollState = rememberScrollState()
    Column(
        m
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color(217, 217, 217, 255))
            .padding(vertical = 42.dp),
    ) {
        Box(
            m.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = strings.get("SETTINGS"),
                fontFamily = popMid,
                fontSize = 26.sp,
                fontWeight = FontWeight.W500,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        listOf(Color(194, 0, 0, 255), Color(92, 0, 0, 255))
                    )
                ),
            )
        }
        Spacer(m.height(8.dp))
        HorizontalDivider(
            color = Color(102, 102, 102, 51),
            thickness = 1.dp,
            modifier = m
                .fillMaxWidth(1f)

        )
        Column(
            m
                .fillMaxWidth()
                .padding(horizontal = 26.dp)
        ) {

            Spacer(m.height(18.dp))
            Text(
                text = strings.get("ACCOUNT"),
                fontFamily = popMid,
                fontSize = 18.sp,
                fontWeight = FontWeight.W500,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        listOf(Color(194, 0, 0, 255), Color(92, 0, 0, 255))
                    )
                ),
            )
            Spacer(m.height(2.dp))

            SettingsRowButton(
                icon = painterResource(Res.drawable.settings_profile),
                label = strings.get("EDIT_PROFILE"),
                onClick = { navController.navigate(ProfileScreen) },
            )
            Spacer(m.height(28.dp))

            SettingsRowButton(
                painterResource(Res.drawable.settings_lock),
                strings.get("CHANGE_PASSWORD"),
                {}
            )
            Spacer(m.height(42.dp))

            Text(
                text = strings.get("NOTIFICATIONS"),
                fontFamily = popMid,
                fontSize = 18.sp,
                fontWeight = FontWeight.W500,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        listOf(Color(194, 0, 0, 255), Color(92, 0, 0, 255))
                    )
                ),
            )
            Spacer(m.height(2.dp))
            var isReminderEnabled = true
            SettingsRowButton(
                painterResource(Res.drawable.settings_clock),
                strings.get("SERVICE_REMINDER"),
                onClick = { isReminderEnabled = !isReminderEnabled }  // or leave it empty {}


            ) {
                Switch(
                    checked = isReminderEnabled,
                    onCheckedChange = { isReminderEnabled = it },

                    thumbContent = {
                        Spacer(
                            modifier = Modifier
                                .size(16.dp) // Set your fixed size here
                                .background(
                                    // We manually color it to match your Green/Red logic
                                    color = if (isReminderEnabled) Color(0xFF008000) else Color(0xFFB00000),
                                    shape = CircleShape
                                )
                        )
                    },
                    colors = SwitchDefaults.colors(
                        // Set thumb colors to Transparent so they don't overlap your custom thumb
                        checkedThumbColor = Color.Transparent,
                        uncheckedThumbColor = Color.Transparent,
                        checkedTrackColor = Color(0xFFE0E0E0),
                        uncheckedTrackColor = Color(0xFFE0E0E0),
                        uncheckedBorderColor = Color.Transparent
                    )
                )
            }
            Spacer(m.height(22.dp))
            var isUpdateEnabled = true
            SettingsRowButton(
                painterResource(Res.drawable.settings_update),
                strings.get("APP_UPDATE"),
                onClick = { isUpdateEnabled = !isUpdateEnabled }  // or leave it empty {}


            ) {
                Switch(
                    checked = isUpdateEnabled,
                    onCheckedChange = { isUpdateEnabled = it },

                    thumbContent = {
                        Spacer(
                            modifier = Modifier
                                .size(16.dp) // Set your fixed size here
                                .background(
                                    // We manually color it to match your Green/Red logic
                                    color = if (isUpdateEnabled) Color(0xFF008000) else Color(0xFFB00000),
                                    shape = CircleShape
                                )
                        )
                    },
                    colors = SwitchDefaults.colors(
                        // Set thumb colors to Transparent so they don't overlap your custom thumb
                        checkedThumbColor = Color.Transparent,
                        uncheckedThumbColor = Color.Transparent,
                        checkedTrackColor = Color(0xFFE0E0E0),
                        uncheckedTrackColor = Color(0xFFE0E0E0),
                        uncheckedBorderColor = Color.Transparent
                    )
                )
            }
            Spacer(m.height(42.dp))
            Text(
                text = strings.get("PREFERENCES"),
                fontFamily = popMid,
                fontSize = 18.sp,
                fontWeight = FontWeight.W500,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        listOf(Color(194, 0, 0, 255), Color(92, 0, 0, 255))
                    )
                ),
            )
            Spacer(m.height(2.dp))
            SettingsRowButton(
                painterResource(Res.drawable.settings_lang),
                strings.get("CHANGE_LANGUAGE"),
                onClick = {navController.navigate(SelectLanguageScreen) }
            )
            Spacer(m.height(22.dp))
            Box(
                m.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = strings.get("HELP_SUPPORT"),
                    fontFamily = popMid,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W400,
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            listOf(Color(194, 0, 0, 255), Color(92, 0, 0, 255))
                        )
                    ),
                )
            }
            Spacer(m.height(32.dp))

            Card(
                onClick = { preferencesManager.performLogout() },
                modifier = m
                    .size(width = 240.dp, height = 55.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(shape = RoundedCornerShape(8.dp)),
//                .appButtonBack(),
                colors = CardDefaults.cardColors(containerColor = Color(194, 0, 0, 128)),


                ) {

                Row(
                    modifier = m.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Icon(
                        painter = painterResource(Res.drawable.settings_logout),
                        contentDescription = null,
                        tint = Color.Unspecified, // need to be gradient
                        modifier = m.size(22.dp)
                    )
                    Spacer(m.width(8.dp))
                    Text(
                        strings.get("LOG_OUT"),
                        fontFamily = popMid,
                        fontWeight = FontWeight.W500,
                        fontSize = 21.sp,
                        color = Color(255, 255, 255, 255)
                    )
                }
            }
            Spacer(m.height(100.dp))
        }
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
            tint = Color.Red, // need to be gradient
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
            fontSize = 20.sp,
            fontWeight = FontWeight.W400,
            color = Color(118, 118, 118, 191),
        )
        Spacer(m.weight(1f))  // ← cleaner than fillMaxWidth(0.9f)

        trailingContent()
    }
}
