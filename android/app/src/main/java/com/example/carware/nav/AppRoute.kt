package com.example.carware.nav

import kotlinx.serialization.Serializable

@Serializable
object SignUpScreen

@Serializable
object LoginScreen

@Serializable
object HomeScreen

@Serializable
object ResetPasswordScreen

@Serializable
object NewPasswordScreen

@Serializable
object VerficationCodeScreen

// Optional: Sealed class wrapper without serialization
sealed class AppRoute {
    object SignUp : AppRoute()
    object Login : AppRoute()
    object Home : AppRoute()
    object ResetPassword : AppRoute()
    object VerificationCode : AppRoute()
    object NewPassword: AppRoute()
}