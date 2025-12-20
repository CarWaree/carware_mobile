package com.example.carware.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import carware.composeapp.generated.resources.Res
import carware.composeapp.generated.resources.carware
import carware.composeapp.generated.resources.line_1
import carware.composeapp.generated.resources.poppins_medium
import carware.composeapp.generated.resources.poppins_semibold
import com.example.carware.m
import com.example.carware.navigation.HomeScreen
import com.example.carware.navigation.OnboardingScreen
import com.example.carware.navigation.SignUpScreen
import com.example.carware.util.onBoarding.OnboardingConfig.pages
import com.example.carware.util.storage.PreferencesManager
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource


@Composable
fun OnBoardingScreen(
    navController: NavController,
    preferencesManager: PreferencesManager
) {
    val popSemi = FontFamily(Font(Res.font.poppins_semibold))
    val popMid = FontFamily(Font(Res.font.poppins_medium))

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .appGradBack()
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { pageIndex ->
            val page = pages[pageIndex]

            Column(
                modifier = m
                    .fillMaxSize()
                    .padding(top = 95.dp)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    painter = painterResource(Res.drawable.line_1),
                    contentDescription = null,
                    tint = Color(211, 203, 203, 255)
                )
                Spacer(modifier = m.padding(vertical = 4.dp))
                Icon(
                    painter = painterResource(Res.drawable.carware),
                    contentDescription = null,
                    tint = Color(211, 203, 203, 255)
                )
                Spacer(modifier = m.padding(vertical = 25.dp))

                Image(
                    painter = painterResource(page.imageRes),
                    contentDescription = null,
                    modifier = Modifier.size(300.dp)
                ) // image
//                Spacer(Modifier.padding(vertical = 5.dp))
                Text(
                    text = page.title,
                    color = Color.White,
                    fontSize = 26.sp,
                    fontFamily = popSemi,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                ) //Title
                Spacer(Modifier.padding(vertical = 2.dp))
                Text(
                    text = page.description,
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 16.sp,
                    fontFamily = popMid,
                    textAlign = TextAlign.Center
                ) // desc
            }
        }
        val isFirstPage = pagerState.currentPage == 0
        val isLastPage = pagerState.currentPage == pages.size - 1

        if (isLastPage) {
            Column(
                modifier = m
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Card(
                    onClick = {
                        preferencesManager.setOnboardingComplete()
                        navController.navigate(SignUpScreen) {
                            popUpTo(OnboardingScreen) { inclusive = true }
                        }
                     },
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(2.dp, Color(30, 30, 30, 110)),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(217, 217, 217, 255)
                    ),
                    modifier = m.size(250.dp, 40.dp)
                ) {

                    Row(
                        modifier = m.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Get Started",
                            fontFamily = popSemi,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(55, 71, 79, 255)
                        )
                    }
                } // get started
                Spacer(modifier = m.padding(vertical = 10.dp))
                Card(
                    onClick = {
                        preferencesManager.setOnboardingComplete()
                        navController.navigate(HomeScreen) {
                            popUpTo(OnboardingScreen) { inclusive = true }
                        }
                    },
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(2.dp, Color(30, 30, 30, 110)),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(217, 217, 217, 255)
                    ),
                    modifier = m.size(250.dp, 40.dp)
                ) {

                    Row(
                        modifier = m.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Continue as a Guest",
                            fontFamily = popSemi,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(55, 71, 79, 255)
                        )
                    }
                } // Continue as a Guest
                Spacer(modifier = m.padding(top = 17.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = if (isFirstPage) "Skip" else "Back",
                        color = Color.White,
                        modifier = m
                            .clickable {
                                if (isFirstPage) {
                                        preferencesManager.setOnboardingComplete()
                                        navController.navigate(SignUpScreen) {
                                            popUpTo(OnboardingScreen) { inclusive = true }
                                        }
                                } else {
                                    scope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                    }
                                }
                            },
                        fontSize = 18.sp,
                        fontFamily = popMid,

                        )
                }


            }

        } else {
            Column(
                modifier = m
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                PagerIndicator(
                    size = pages.size,
                    currentPage = pagerState.currentPage
                )
                Spacer(Modifier.height(30.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {


                    if (!isLastPage) {
                        Text(
                            text = if (isFirstPage) "Skip" else "Back",
                            color = Color.White,
                            modifier = Modifier.clickable {
                                if (isFirstPage) {
                                    preferencesManager.setOnboardingComplete()
                                    navController.navigate(SignUpScreen) {
                                        popUpTo(OnboardingScreen) { inclusive = true }
                                    }

                                } else {
                                    scope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                    }
                                }
                            },
                            fontSize = 18.sp,
                            fontFamily = popMid
                        )
                    } else {
                        Spacer(Modifier.width(50.dp))
                    }

                    Text(
                        text = if (isLastPage) "Get Started" else "Next",
                        color = Color.White,
                        modifier = Modifier.clickable {
                            if (isLastPage) {
                                preferencesManager.setOnboardingComplete()
                                navController.navigate(SignUpScreen) {
                                    popUpTo(OnboardingScreen) { inclusive = true }

                    }
                            } else {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            }
                        },
                        fontSize = 18.sp,
                        fontFamily = popMid
                    )
                }
            }
        }

    }

}


@Composable
fun IndicateIcon(isSelected: Boolean) {

    val shape = RoundedCornerShape(50)

    Box(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .height(8.dp)
            .width(42.dp)
            .clip(shape)
            .then(
                if (isSelected) {
                    Modifier.background(Color(0xFFD9D9D9)) // EXACT color in your screenshot
                } else {
                    Modifier
                        .background(Color.Transparent)
                        .border(1.dp, Color.White, shape)
                }
            )
    )
}

@Composable
fun PagerIndicator(size: Int, currentPage: Int) {
    Row(
        // modifier=m.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ) {
        repeat(size) { index ->
            IndicateIcon(isSelected = index == currentPage)
        }
    }
}

