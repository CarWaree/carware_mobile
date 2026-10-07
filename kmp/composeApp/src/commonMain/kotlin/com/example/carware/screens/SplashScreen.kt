package com.example.carware.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.carware.core.navigation.AddCarScreen
import com.example.carware.core.navigation.HomeScreen
import com.example.carware.core.navigation.LanguageSelectionScreen
import com.example.carware.core.navigation.OnboardingScreen
import com.example.carware.core.navigation.SignUpScreen
import com.example.carware.core.storage.PreferencesManager
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable


@Composable
fun SplashScreen(
    preferencesManager: PreferencesManager,
    onNavigate: (route: @Serializable Any) -> Unit
) {
    LaunchedEffect(Unit) {

        // optional delay for UX
        delay(1800)

        val destination = when {
            !preferencesManager.isLanguageSelected() -> LanguageSelectionScreen
            !preferencesManager.isOnboardingComplete() -> OnboardingScreen
            !preferencesManager.isLoggedIn() -> SignUpScreen
            !preferencesManager.hasAddedCar() -> AddCarScreen
            else -> HomeScreen
        }

        onNavigate(destination)
    }

    PlatformSplashAnimation()
}

@Composable
expect fun PlatformSplashAnimation(

)
