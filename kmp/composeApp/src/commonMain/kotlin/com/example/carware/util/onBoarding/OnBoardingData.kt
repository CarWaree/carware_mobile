package com.example.carware.util.onBoarding

import androidx.compose.runtime.Composable
import carware.composeapp.generated.resources.Res
import carware.composeapp.generated.resources.onBoard1
import carware.composeapp.generated.resources.onBoard3
import carware.composeapp.generated.resources.onBoarding2
import com.example.carware.LocalStrings
import com.example.carware.util.lang.LocalizedStrings
import org.jetbrains.compose.resources.DrawableResource


data class OnboardingPage(
    val id: Int,
    val title: String,
    val description: String,
    val imageRes: DrawableResource // Will be handled differently per platform
)


object OnboardingConfig {

    val pages = listOf(

        OnboardingPage(
            id = 1,
            title = "ONBOARDING_TITLE_1",
            description = "ONBOARDING_DESC_1",
            imageRes = Res.drawable.onBoard1
        ),
        OnboardingPage(
            id = 2,
            title = "ONBOARDING_TITLE_2",
            description = "ONBOARDING_DESC_3",

            imageRes = Res.drawable.onBoarding2
        ),
        OnboardingPage(
            id = 3,
            title = "ONBOARDING_TITLE_3",
            description = "ONBOARDING_DESC_3",
            imageRes = Res.drawable.onBoard3
        )
    )

}
