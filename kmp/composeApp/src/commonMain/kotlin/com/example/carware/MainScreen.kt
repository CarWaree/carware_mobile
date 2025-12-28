package com.example.carware

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.carware.navigation.AddCarScreen
import com.example.carware.navigation.EmailVerificationScreen
import com.example.carware.navigation.HistoryScreen
import com.example.carware.navigation.HomeScreen
import com.example.carware.navigation.LanguageSelectionScreen
import com.example.carware.navigation.LoginScreen
import com.example.carware.navigation.NewPasswordScreen
import com.example.carware.navigation.OnboardingScreen
import com.example.carware.navigation.ResetPasswordScreen
import com.example.carware.navigation.ScheduleScreen
import com.example.carware.navigation.SettingsScreen
import com.example.carware.navigation.SignUpScreen
import com.example.carware.navigation.SplashScreen
import com.example.carware.navigation.TestScreen
import com.example.carware.navigation.VerificationCodeScreen
import com.example.carware.repository.ServiceRepository
import com.example.carware.repository.VehicleRepository
import com.example.carware.repository.auth.AuthRepository
import com.example.carware.screens.AddCarScreen
import com.example.carware.screens.BottomNavBar
import com.example.carware.screens.onBoarding.OnBoardingScreen
import com.example.carware.screens.SplashScreen
import com.example.carware.screens.auth.EmailVerificationScreen
import com.example.carware.screens.auth.LoginScreen
import com.example.carware.screens.auth.NewPasswordScreen
import com.example.carware.screens.auth.ResetPasswordScreen
import com.example.carware.screens.auth.SignUpScreen
import com.example.carware.screens.auth.VerificationCodeScreen
import com.example.carware.screens.mainScreens.HistoryScreen
import com.example.carware.screens.mainScreens.HomeScreen
import com.example.carware.screens.mainScreens.ScheduleScreen
import com.example.carware.screens.mainScreens.SettingsScreen
import com.example.carware.screens.onBoarding.LanguageSelectionScreen
import com.example.carware.util.lang.AppLanguage
import com.example.carware.util.lang.LocalizedStrings
import com.example.carware.util.navBar.bottomTabs
import com.example.carware.util.storage.PreferencesManager
import com.example.carware.viewModel.home.HomeScreenViewModel
import com.example.carware.viewModel.addcar.AddCarViewModel
import com.example.carware.viewModel.auth.logIn.LogInViewModel
import com.example.carware.viewModel.auth.newPassword.NewPasswordViewModel
import com.example.carware.viewModel.auth.otpVerification.OTPViewModel
import com.example.carware.viewModel.auth.signUp.SignUpViewModel
import com.example.carware.viewModel.schedule.ScheduleScreenViewModel

val m = Modifier
val LocalStrings = staticCompositionLocalOf<LocalizedStrings> {
    error("No LocalizedStrings provided")
}

@Composable
fun MainScreen(preferencesManager: PreferencesManager) {
    val navController = rememberNavController()

    val pagerState = rememberPagerState(initialPage = bottomTabs.first().index) {
        bottomTabs.size
    }
    // Coroutine Scope needed for the BottomNavBar to smoothly animate the Pager scroll
    val scope = rememberCoroutineScope()

    var currentLanguage by remember {
        mutableStateOf(AppLanguage.fromCode(preferencesManager.getLanguageCode()))
    }

    val localizedStrings = remember(currentLanguage) {
        LocalizedStrings(preferencesManager)
    }

    val layoutDirection = if (currentLanguage == AppLanguage.AR) {
        LayoutDirection.Rtl
    } else {
        LayoutDirection.Ltr
    }
    val vehicleRepository = VehicleRepository(preferencesManager)

    val scheduleRepository = ServiceRepository()



    CompositionLocalProvider(
        LocalStrings provides localizedStrings,
        LocalLayoutDirection provides layoutDirection // This line flips the UI
    ) {
        NavHost(
            navController = navController,
            startDestination = VerificationCodeScreen,
        )
        {
            composable<HomeScreen> {
                Scaffold(
                    bottomBar = {
                        BottomNavBar(
                            pagerState = pagerState,
                            scope = scope,
                            tabs = bottomTabs,
                            modifier = Modifier.navigationBarsPadding()
                        )
                    }
                ) { innerPadding ->
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize(),
                        userScrollEnabled = true
                    ) { page ->
                        val currentTab = bottomTabs[page]
                        when (currentTab.route) {
                            HomeScreen::class -> HomeScreen(
                                navController, HomeScreenViewModel(
                                    VehicleRepository(preferencesManager), preferencesManager
                                )
                            )

                            ScheduleScreen::class -> ScheduleScreen(
                                navController,
                                viewModel = ScheduleScreenViewModel(
                                    ServiceRepository(),
                                    VehicleRepository(preferencesManager)
                                )
                            )

                            HistoryScreen::class -> HistoryScreen(navController)
                            SettingsScreen::class -> SettingsScreen(
                                navController,
                                preferencesManager,
                                onLangChange = { newLang ->
                                    preferencesManager.saveLanguageCode(newLang.isoCode)
                                    currentLanguage = newLang // This is the magic that flips the UI
                                }
                            )

                            else -> Box(Modifier.fillMaxSize())
                        }
                    }
                }
            }

            composable<OnboardingScreen> {
                OnBoardingScreen(navController, preferencesManager)
            }
            composable<SignUpScreen> {
                SignUpScreen(
                    navController,
                    preferencesManager,
                    SignUpViewModel(AuthRepository(preferencesManager),
                        preferencesManager)
                )
            }
            composable<LoginScreen> {
                LoginScreen(
                    navController,
                    preferencesManager,
                    LogInViewModel(AuthRepository(preferencesManager),
                        preferencesManager)
                    )
            }

            composable<ResetPasswordScreen> {
                ResetPasswordScreen(navController, preferencesManager)
            }
            composable<VerificationCodeScreen> {
                VerificationCodeScreen(navController,
                    preferencesManager,
                    OTPViewModel(AuthRepository(preferencesManager),
                        preferencesManager)
                )
            }
            composable<NewPasswordScreen> {
                NewPasswordScreen(navController,
                    preferencesManager,
                    NewPasswordViewModel(AuthRepository(preferencesManager),
                        preferencesManager)
                    )
            }
            composable<SettingsScreen> {
                SettingsScreen(
                    navController,
                    preferencesManager,
                    onLangChange = { newLang ->
                        preferencesManager.saveLanguageCode(newLang.isoCode)
                        currentLanguage = newLang // This is the magic that flips the UI
                    })
            }
            composable<ScheduleScreen> {
                ScheduleScreen(
                    navController,
                    viewModel = ScheduleScreenViewModel(
                        ServiceRepository(),
                        VehicleRepository(preferencesManager)
                    )
                )
            }
            composable<HistoryScreen> {
                HistoryScreen(navController)
            }
            composable<AddCarScreen> {
                AddCarScreen(
                    navController,
                    viewModel = AddCarViewModel(vehicleRepository, preferencesManager)
                )
            }
            composable<SplashScreen> {
                SplashScreen(
                    preferencesManager
                ) { destination ->
                    navController.navigate(destination) {
                        popUpTo(SplashScreen) { inclusive = true }
                    }
                }
            }
            composable<LanguageSelectionScreen> {
                LanguageSelectionScreen(
                    navController,
                    preferencesManager,
                    onLangChange = { newLang ->
                        preferencesManager.saveLanguageCode(newLang.isoCode)
                        currentLanguage = newLang // This triggers the RTL/LTR flip
                    }
                )
            }

            composable<TestScreen> {
                com.example.carware.screens.TestScreen(navController, preferencesManager)

            }
            composable<EmailVerificationScreen> {
                EmailVerificationScreen(navController)

            }
        }
    }
}
