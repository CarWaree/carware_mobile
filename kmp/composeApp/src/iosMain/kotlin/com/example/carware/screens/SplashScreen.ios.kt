package com.example.carware.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import com.example.carware.lottieControllerFactory
import platform.UIKit.UIViewController

@Composable
actual fun PlatformSplashAnimation() {
    val factory = lottieControllerFactory
    if (factory != null) {
        UIKitViewController(
            factory = factory,
            modifier = Modifier.fillMaxSize()
        )
    }
}