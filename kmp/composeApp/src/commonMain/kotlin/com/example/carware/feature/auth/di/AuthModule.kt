package com.example.carware.feature.auth.di

import com.example.carware.feature.auth.data.remote.AuthApi
import com.example.carware.feature.auth.data.repository.AuthRepositoryImpl
import com.example.carware.feature.auth.domain.repository.AuthRepository
import com.example.carware.feature.auth.presentation.changePassword.ChangePassViewModel
import com.example.carware.feature.auth.presentation.emailVerification.EmailVerificationViewModel
import com.example.carware.feature.auth.presentation.forgotPassword.ForgotPasswordViewModel
import com.example.carware.feature.auth.presentation.login.LoginViewModel
import com.example.carware.feature.auth.presentation.otpVerification.OTPViewModel
import com.example.carware.feature.auth.presentation.resetPassword.ResetPasswordViewModel
import com.example.carware.feature.auth.presentation.signup.SignUpViewModel
import org.koin.dsl.module

val authModule = module {
    single { AuthApi(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    factory { SignUpViewModel(get(), get()) }
    factory { LoginViewModel(get(), get()) }
    factory { ForgotPasswordViewModel(get()) }
    factory { OTPViewModel(get(), get()) }
    factory { ResetPasswordViewModel(get(), get()) }
    factory { EmailVerificationViewModel(get(), get()) }
    factory { ChangePassViewModel(get(),get()) }

}