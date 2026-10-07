package com.example.carware.util.navBar

import carware.composeapp.generated.resources.Res
import carware.composeapp.generated.resources.history
import carware.composeapp.generated.resources.home
import carware.composeapp.generated.resources.schedule
import carware.composeapp.generated.resources.setting__1__6
import com.example.carware.core.navigation.HistoryScreen
import com.example.carware.core.navigation.HomeScreen
import com.example.carware.core.navigation.ScheduleScreen
import com.example.carware.core.navigation.SettingsScreen

val bottomTabs = listOf(
    TabItem(
        route = HomeScreen::class,
        title = "Home",
        icon = Res.drawable.home,
        index = 0
    ),
    TabItem(
        route = ScheduleScreen::class,
        title = "Schedule",
        icon = Res.drawable.schedule,
        index = 1
    ),
    TabItem(
        route = HistoryScreen::class,
        title = "History",
        icon = Res.drawable.history,
        index = 2
    ),
    TabItem(
        route = SettingsScreen::class,
        title = "Setting",
        icon = Res.drawable.setting__1__6,
        index = 3
    )
)