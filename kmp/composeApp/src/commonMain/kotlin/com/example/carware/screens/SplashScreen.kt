package com.example.carware.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import carware.composeapp.generated.resources.Res

import com.example.carware.navigation.AddCarScreen
import com.example.carware.navigation.HomeScreen
import com.example.carware.navigation.OnboardingScreen
import com.example.carware.navigation.SignUpScreen
import com.example.carware.util.LoginManager
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource


@Composable
fun SplashScreen(
    loginManager: LoginManager,
    onNavigate: (route: @Serializable Any) -> Unit
) {
    LaunchedEffect(Unit) {

        // optional delay for UX
        delay(3000)

        val destination = when {
            !loginManager.isOnboardingComplete() -> OnboardingScreen
            !loginManager.shouldAutoLogin() -> SignUpScreen
            !loginManager.hasAddedCar() -> AddCarScreen
            else -> HomeScreen
        }

        onNavigate(destination)
    }

    PlatformSplashAnimation()
}

@Composable
expect fun PlatformSplashAnimation(

)
