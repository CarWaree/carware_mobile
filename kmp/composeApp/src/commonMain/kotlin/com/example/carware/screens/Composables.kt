package com.example.carware.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import carware.composeapp.generated.resources.Res
import carware.composeapp.generated.resources.arrow_1
import carware.composeapp.generated.resources.audi
import carware.composeapp.generated.resources.car
import carware.composeapp.generated.resources.check_circle
import carware.composeapp.generated.resources.check_time_slot
import carware.composeapp.generated.resources.clander_right_arrow
import carware.composeapp.generated.resources.color
import carware.composeapp.generated.resources.cuate
import carware.composeapp.generated.resources.dots
import carware.composeapp.generated.resources.failed
import carware.composeapp.generated.resources.keyboard_arrow_down
import carware.composeapp.generated.resources.keyboard_arrow_up
import carware.composeapp.generated.resources.modelyear
import carware.composeapp.generated.resources.poppins_medium
import carware.composeapp.generated.resources.poppins_semibold
import carware.composeapp.generated.resources.recycle_bin
import carware.composeapp.generated.resources.success
import carware.composeapp.generated.resources.x_time_slot
import com.example.carware.m
import com.example.carware.util.navBar.TabItem
import com.example.carware.viewModel.schedule.screen.ScheduleScreenState
import com.example.carware.viewModel.schedule.screen.ScheduleScreenViewModel
import com.example.carware.viewModel.schedule.screen.TimeSlot
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
                Color(0xFFC20000), Color(0xFF5C0000)
            )
        )
    )
)

fun Modifier.appButtonBack(): Modifier = this.then(
    background(
        brush = Brush.horizontalGradient(
            colors = listOf(
                Color(0xFFC20000), Color(0xFF5C0000)
            )
        )
    )
)

fun Modifier.disabledAppButtonBack(): Modifier = this.then(
    background(
        brush = Brush.horizontalGradient(
            colors = listOf(
                Color(0xFFFFFFFF), Color(0xFF000000)
            )
        )
    )
)


fun Modifier.cardGradBack(): Modifier = this.then(
    background(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color(0xFF949697), Color(0xFFC9CDCF)
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
        modifier = modifier.fillMaxWidth().height(80.dp), contentAlignment = Alignment.Center
    ) {

        // ACTUAL NAV BAR CONTAINER
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth().size(350.dp, 55.dp).padding(horizontal = 10.dp)
                .background(
                    color = Color(204, 204, 204, 255), shape = RoundedCornerShape(50.dp)
                )
        ) {

            // ---- MEASUREMENTS ----
            val barWidth = maxWidth
            val tabWidth = barWidth / tabs.size

            val pillWidth = 80.dp     // edit size here
            val pillHeight = 40.dp

            // Calculates X position for the pill
            val indicatorOffset by animateDpAsState(
                targetValue = (tabWidth * pagerState.currentPage) + (tabWidth / 2) - (pillWidth / 2),
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessMedium
                ),
                label = "indicator"
            )

            // ---- MOVING PILL (BEHIND CONTENT) ----
            Box(
                modifier = Modifier.offset(x = indicatorOffset)
                    .align(Alignment.CenterStart)   // <-- ALWAYS CENTER VERTICALLY
                    .size(pillWidth, pillHeight).clip(RoundedCornerShape(50.dp)).background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFC20000), Color(0xFF5C0000)
                            )
                        )
                    )
                // deep red like screenshot
            )

            // ---- NAV ITEMS ----
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                tabs.forEach { tab ->

                    val isSelected = pagerState.currentPage == tab.index

                    // Each tab occupies equal width
                    Box(
                        modifier = Modifier.width(tabWidth).fillMaxHeight().clickable {
                            scope.launch {
                                pagerState.animateScrollToPage(tab.index)
                            }
                        }, contentAlignment = Alignment.Center
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            // ICON
                            Icon(
                                painter = painterResource(tab.icon),
                                contentDescription = tab.title,
                                tint = if (isSelected) Color(
                                    204, 204, 204, 255
                                ) else Color.Unspecified,
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


// home Screen
@Composable
fun CarCard(
    brand: String, model: String, modelYear: String, color: String, image: DrawableResource
) {
    val popSemi = FontFamily(Font(Res.font.poppins_semibold))
    val popMid = FontFamily(Font(Res.font.poppins_medium))

    val cardMod = Modifier.size(width = 305.dp, height = 255.dp)


    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0x39000000)),
        modifier = cardMod.shadow(
            elevation = 10.dp, shape = RoundedCornerShape(15.dp), clip = false
        ),
        shape = RoundedCornerShape(15.dp),


        ) { }//shadow
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = cardMod.clip(shape = RoundedCornerShape(15.dp)).cardGradBack(),
    ) {
        Column(
            modifier = m.fillMaxSize().padding(top = 15.dp, bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Row(
                modifier = m.fillMaxWidth().padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    painter = painterResource(Res.drawable.dots),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = m.size(20.dp)

                )
            } // top dots
            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = m.size(230.dp, 160.dp)

            ) //car image
            Row(
                modifier = m.fillMaxWidth().padding(horizontal = 20.dp),
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
            Spacer(modifier = m.padding(vertical = 2.dp))
            Row(
                modifier = m.fillMaxWidth(),
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
                        modifier = m.size(18.dp)

                    ) //car icon
                    Spacer(modifier = m.padding(horizontal = 2.dp))
                    Text(
                        model,
                        fontFamily = popSemi,
                        fontSize = 14.sp,
                        color = Color(102, 102, 102, 255)
                    )
                }
                Spacer(modifier = m.padding(horizontal = 4.dp))
                Row(
                    modifier = m
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.modelyear),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = m.size(20.dp)

                    ) //model year icon
                    Spacer(modifier = m.padding(horizontal = 2.dp))
                    Text(
                        modelYear,
                        fontFamily = popSemi,
                        fontSize = 14.sp,
                        color = Color(102, 102, 102, 255)
                    ) //model year

                }
                Spacer(modifier = m.padding(horizontal = 4.dp))
                Row(
                    modifier = m
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.color),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = m.size(20.dp)

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
fun OBDCard(onClick: () -> Unit) {
    val popSemi = FontFamily(Font(Res.font.poppins_semibold))

    val popMid = FontFamily(Font(Res.font.poppins_medium))

    Card(
        m.fillMaxWidth().height(170.dp),
        shape = RoundedCornerShape(15.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxSize().appButtonBack().padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f), contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.cuate),
                        contentDescription = null,
                        modifier = m.size(230.dp, 160.dp)

                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "OBD",
                        fontFamily = popSemi,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(217, 217, 217, 255)
                    )

                    Spacer(m.height(2.dp))

                    // More details button
                    Row(
                        m.clickable { onClick }, verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "More details",
                            fontFamily = popSemi,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(217, 217, 217, 255)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            painter = painterResource(Res.drawable.arrow_1),
                            null,
                            tint = Color(229, 174, 65, 255)

                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UpcomingMaintenance() {
    val popSemi = FontFamily(Font(Res.font.poppins_semibold))

    val popMid = FontFamily(Font(Res.font.poppins_medium))

    @Composable
    fun TimerUpcomingMaintenance(time: String) {
        val popSemi = FontFamily(Font(Res.font.poppins_semibold))

        val popMid = FontFamily(Font(Res.font.poppins_medium))
        Column(
            m.size(40.dp).border(
                shape = RoundedCornerShape(8.dp), width = 1.dp, color = Color(30, 30, 30, 110)
            ).clip(shape = RoundedCornerShape(8.dp)).background(Color(217, 217, 217, 255)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                time,
                fontFamily = popSemi,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(118, 118, 118, 255)
            )

        }

    }
    Row(
        m.appButtonBack().padding(vertical = 20.dp, horizontal = 12.dp).fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start

    )

    {
        Text(
            "Upcoming \n Maintenance ",
            fontFamily = popSemi,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(217, 217, 217, 255)
        )

        Spacer(m.padding(horizontal = 2.dp))
        TimerUpcomingMaintenance("10")
        Spacer(m.padding(horizontal = 4.dp))

        Text(
            ":",
            fontFamily = popSemi,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(217, 217, 217, 255)
        ) // two dots
        Spacer(m.padding(horizontal = 4.dp))

        TimerUpcomingMaintenance("25")
        Spacer(m.padding(horizontal = 4.dp))

        Text(
            ":",
            fontFamily = popSemi,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(217, 217, 217, 255)
        ) // two dots
        Spacer(m.padding(horizontal = 4.dp))

        TimerUpcomingMaintenance("50")


    }

}

@Composable
fun ToastMessage(message: String, state: Boolean) {
    val popSemi = FontFamily(Font(Res.font.poppins_semibold))

    Row(
        m.fillMaxWidth(0.9f).height(50.dp).clip(shape = RoundedCornerShape(8.dp))
            .background(Color(217, 217, 217, 255)).border(
                shape = RoundedCornerShape(8.dp), width = 1.dp, brush = Brush.radialGradient(
                    colors = listOf(
                        Color.Transparent,
                        if (state) Color(0, 200, 116, 255)
                        else Color(194, 0, 0, 255),
                    )
                )
            ).padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(
                if (state) Res.drawable.success
                else Res.drawable.failed
            ),

            null, tint = if (state) Color(0, 200, 116, 255)
            else Color(194, 0, 0, 255),

            modifier = m.size(25.dp)
        )
        Spacer(m.width(8.dp))
        Text(
            message,
            fontFamily = popSemi,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(30, 30, 30, 161)
        )


    }
    // need adding time

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
            path = path, color = Color.Red, style = Stroke(width = 4f)
        )
    }
}


@Composable
fun SelectDropdown(

    label: String,
    selectedValue: String?,
    options: List<String>,
    onSelect: (String) -> Unit,

    ) {

    val textFieldColors = TextFieldDefaults.colors(

        unfocusedTextColor = Color.DarkGray,
        errorTextColor = Color(194, 0, 0, 255),

        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,


        cursorColor = Color(194, 0, 0, 255),
        focusedIndicatorColor = Color(
            118, 118, 118, 255
        ),    // underline/border when focused
        unfocusedIndicatorColor = Color(
            118, 118, 118, 255
        ),  // underline/border when not focused
        errorIndicatorColor = Color(194, 0, 0, 255),
        focusedTextColor = Color(0, 0, 0, 255)


    )
    val popSemi = FontFamily(Font(Res.font.poppins_semibold))
    val popMid = FontFamily(Font(Res.font.poppins_medium))
    val scrollState = rememberScrollState()

    var expanded by remember { mutableStateOf(false) }

    Box(
        m.clickable { expanded = true }) {
        OutlinedTextField(
            modifier = m.fillMaxWidth().clickable { expanded = true }
                .size(290.dp, 55.dp),
            value = selectedValue ?: "",
            onValueChange = {},
            readOnly = true,
            placeholder = {
                Text(
                    label, fontFamily = popMid, fontSize = 16.sp, color = Color(30, 30, 30, 51)
                )
            },
            trailingIcon = {
                Icon(
                    if (expanded == false) {
                        painterResource(Res.drawable.keyboard_arrow_down)
                    } else {
                        painterResource(Res.drawable.keyboard_arrow_up)
                    }, contentDescription = "dropdown arrow", m.clickable { expanded = true })
            },
            shape = RoundedCornerShape(8.dp),
            colors = textFieldColors


        )
        DropdownMenu(
            modifier = m.verticalScroll(scrollState).size(300.dp, 135.dp)
                .background(Color(230, 230, 230, 255)),
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(modifier = m.height(40.dp), text = {
                    Text(
                        option,
                        fontFamily = popSemi,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                        color = Color(102, 104, 105, 255),
                    )
                }, onClick = {
                    onSelect(option)
                    expanded = false
                }

                )
                if (index < options.size - 1) {
                    Divider(color = Color(118, 118, 118, 128), thickness = 1.dp)
                }

            }
        }
    }

}

@Composable
fun CalenderBox(viewModel: ScheduleScreenViewModel) {
    val _state by viewModel.state.collectAsState()
    val state = _state

    val daysOfWeek = listOf("SUN", "MON", "TUE", "WEN", "THU", "FRI", "SAT")

    // Using your data structure
    val calendarDays = listOf(
        listOf(
            "28" to false,
            "29" to false,
            "30" to false,
            "1" to true,
            "2" to true,
            "3" to true,
            "4" to true
        ), listOf(
            "5" to true,
            "6" to true,
            "7" to true,
            "8" to true,
            "9" to true,
            "10" to true,
            "11" to true
        ), listOf(
            "12" to true,
            "13" to true,
            "14" to true,
            "15" to true,
            "16" to true,
            "17" to true,
            "18" to true
        ), listOf(
            "19" to true,
            "20" to true,
            "21" to true,
            "22" to true,
            "23" to true,
            "24" to true,
            "25" to true
        ), listOf(
            "26" to true,
            "27" to true,
            "28" to true,
            "29" to true,
            "30" to true,
            "1" to false,
            "2" to false
        )
    )
    val popSemi = FontFamily(Font(Res.font.poppins_semibold))
    val popMid = FontFamily(Font(Res.font.poppins_medium))
    Column(
        modifier = m.size(375.dp, 300.dp).scale(0.9f).clip(RoundedCornerShape(5.dp))
            .background(Color(207, 207, 207, 207)),
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header: Month and Year
        Row(
            modifier = m.fillMaxWidth().height(50.dp).appButtonBack(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Month Control
            CalendarArrowButton(isLeft = true) { viewModel.changeMonth(false) }

            Text(
                viewModel.months[state.currentMonthIndex],
                color = Color.White,
                fontFamily = popSemi,
                fontSize = 20.sp,
                modifier = m.padding(horizontal = 10.dp)
            )
            CalendarArrowButton(isLeft = false) { viewModel.changeMonth(true) }

            // Year Control
            CalendarArrowButton(isLeft = true) { state.currentYear-- }
            Text(
                state.currentYear.toString(),
                color = Color.White,
                fontFamily = popSemi,
                fontSize = 20.sp,
                modifier = m.padding(horizontal = 10.dp)
            )
            CalendarArrowButton(isLeft = false) { state.currentYear++ }
        }

        // Days Header (SUN, MON...)
        Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp)) {
            daysOfWeek.forEach { day ->
                Text(
                    day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp
                )
            }
        }

        // Days Grid
        calendarDays.forEach { week ->
            Row(
                modifier = m.fillMaxWidth().padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                week.forEach { (day, isCurrentMonth) ->
                    val isSelected = state.selectedDay == day && isCurrentMonth

                    Box(
                        modifier = m.size(35.dp).clip(RoundedCornerShape(8.dp))
                            // Highlight the background if selected
                            .background(if (isSelected) Color(0xFFC20000) else Color.Transparent)
                            .clickable(enabled = isCurrentMonth) {
                                viewModel.onDayClick(day)
                                state.isTimePickerVisible = true

                            },

                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = day, fontFamily = popSemi, color = when {
                                isSelected -> Color.White
                                isCurrentMonth -> Color.Black
                                else -> Color.Gray
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarArrowButton(isLeft: Boolean, onClick: () -> Unit) {
    Icon(
        painter = painterResource(Res.drawable.clander_right_arrow),
        contentDescription = null,
        tint = Color.White,
        modifier = m.size(20.dp).rotate(if (isLeft) 180f else 360f).clickable { onClick() })
}

@Composable
fun SelectDateBox(viewModel: ScheduleScreenViewModel) {

    val _state by viewModel.state.collectAsState()
    val state = _state
    val textFieldColors = TextFieldDefaults.colors(

        unfocusedTextColor = Color.DarkGray,
        errorTextColor = Color(194, 0, 0, 255),

        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,


        cursorColor = Color(194, 0, 0, 255),
        focusedIndicatorColor = Color(
            118, 118, 118, 255
        ),    // underline/border when focused
        unfocusedIndicatorColor = Color(
            118, 118, 118, 255
        ),  // underline/border when not focused
        errorIndicatorColor = Color(194, 0, 0, 255),
        focusedTextColor = Color(0, 0, 0, 255)


    )

    val popSemi = FontFamily(Font(Res.font.poppins_semibold))
    Column(
        m.fillMaxSize().background(Color(0, 0, 0, 128)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            m

                .fillMaxWidth(0.85f).fillMaxHeight(0.7f).clip(RoundedCornerShape(10.dp))
                .background(Color(217, 217, 217, 255)).padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Text(
                "Choose Available Time Slot",
                fontFamily = popSemi,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        listOf(
                            Color(194, 0, 0, 255), Color(92, 0, 0, 255)
                        )
                    ),
                ),
            ) // choose text
            Spacer(m.padding(top = 24.dp))
            Text(
                "Morning",
                fontFamily = popSemi,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        listOf(
                            Color(194, 0, 0, 255), Color(92, 0, 0, 255)
                        )
                    ),
                ),
            ) //morning text
            Spacer(m.padding(top = 4.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(viewModel.morningSlots) { slot ->
                    TimeSlotItem(
                        slot = slot, onSlotClick = { viewModel.onTimeClick(it) })
                }
            }
            Spacer(m.padding(top = 22.dp))
            Text(
                "Evening",
                fontFamily = popSemi,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        listOf(
                            Color(194, 0, 0, 255), Color(92, 0, 0, 255)
                        )
                    ),
                ),
            ) //evening text
            Spacer(m.padding(top = 4.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(viewModel.eveningSlots) { slot ->
                    TimeSlotItem(
                        slot = slot, onSlotClick = { viewModel.onTimeClick(it) })
                }
            }
            Spacer(m.padding(top = 22.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                Card(
                    onClick = {
                        val finalDate = viewModel.getFinalSelectionString()
                        if (finalDate != null) {
                            println("User selected😂😂: $finalDate")
                            // This is where you would pass it to the next screen
                            // or save it to your database
                            viewModel.closeTimePicker()
                        } else {
                            println("Please select both a day and a time💕.")
                        }

                    },
                    modifier = m

                        .fillMaxWidth(0.8f).height(45.dp).border(
                            width = 1.dp,
                            color = Color(30, 30, 30, 110),
                            shape = RoundedCornerShape(8.dp)
                        ).clip(shape = RoundedCornerShape(8.dp)).appButtonBack(),

                    colors = CardDefaults.cardColors(containerColor = Color.Transparent),

                    ) {

                    Row(
                        modifier = m.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Confirm",
                            fontFamily = popSemi,
                            fontSize = 18.sp,
                            color = Color(217, 217, 217, 255)
                        )
                    }
                }
            } //continue button


        }

    }
}

@Composable
fun TimeSlotItem(
    slot: TimeSlot, onSlotClick: (String) -> Unit
) {
    // 1. Define colors based on the slot's internal state
    val backgroundColor = if (slot.isSelected) Color(0xFF259F00) else Color.Transparent
    val textColor = if (slot.isSelected) Color.White else Color(0x6E1E1E1E).copy(alpha = 0.7f)
    val borderColor = if (slot.isSelected) Color(0xFF259F00) else Color(30, 30, 30, 51)
    val popSemi = FontFamily(Font(Res.font.poppins_semibold))

    Box(
        modifier = Modifier.fillMaxWidth()
            .height(25.dp) // Increased slightly for better padding/touch
            .clip(RoundedCornerShape(8.dp)).background(backgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
            .clickable(enabled = slot.isAvailable) { slot.time?.let { onSlotClick(it) } },
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 4.dp)
        ) {
            // 2. Icon Logic
            when {
                slot.isSelected -> {
                    Icon(
                        painter = painterResource(Res.drawable.check_time_slot),
                        contentDescription = null,
                        tint = Color.White, // Force white checkmark
                        modifier = Modifier.size(12.dp)
                    )
                }

                !slot.isAvailable -> {
                    Icon(
                        painter = painterResource(Res.drawable.x_time_slot),
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.size(12.dp)
                    )
                }

                else -> {
                    // Available circle
                    Box(
                        modifier = Modifier.size(12.dp).border(
                            1.dp, Color(30, 30, 30, 51), CircleShape
                        )
                    )
                }
            }

            Spacer(Modifier.width(4.dp))

            // 3. Time Text
            slot.time?.let {
                Text(
                    text = it,
                    fontSize = 11.sp,
                    fontFamily = popSemi,
                    color = textColor,
                    fontWeight = FontWeight.W500
                )
            }
        }
    }
}

@Composable
fun LoadingOverlay() {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.2f))
            .blur(radius = 2.dp), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Color(0xFFC40000), modifier = Modifier.size(50.dp)
        )
    }
}


@Composable
fun UsersCar(
    brand: String,
    model: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val popSemi = FontFamily(Font(Res.font.poppins_semibold))

    Row(modifier = Modifier.fillMaxWidth().height(60.dp).clickable { onClick() }
        .background(Color(204, 204, 204, 255)).padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically) {

        Box(
            m.size(50.dp) // Set the size of the circle
                .clip(CircleShape) // This makes it a circle
                .border(1.dp, Color(30, 30, 30, 51), CircleShape)
        ) {
            Image(
                painter = painterResource(Res.drawable.audi),
                contentDescription = "Car Logo",
                modifier = Modifier.size(42.dp) // Set the size of the circle
                    .align(Alignment.Center)

            )
        } // car image
        Spacer(modifier = Modifier.width(22.dp))
        Column(
            modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center
        ) {
            Text(
                brand,
                fontFamily = popSemi,
                fontSize = 20.sp,
                fontWeight = FontWeight.W500,
                color = Color(102, 102, 102, 255)
            )
            Text(
                model,
                fontFamily = popSemi,
                fontSize = 16.sp,
                fontWeight = FontWeight.W300,
                color = Color(102, 102, 102, 255)
            )

        } // car details
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(Res.drawable.check_circle),
            contentDescription = null,
            tint = if (isSelected) Color(194, 0, 0, 255) else Color.Transparent,
            modifier = m.size(20.dp)

        )


    }
}


@Composable
fun ServiceHistoryItem(
    carName: String,
    serviceName: String, // e.g., "oil changes"
    date: String,        // e.g., "8/12/2025"
    onDeleteClick: () -> Unit,
) {
    val popSemi = FontFamily(Font(Res.font.poppins_semibold))

    Spacer(m.padding(horizontal = 4.dp))
    Column(
        m
            .size(120.dp, 80.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .fillMaxSize()
            .background(Color(102, 102, 102, 36))
            .padding(horizontal = 6.dp, vertical = 4.dp)
    ) {
        Row(

            verticalAlignment = Alignment.CenterVertically

        ) {
            Box(
            m.size(28.dp) // Set the size of the circle
                .clip(CircleShape) // This makes it a circle
                .border(1.dp, Color(30, 30, 30, 51), CircleShape)
        ) {
            Image(
                painter = painterResource(Res.drawable.audi),
                contentDescription = "Car Logo",
                modifier = Modifier.size(23.dp) // Set the size of the circle
                    .align(Alignment.Center)

            )
        } // car image
            Spacer(m.padding(start = 2.dp))
            Text(
                carName,
                fontFamily = popSemi,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                color = Color(102, 102, 102, 255)
            )
        }
        Spacer(m.padding(vertical = 1.dp))

        Text(
            serviceName,
            fontFamily = popSemi,
                fontSize = 12.sp,
            fontWeight = FontWeight.W400,
            color = Color(102, 102, 102, 255)
        )
        Spacer(m.padding(vertical = 2.dp))
        Spacer(m.padding(vertical = 1.dp))


        Row(m
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                date,
                fontFamily = popSemi,
                fontSize = 11.sp,
                fontWeight = FontWeight.W300,
                color = Color(102, 102, 102, 255)
            )
            Image(
                painter = painterResource(Res.drawable.recycle_bin),
                contentDescription = "RecycleBin",
                modifier = Modifier
                    .size(15.dp)
                    .clickable(onClick = onDeleteClick)

            )
        }


    }
}



@Preview
@Composable
fun prev() {
    ServiceHistoryItem("brand", "service", "date", {})
}