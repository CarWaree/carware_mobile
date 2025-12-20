package com.example.carware.screens.auth

import com.example.carware.util.onBoarding.OnboardingConfig.pages
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import carware.composeapp.generated.resources.poppins_medium
import carware.composeapp.generated.resources.poppins_semibold
import com.example.carware.navigation.SignUpScreen
import com.example.carware.util.lang.AppLanguage
import com.example.carware.util.lang.LocalizedStrings
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun OnboardingScreenhj(
    // Simplified parameters: Only what the screen needs to do its job.
    onComplete: () -> Unit,
    navController: NavController
) {
    val popSemi = FontFamily(Font(Res.font.poppins_semibold))
    val popMid = FontFamily(Font(Res.font.poppins_medium))

    // State management: pagerState is the single source of truth.
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()
    val lang = AppLanguage.AR
    val strings = LocalizedStrings(lang)

    // --- LAYOUT ---
    // 1. Parent Box to correctly layer the content and controls.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF3A6EA5)) // Or your appGradBack() modifier
    ) {
        // 2. The HorizontalPager for swiping through pages.
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { pageIndex ->
            val pageData = pages[pageIndex]
            // The content for each page (Image and Text)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(pageData.imageRes),
                    contentDescription = "Onboarding ${pageData.id}",
                    modifier = Modifier.size(300.dp)
                )
                Spacer(Modifier.height(50.dp))
                Text(
                    text = pageData.title,
                    fontFamily = popSemi,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = pageData.description,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = popMid,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }

        // 3. The bottom controls (Indicator and Buttons)
        // This Column is aligned to the bottom of the parent Box.
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter) // This works because it's inside the parent Box
                .fillMaxWidth()
                .padding(bottom = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PagerIndicator(
                size = pages.size,
                currentPage = pagerState.currentPage
            )
            Spacer(Modifier.height(40.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Simplified button logic
                val isFirstPage = pagerState.currentPage == 0
                val isLastPage = pagerState.currentPage == pages.size - 1

                if (isLastPage) {
                    // On the last page, show only "Get Started"
                    Spacer(Modifier.weight(1f)) // Spacer for centering
                    Text(
                        text = strings.get("GET_STARTED"),
                        color = Color.White,
                        modifier = Modifier
                            .clickable {
                                onComplete()
                                navController.navigate(SignUpScreen)
                            }
                            .weight(1f),
                        textAlign = TextAlign.Center,
                        fontFamily = popMid,
                        fontSize = 18.sp
                    )
                    Spacer(Modifier.weight(1f)) // Spacer for centering
                } else {
                    // On all other pages
                    Text(
                        text = if (isFirstPage) strings.get("SKIP") else strings.get("BACK"),
                        color = Color.White,
                        modifier = Modifier.clickable {
                            if (isFirstPage) {
                                // Skip goes to the end
                                onComplete()
                                navController.navigate(SignUpScreen)
                            } else {
                                // Back goes to previous page
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                }
                            }
                        },
                        fontFamily = popMid,
                        fontSize = 18.sp
                    )

                    Text(
                        text = strings.get("NEXT"),
                        color = Color.White,
                        modifier = Modifier.clickable {
                            // Next goes to next page
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        },
                        fontFamily = popMid,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
fun PagerIndicator(size: Int, currentPage: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(top = 20.dp)
    ) {
        repeat(size) { iteration ->
            IndicateIcon(isSelected = iteration == currentPage)
        }
    }
}

@Composable
fun IndicateIcon(isSelected: Boolean) {
    val width by animateDpAsState(targetValue = if (isSelected) 30.dp else 10.dp)
    Box(
        modifier = Modifier
            .height(10.dp)
            .width(width)
            .clip(CircleShape)
            .background(if (isSelected) Color.White else Color.Gray)
    )
}
