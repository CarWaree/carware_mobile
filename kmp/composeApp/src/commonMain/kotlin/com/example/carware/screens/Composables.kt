package com.example.carware.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import carware.composeapp.generated.resources.Res
import carware.composeapp.generated.resources.car
import carware.composeapp.generated.resources.color
import carware.composeapp.generated.resources.dots
import carware.composeapp.generated.resources.modelyear
import carware.composeapp.generated.resources.poppins_medium
import carware.composeapp.generated.resources.poppins_semibold
import com.example.carware.m
import com.example.carware.util.navBar.TabItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


fun Modifier.appGradBack(): Modifier = this.then(
    background(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color(0xFFC20000),
                Color(0xFF5C0000)
            )
        )
    )
)

fun Modifier.appButtonBack(): Modifier = this.then(
    background(
        brush = Brush.horizontalGradient(
            colors = listOf(
                Color(0xFFC20000),
                Color(0xFF5C0000)
            )
        )
    )
)
fun Modifier.disabledAppButtonBack(): Modifier = this.then(
    background(
        brush = Brush.horizontalGradient(
            colors = listOf(
                Color(0xFFFFFFFF),
                Color(0xFF000000)
            )
        )
    )
)


fun Modifier.cardGradBack(): Modifier = this.then(
    background(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color(0xFF949697),
                Color(0xFFC9CDCF)
            )
        )
    )
)


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomNavBar(
    pagerState: PagerState,
    scope: CoroutineScope,
    tabs: List<TabItem>,
    modifier: Modifier = Modifier
) {
    val popSemi = FontFamily(Font(Res.font.poppins_semibold))

    val popMid = FontFamily(Font(Res.font.poppins_medium))



    // OUTER HEIGHT ONLY FOR SPACING
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        contentAlignment = Alignment.Center
    ) {

        // ACTUAL NAV BAR CONTAINER
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .size(350.dp,55.dp)
                .padding(horizontal = 10.dp)
                .background(
                    color = Color(204, 204, 204, 255),
                    shape = RoundedCornerShape(50.dp)
                )
        ) {

            // ---- MEASUREMENTS ----
            val barWidth = maxWidth
            val tabWidth = barWidth / tabs.size

            val pillWidth = 80.dp     // edit size here
            val pillHeight = 40.dp

            // Calculates X position for the pill
            val indicatorOffset by animateDpAsState(
                targetValue = (tabWidth * pagerState.currentPage) +
                        (tabWidth / 2) - (pillWidth / 2),
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                ),
                label = "indicator"
            )

            // ---- MOVING PILL (BEHIND CONTENT) ----
            Box(
                modifier = Modifier
                    .offset(x = indicatorOffset)
                    .align(Alignment.CenterStart)   // <-- ALWAYS CENTER VERTICALLY
                    .size(pillWidth, pillHeight)
                    .clip(RoundedCornerShape(50.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFC20000),
                                Color(0xFF5C0000)
                            )
                        )
                        )
                    // deep red like screenshot
            )

            // ---- NAV ITEMS ----
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                tabs.forEach { tab ->

                    val isSelected = pagerState.currentPage == tab.index

                    // Each tab occupies equal width
                    Box(
                        modifier = Modifier
                            .width(tabWidth)
                            .fillMaxHeight()
                            .clickable {
                                scope.launch {
                                    pagerState.animateScrollToPage(tab.index)
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            // ICON
                            Icon(
                                painter = painterResource(tab.icon),
                                contentDescription = tab.title,
                                tint = if (isSelected) Color(204, 204, 204, 255) else Color.Unspecified,
                                modifier = Modifier.size(22.dp)
                            )

                            // TITLE
                            if (isSelected) {
                                Spacer(modifier = Modifier.width(6.dp))

                                Text(
                                    text = tab.title,
                                    fontFamily = popSemi,
                                    color = Color(204, 204, 204, 255),
                                    fontSize = 12.sp,
//                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun CarCard(
    brand: String,
    model: String,
    modelYear: String,
    color: String,
    image: DrawableResource
) {
    val popSemi = FontFamily(Font(Res.font.poppins_semibold))
    val popMid = FontFamily(Font(Res.font.poppins_medium))

    val cardMod = Modifier
        .size(width = 315.dp, height = 265.dp)


    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0x39000000)),
        modifier = cardMod
            //                       .offset(x = 4.dp, y = 8.dp) // move the shadow
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(15.dp),
                clip = false
            ),
        shape = RoundedCornerShape(15.dp),


        ) { }//shadow
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = cardMod
            .clip(shape = RoundedCornerShape(15.dp))
            .cardGradBack(),
    ) {
        Column(
            modifier = m.fillMaxSize()
                .padding(top = 20.dp, bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Row(
                modifier = m
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    painter = painterResource(Res.drawable.dots),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = m
                        .size(20.dp)

                )
            } // top dots
            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = m
                    .size(230.dp, 160.dp)

            ) //car image
            Row(
                modifier = m
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    brand,
                    fontFamily = popSemi,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(102, 102, 102, 255)
                )
            } //car brand
            Spacer(modifier = m.padding(vertical = 4.dp))
            Row(
                modifier = m
                    .fillMaxWidth(),
                //                                .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Icon(
                        painter = painterResource(Res.drawable.car),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = m
                            .size(22.dp)

                    ) //car icon
                    Spacer(modifier = m.padding(horizontal = 2.dp))
                    Text(
                        brand,
                        fontFamily = popSemi,
                        fontSize = 14.sp,
                        color = Color(102, 102, 102, 255)
                    )
                }
                Spacer(modifier = m.padding(horizontal = 8.dp))
                Row(
                    modifier = m
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.modelyear),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = m
                            .size(22.dp)

                    ) //model year icon
                    Spacer(modifier = m.padding(horizontal = 2.dp))
                    Text(
                        modelYear,
                        fontFamily = popSemi,
                        fontSize = 14.sp,
                        color = Color(102, 102, 102, 255)
                    ) //model year

                }
                Spacer(modifier = m.padding(horizontal = 8.dp))
                Row(
                    modifier = m
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.color),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = m
                            .size(22.dp)

                    ) //color icon
                    Spacer(modifier = m.padding(horizontal = 2.dp))
                    Text(
                        color,
                        fontFamily = popSemi,
                        fontSize = 14.sp,
                        color = Color(102, 102, 102, 255)
                    ) //color

                }

            } //car details
        } //card content
    }  // car card
}



@Composable
fun CurvedLineCanvas() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val path = Path().apply {
            moveTo(0f, 0f) // start point
            // First curve (quadratic)
            quadraticBezierTo(85f, 50f, 50f, 140f)
            // Second curve (quadratic)
            quadraticBezierTo(150f, 100f, 320f, 110f)
        }

        drawPath(
            path = path,
            color = Color.Red,
            style = Stroke(width = 4f)
        )
    }
}


@Preview
@Composable
fun PrevLine(){
    CurvedLineCanvas()
}

