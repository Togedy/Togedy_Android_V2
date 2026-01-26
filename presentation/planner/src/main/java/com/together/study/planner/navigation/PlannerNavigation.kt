package com.together.study.planner.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.together.study.common.navigation.MainTabRoute
import com.together.study.common.navigation.Route
import com.together.study.planner.main.PlannerScreen
import com.together.study.planner.share.PlannerShareRoute
import com.together.study.planner.subject.SubjectDetailRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToPlanner(
    navOptions: NavOptions? = null,
) = navigate(Planner)

fun NavController.navigateToSubjectDetail(
    navOptions: NavOptions? = null,
) = navigate(SubjectDetail)

fun NavController.navigateToSharePlanner(
    year: Int,
    month: Int,
    day: Int,
    navOptions: NavOptions? = null,
) = navigate(PlannerShare(year, month, day))

fun NavGraphBuilder.plannerGraph(
    navigateToUp: () -> Unit,
    navigateToSharePlanner: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    composable<Planner> {
        PlannerScreen(
            modifier = modifier,
            onShareNavigate = navController::navigateToSharePlanner,
            onTimerNavigate = { },
            onEditSubjectNavigate = navController::navigateToSubjectDetail,
        )
    }

    composable<SubjectDetail> {
        SubjectDetailRoute(
            onBackButtonClick = navigateToUp,
            modifier = modifier,
        )
    }

    composable<PlannerShare> {
        PlannerShareRoute(
            modifier = modifier,
        )
    }
}

@Serializable
data object Planner : MainTabRoute

@Serializable
data object SubjectDetail : Route

@Serializable
data class PlannerShare(
    val year: Int,
    val month: Int,
    val day: Int,
) : Route
