package com.example.carware

import androidx.compose.ui.window.ComposeUIViewController
import com.example.carware.di.appModule
import com.example.carware.util.storage.PreferencesManager
import com.russhwolf.settings.Settings
import org.koin.core.context.startKoin
import platform.UIKit.UIViewController

// MainViewController.kt
fun MainViewController(): UIViewController {
    val preferencesManager = PreferencesManager(Settings())

    startKoin {
        modules(appModule(preferencesManager))
    }

    return ComposeUIViewController {
        MainScreen()
    }
}