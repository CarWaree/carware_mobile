package com.example.carware.di

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.viewmodel.compose.viewModel
import carware.composeapp.generated.resources.Res
import carware.composeapp.generated.resources.poppins_medium
import carware.composeapp.generated.resources.poppins_semibold
import com.example.carware.network.createHttpClient
import com.example.carware.repository.HistoryRepository
import com.example.carware.repository.ProfileRepository
import com.example.carware.repository.ServiceRepository
import com.example.carware.repository.VehicleRepository
import com.example.carware.repository.auth.AuthRepository
import com.example.carware.util.storage.PreferencesManager
import com.example.carware.viewModel.addcar.AddCarViewModel
import com.example.carware.viewModel.auth.emailVerification.EmailVerificationViewModel
import com.example.carware.viewModel.auth.forgotPassword.ForgotPasswordViewModel
import com.example.carware.viewModel.auth.logIn.LogInViewModel
import com.example.carware.viewModel.auth.newPassword.NewPasswordViewModel
import com.example.carware.viewModel.auth.otpVerification.OTPViewModel
import com.example.carware.viewModel.auth.signUp.SignUpViewModel
import com.example.carware.viewModel.history.HistoryScreenViewModel
import com.example.carware.viewModel.home.HomeScreenViewModel
import com.example.carware.viewModel.profile.ProfileScreenViewModel
import com.example.carware.viewModel.schedule.screen.ScheduleScreenViewModel
import org.koin.dsl.module

fun appModule(preferencesManager: PreferencesManager) = module {

    // Core
    single { preferencesManager }
    single { createHttpClient(get()) }

    // Repositories
    single { VehicleRepository(get()) }
    single { AuthRepository(get()) }
    single { HistoryRepository(get()) }
    single { ProfileRepository(get()) }
    single { ServiceRepository(get()) }

    // ViewModels
    factory { HomeScreenViewModel(get()) }
    factory { SignUpViewModel(get(), get(), get()) }
    factory { LogInViewModel(get(), get(), get()) }
    factory { OTPViewModel(get(), get()) }
    factory { NewPasswordViewModel(get(), get()) }
    factory { EmailVerificationViewModel(get(), get()) }
    factory { HistoryScreenViewModel(get()) }
    factory { ScheduleScreenViewModel(get(), get()) }
    factory { AddCarViewModel(get(), get()) }
    factory { ProfileScreenViewModel(get(), get()) }
    factory { ForgotPasswordViewModel(get(), get()) }
    // AppFonts.kt

}

