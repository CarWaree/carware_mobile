package com.example.carware

import android.os.Bundle
import androidAppContext
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        androidAppContext = this.applicationContext

        setContent {
            MainScreen(preferencesManager = CarwareApplication.preferences)
        }
    }
}
