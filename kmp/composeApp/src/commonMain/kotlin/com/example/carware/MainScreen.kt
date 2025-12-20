package com.example.carware

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.carware.navigation.HistoryScreen
import com.example.carware.navigation.HomeScreen
import com.example.carware.navigation.LoginScreen
import com.example.carware.navigation.NewPasswordScreen
import com.example.carware.navigation.OnboardingScreen
import com.example.carware.navigation.ResetPasswordScreen
import com.example.carware.navigation.ScheduleScreen
import com.example.carware.navigation.SettingsScreen
import com.example.carware.navigation.SignUpScreen
import com.example.carware.navigation.TestScreen
import com.example.carware.navigation.VerificationCodeScreen
import com.example.carware.screens.BottomNavBar
import com.example.carware.screens.OnBoardingScreen
import com.example.carware.screens.TestScreen
import com.example.carware.screens.appGradBack
import com.example.carware.screens.auth.LoginScreen
import com.example.carware.screens.auth.NewPasswordScreen
import com.example.carware.screens.auth.ResetPasswordScreen
import com.example.carware.screens.auth.SignUpScreen
import com.example.carware.screens.auth.VerificationCodeScreen
import com.example.carware.screens.mainScreens.HistoryScreen
import com.example.carware.screens.mainScreens.HomeScreen
import com.example.carware.screens.mainScreens.ScheduleScreen
import com.example.carware.screens.mainScreens.SettingsScreen
import com.example.carware.util.navBar.bottomTabs
import com.example.carware.util.LoginManager

val m = Modifier

@Composable
fun MainScreen(loginManager: LoginManager) {
    val navController = rememberNavController()

    val pagerState = rememberPagerState(initialPage = bottomTabs.first().index) {
        bottomTabs.size
    }
    // Coroutine Scope needed for the BottomNavBar to smoothly animate the Pager scroll
    val scope = rememberCoroutineScope()

    val startDestination = when {
        !loginManager.isOnboardingComplete() -> OnboardingScreen
        loginManager.shouldAutoLogin() -> HomeScreen
        else -> SignUpScreen   // or LoginScreen
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()



    NavHost(
        navController = navController,
        startDestination = TestScreen,
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
                            HomeScreen::class -> HomeScreen(navController,)
                            ScheduleScreen::class -> ScheduleScreen(navController)
                            HistoryScreen::class -> HistoryScreen(navController)
                            SettingsScreen::class -> SettingsScreen(navController)
                            else -> Box(Modifier.fillMaxSize())
                        }
                    }
                }
            }

        composable<OnboardingScreen> {
            OnBoardingScreen(navController, loginManager)
        }
        composable<SignUpScreen> {
            SignUpScreen(navController, loginManager)
        }
        composable<LoginScreen> {
            LoginScreen(navController, loginManager)
        }

        composable<ResetPasswordScreen> {
            ResetPasswordScreen(navController)
        }
        composable<VerificationCodeScreen> {
            VerificationCodeScreen(navController)
        }
        composable<NewPasswordScreen> {
            NewPasswordScreen(navController)
        }
        composable<SettingsScreen> {
            SettingsScreen(navController)
        }
        composable<ScheduleScreen> {
            ScheduleScreen(navController)
        }
        composable<HistoryScreen> {
            HistoryScreen(navController)
        }
        composable<TestScreen> {
            TestScreen(navController)
        }

    }
}




