package com.together.study.calendar.maincalendar.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.together.study.calendar.maincalendar.CalendarRoute
import com.together.study.common.navigation.MainTabRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToCalendar(
    navOptions: NavOptions? = null,
) = navigate(Calendar, navOptions)

fun NavGraphBuilder.calendarGraph(
    navigateToUnivSearch: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<Calendar> {
        CalendarRoute(
            onSearchBoxClick = navigateToUnivSearch,
            modifier = modifier,
        )
    }
}

@Serializable
data object Calendar : MainTabRoute
