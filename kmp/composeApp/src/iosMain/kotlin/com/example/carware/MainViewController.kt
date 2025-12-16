package com.example.carware

import IOSUserSession
import androidx.compose.ui.window.ComposeUIViewController

val iosSession = IOSUserSession()


val iosLoginManager = LoginManager(iosSession)


fun MainViewController() = ComposeUIViewController { MainScreen(iosLoginManager) }