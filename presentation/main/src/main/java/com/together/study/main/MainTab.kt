package com.together.study.main

import androidx.compose.runtime.Composable
import com.together.study.calendar.maincalendar.navigation.Calendar
import com.together.study.common.navigation.MainTabRoute
import com.together.study.common.navigation.Route
import com.together.study.designsystem.R.drawable.ic_ai
import com.together.study.designsystem.R.drawable.ic_calendar
import com.together.study.designsystem.R.drawable.ic_my
import com.together.study.designsystem.R.drawable.ic_planner
import com.together.study.designsystem.R.drawable.ic_study
import kotlinx.serialization.Serializable

enum class MainTab(
    val icon: Int,
    val contentDescription: String,
    val route: MainTabRoute,
) {
    CALENDAR(
        icon = ic_calendar,
        contentDescription = "캘린더",
        route = Calendar,
    ),
    DUMMY2(
        icon = ic_ai,
        contentDescription = "입시GPT",
        route = Dummy,
    ),
    DUMMY3(
        icon = ic_planner,
        contentDescription = "플래너",
        route = Dummy,
    ),
    DUMMY4(
        icon = ic_study,
        contentDescription = "스터디",
        route = Dummy,
    ),
    DUMMY5(
        icon = ic_my,
        contentDescription = "MY",
        route = Dummy,
    );


    companion object {
        @Composable
        fun find(predicate: @Composable (MainTabRoute) -> Boolean): MainTab? {
            return entries.find { predicate(it.route) }
        }

        @Composable
        fun contains(predicate: @Composable (Route) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }
    }
}

// TODO: 더미 추가 후 삭제
@Serializable
data object Dummy : MainTabRoute
