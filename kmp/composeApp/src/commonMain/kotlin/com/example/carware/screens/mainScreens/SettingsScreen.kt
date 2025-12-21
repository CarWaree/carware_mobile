package com.example.carware.screens.mainScreens

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.example.carware.LocalStrings
import com.example.carware.util.lang.AppLanguage
import com.example.carware.util.storage.PreferencesManager

@Composable
fun SettingsScreen(navController: NavController
                    ,preferencesManager: PreferencesManager,
                   onLangChange: (AppLanguage) -> Unit ){

    val strings = LocalStrings.current
    val currentLang = AppLanguage.fromCode(preferencesManager.getLanguageCode())
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("العربية")
        Switch(
            checked = currentLang == AppLanguage.AR,
            onCheckedChange = { isAr ->
                val selected = if (isAr) AppLanguage.AR else AppLanguage.EN
                onLangChange(selected)
            }
        )
    }
}