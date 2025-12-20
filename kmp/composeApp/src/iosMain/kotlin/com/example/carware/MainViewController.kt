package com.example.carware

import androidx.compose.ui.window.ComposeUIViewController
import com.example.carware.util.storage.PreferencesManager
import com.russhwolf.settings.KeychainSettings
import com.russhwolf.settings.Settings
import platform.Foundation.NSUserDefaults

val preferencesManager = PreferencesManager(Settings())

fun MainViewController() = ComposeUIViewController {
    MainScreen(preferencesManager = preferencesManager)
}