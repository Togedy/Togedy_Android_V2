package com.together.study.main

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import com.together.study.calendar.maincalendar.navigation.Calendar
import com.together.study.common.navigation.MainTabRoute
import com.together.study.common.navigation.Route
import com.together.study.designsystem.R.drawable.ic_ai
import com.together.study.designsystem.R.drawable.ic_ai_selected
import com.together.study.designsystem.R.drawable.ic_calendar
import com.together.study.designsystem.R.drawable.ic_calendar_selected
import com.together.study.designsystem.R.drawable.ic_my
import com.together.study.designsystem.R.drawable.ic_my_selected
import com.together.study.designsystem.R.drawable.ic_planner
import com.together.study.designsystem.R.drawable.ic_planner_selected
import com.together.study.designsystem.R.drawable.ic_study
import com.together.study.designsystem.R.drawable.ic_study_selected
import kotlinx.serialization.Serializable

enum class MainTab(
    @DrawableRes val defaultIcon: Int,
    @DrawableRes val selectedIcon: Int,
    val contentDescription: String,
    val route: MainTabRoute,
) {
    CALENDAR(
        defaultIcon = ic_calendar,
        selectedIcon = ic_calendar_selected,
        contentDescription = "캘린더",
        route = Calendar,
    ),
    DUMMY2(
        defaultIcon = ic_ai,
        selectedIcon = ic_ai_selected,
        contentDescription = "입시GPT",
        route = Dummy,
    ),
    DUMMY3(
        defaultIcon = ic_planner,
        selectedIcon = ic_planner_selected,
        contentDescription = "플래너",
        route = Dummy,
    ),
    DUMMY4(
        defaultIcon = ic_study,
        selectedIcon = ic_study_selected,
        contentDescription = "스터디",
        route = Dummy,
    ),
    DUMMY5(
        defaultIcon = ic_my,
        selectedIcon = ic_my_selected,
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
