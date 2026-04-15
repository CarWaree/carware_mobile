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
import com.example.carware.navigation.EditProfileScreen
import com.example.carware.navigation.EmailVerificationScreen
import com.example.carware.navigation.HistoryScreen
import com.example.carware.navigation.HomeScreen
import com.example.carware.navigation.LanguageSelectionScreen
import com.example.carware.navigation.LoginScreen
import com.example.carware.navigation.NewPasswordScreen
import com.example.carware.navigation.OnboardingScreen
import com.example.carware.navigation.ProfileScreen
import com.example.carware.navigation.ResetPasswordScreen
import com.example.carware.navigation.ScheduleScreen
import com.example.carware.navigation.SelectLanguageScreen
import com.example.carware.navigation.SettingsScreen
import com.example.carware.navigation.SignUpScreen
import com.example.carware.navigation.SplashScreen
import com.example.carware.navigation.TestScreen
import com.example.carware.navigation.VerificationCodeScreen
import com.example.carware.screens.AddCarScreen
import com.example.carware.screens.BottomNavBar
import com.example.carware.screens.SelectLanguageScreen
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
import com.example.carware.screens.onBoarding.OnBoardingScreen
import com.example.carware.screens.profile.EditProfileScreen
import com.example.carware.screens.profile.ProfileScreen
import com.example.carware.util.lang.AppLanguage
import com.example.carware.util.lang.LocalizedStrings
import com.example.carware.util.navBar.bottomTabs
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
import com.example.carware.viewModel.notification.NotificationViewModel
import com.example.carware.viewModel.profile.ProfileScreenViewModel
import com.example.carware.viewModel.schedule.screen.ScheduleScreenViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

val m = Modifier
val LocalStrings = staticCompositionLocalOf<LocalizedStrings> {
    error("No LocalizedStrings provided")
}

@Composable

fun MainScreen() {
    val navController = rememberNavController()
    val pagerState = rememberPagerState(initialPage = bottomTabs.first().index) { bottomTabs.size }
    val scope = rememberCoroutineScope()

    val preferencesManager: PreferencesManager = koinInject()

    var currentLanguage by remember {
        mutableStateOf(AppLanguage.fromCode(preferencesManager.getLanguageCode()))
    }
    val localizedStrings = remember(currentLanguage) { LocalizedStrings(preferencesManager) }
    val layoutDirection =
        if (currentLanguage == AppLanguage.AR) LayoutDirection.Rtl else LayoutDirection.Ltr

//    // ✅ Get all ViewModels from Koin once
//    val homeViewModel: HomeScreenViewModel = koinViewModel()
//    val signUpViewModel: SignUpViewModel = koinViewModel()
//    val loginViewModel: LogInViewModel = koinViewModel()
//    val otpViewModel: OTPViewModel =koinViewModel()
//    val newPasswordViewModel: NewPasswordViewModel = koinViewModel()
//    val emailVerificationViewModel: EmailVerificationViewModel = koinViewModel()
//    val historyViewModel: HistoryScreenViewModel = koinViewModel()
//    val scheduleViewModel: ScheduleScreenViewModel =koinViewModel()
//    val addCarViewModel: AddCarViewModel =koinViewModel()
//    val forgetPasswordViewModel: ForgotPasswordViewModel =koinViewModel()
//    val profileViewModel: ProfileScreenViewModel = koinViewModel()

    CompositionLocalProvider(
        LocalStrings provides localizedStrings,
        LocalLayoutDirection provides layoutDirection
    ) {
        NavHost(navController = navController, startDestination = SplashScreen) {

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
                ) { _ ->
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize(),
                        userScrollEnabled = true
                    ) { page ->
                        when (bottomTabs[page].route) {
                            HomeScreen::class -> {
                                val homeViewModel: HomeScreenViewModel = koinViewModel()
                                val notificationViewModel: NotificationViewModel = koinViewModel()
                                HomeScreen(navController, homeViewModel, notificationViewModel)
                            }

                            ScheduleScreen::class -> {
                                val scheduleViewModel: ScheduleScreenViewModel = koinViewModel()
                                ScheduleScreen(navController, scheduleViewModel, preferencesManager)
                            }

                            HistoryScreen::class -> {
                                val historyViewModel: HistoryScreenViewModel = koinViewModel()
                                HistoryScreen(navController, historyViewModel)
                            }

                            SettingsScreen::class -> {
                                SettingsScreen(navController, preferencesManager)
                            }

                            else -> Box(Modifier.fillMaxSize())
                        }

                    }
                }
            }

            composable<OnboardingScreen> {
                OnBoardingScreen(navController, preferencesManager)
            }

            composable<SignUpScreen> { backStackEntry ->
                val signUpViewModel: SignUpViewModel = koinViewModel(
                    viewModelStoreOwner = backStackEntry
                )
                SignUpScreen(navController, signUpViewModel)
            }

            composable<LoginScreen> { backStackEntry ->
                val logInViewModel: LogInViewModel = koinViewModel(
                    viewModelStoreOwner = backStackEntry
                )
                LoginScreen(navController, logInViewModel)
            }

            composable<ResetPasswordScreen> { backStackEntry ->
                val forgetPasswordViewModel: ForgotPasswordViewModel = koinViewModel(
                    viewModelStoreOwner = backStackEntry
                )
                ResetPasswordScreen(navController, forgetPasswordViewModel)
            }

            composable<VerificationCodeScreen> { backStackEntry ->
                val otpViewModel: OTPViewModel = koinViewModel(
                    viewModelStoreOwner = backStackEntry
                )
                VerificationCodeScreen(navController, otpViewModel)
            }

            composable<NewPasswordScreen> { backStackEntry ->
                val newPasswordViewModel: NewPasswordViewModel = koinViewModel(
                    viewModelStoreOwner = backStackEntry
                )
                NewPasswordScreen(navController, newPasswordViewModel)
            }

            composable<SettingsScreen> {
                SettingsScreen(navController, preferencesManager)
            }

            composable<ScheduleScreen> { backStackEntry ->
                val scheduleViewModel: ScheduleScreenViewModel = koinViewModel(
                    viewModelStoreOwner = backStackEntry
                )
                ScheduleScreen(navController, scheduleViewModel, preferencesManager)
            }

            composable<HistoryScreen> { backStackEntry ->
                val historyViewModel: HistoryScreenViewModel = koinViewModel(
                    viewModelStoreOwner = backStackEntry
                )
                HistoryScreen(navController, historyViewModel)
            }

            composable<AddCarScreen> { backStackEntry ->
                val addCarViewModel: AddCarViewModel = koinViewModel(
                    viewModelStoreOwner = backStackEntry
                )
                AddCarScreen(navController, addCarViewModel)
            }

            composable<SplashScreen> {
                SplashScreen(preferencesManager) { destination ->
                    navController.navigate(destination) {
                        popUpTo(SplashScreen) {
                            inclusive = true
                        }
                    }
                }
            }

            composable<LanguageSelectionScreen> {
                LanguageSelectionScreen(navController, preferencesManager, onLangChange = {
                    preferencesManager.saveLanguageCode(it.isoCode)
                    currentLanguage = it
                })
            }

            composable<TestScreen> {
                com.example.carware.screens.TestScreen(navController, preferencesManager)
            }

            composable<EmailVerificationScreen> { backStackEntry ->
                val emailVerificationViewModel: EmailVerificationViewModel = koinViewModel(
                    viewModelStoreOwner = backStackEntry
                )
                EmailVerificationScreen(navController, emailVerificationViewModel)
            }

            composable<ProfileScreen> { backStackEntry ->
                val profileViewModel: ProfileScreenViewModel = koinViewModel(
                    viewModelStoreOwner = backStackEntry
                )
                ProfileScreen(navController, profileViewModel, preferencesManager)
            }
            composable<EditProfileScreen> { backStackEntry ->
                val profileViewModel: ProfileScreenViewModel = koinViewModel(
                    viewModelStoreOwner = backStackEntry

                )
                EditProfileScreen(navController, profileViewModel)
            }
            composable<SelectLanguageScreen> {
                SelectLanguageScreen(
                    navController, preferencesManager,
                    onLangChange = {
                        preferencesManager.saveLanguageCode(it.isoCode)
                        currentLanguage = it
                    })
            }
        }
    }
}