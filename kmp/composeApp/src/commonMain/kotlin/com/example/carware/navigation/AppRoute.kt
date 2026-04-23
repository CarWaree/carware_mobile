package com.example.carware.navigation

import com.example.carware.network.apiResponse.vehicle.Vehicles
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
data class VerificationCodeScreen(val email: String)

@Serializable
object OnboardingScreen

@Serializable
object SettingsScreen

@Serializable
object HistoryScreen

@Serializable
object ScheduleScreen

@Serializable
object AddCarScreen


@Serializable
object SplashScreen

@Serializable
object LanguageSelectionScreen

@Serializable
object TestScreen

@Serializable
data class EmailVerificationScreen(val email: String)

@Serializable
object ProfileScreen

@Serializable
object EditProfileScreen


@Serializable
object SelectLanguageScreen


@Serializable
object ReminderScreen

@Serializable
object ServiceRecordScreen

@Serializable
data class EditCarScreen(val carId: Int)
