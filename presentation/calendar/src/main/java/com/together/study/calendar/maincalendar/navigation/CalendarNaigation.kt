package com.together.study.calendar.maincalendar.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.together.study.calendar.category.CategoryDetailRoute
import com.together.study.calendar.maincalendar.CalendarRoute
import com.together.study.common.navigation.MainTabRoute
import com.together.study.common.navigation.Route
import kotlinx.serialization.Serializable

fun NavController.navigateToCalendar(
    navOptions: NavOptions? = null,
) = navigate(Calendar, navOptions)

fun NavController.navigateToCategoryDetail(
    navOptions: NavOptions? = null,
) = navigate(CategoryDetail, navOptions)

fun NavGraphBuilder.calendarGraph(
    navigateToUp: () -> Unit,
    navigateToUnivSearch: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<Calendar> {
        CalendarRoute(
            onSearchBoxClick = navigateToUnivSearch,
            modifier = modifier,
        )
    }

    composable<CategoryDetail> {
        CategoryDetailRoute(
            onBackButtonClick = navigateToUp,
            modifier = modifier,
        )
    }
}

@Serializable
data object Calendar : MainTabRoute

@Serializable
data object CategoryDetail : Route
