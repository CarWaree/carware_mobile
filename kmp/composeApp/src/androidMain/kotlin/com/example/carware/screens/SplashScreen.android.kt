package com.example.carware.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.carware.R
import kotlinx.coroutines.delay

@Composable
actual fun PlatformSplashAnimation(

) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(179, 0, 0, 255)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.cw)
        )

        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = 1,
            isPlaying = true,
            speed = 0.9f,

            )

        LottieAnimation(
            composition = composition,
            progress = { progress },
        )
    }

}
