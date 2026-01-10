package com.together.study.planner.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.together.study.common.navigation.MainTabRoute
import com.together.study.planner.main.PlannerScreen
import kotlinx.serialization.Serializable

fun NavController.navigateToPlanner(
    navOptions: NavOptions? = null,
) = navigate(Planner)

fun NavGraphBuilder.plannerGraph(
    navigateToUp: () -> Unit,
    navigateToSharePlanner: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<Planner> {
        PlannerScreen(
            modifier = modifier,
            onShareNavigate = navigateToSharePlanner,
            onTimerNavigate = { },
            onEditSubjectNavigate = { },
        )
    }
}

@Serializable
data object Planner : MainTabRoute
