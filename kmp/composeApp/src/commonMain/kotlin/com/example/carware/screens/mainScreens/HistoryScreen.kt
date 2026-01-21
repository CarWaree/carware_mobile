package com.example.carware.screens.mainScreens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.carware.LocalStrings
import com.example.carware.navigation.HistoryScreen
import com.example.carware.util.lang.AppLanguage
import com.example.carware.util.storage.PreferencesManager

@Composable
fun HistoryScreen(navController: NavController
, preferencesManager: PreferencesManager){
    val strings = LocalStrings.current
    val currentLang = AppLanguage.fromCode(preferencesManager.getLanguageCode())
    Text(strings.get("HISTORY"), color = Color(16, 0, 0, 255))
}