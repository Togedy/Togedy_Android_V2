package com.together.study.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.together.study.common.navigation.MainTabRoute
import com.together.study.common.navigation.Route
import kotlinx.serialization.Serializable

// TODO: 구현 시작 시 수정
enum class MainTab(
    val icon: ImageVector,
    val contentDescription: String,
    val route: MainTabRoute,
) {
    CALENDAR(
        icon = Icons.Filled.Home,
        contentDescription = "캘린더",
        route = CalendarRoute,
    ),
    DUMMY2(
        icon = Icons.Filled.Search,
        contentDescription = "더미2",
        route = Dummy,
    ),
    DUMMY3(
        icon = Icons.Filled.Home,
        contentDescription = "더미3",
        route = Dummy,
    ),
    DUMMY4(
        icon = Icons.Filled.Home,
        contentDescription = "더미4",
        route = Dummy,
    ),
    DUMMY5(
        icon = Icons.Filled.Home,
        contentDescription = "더미5",
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
data object Dummy: MainTabRoute

@Serializable
data object CalendarRoute : MainTabRoute
