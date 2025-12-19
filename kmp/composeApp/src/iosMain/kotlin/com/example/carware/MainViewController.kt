package com.example.carware

import androidx.compose.ui.window.ComposeUIViewController
import com.example.carware.util.storage.PreferencesManager
import com.russhwolf.settings.AppleSettings
import platform.Foundation.NSUserDefaults

private val iosPreferences: PreferencesManager by lazy {
    val settings = AppleSettings(NSUserDefaults.standardUserDefaults)
    PreferencesManager(settings)
}

fun MainViewController() =
    ComposeUIViewController {
        MainScreen(iosPreferences)
    }
