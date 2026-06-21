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
import com.together.study.planner.subject.SubjectEditRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToPlanner(
    navOptions: NavOptions? = null,
) = navigate(Planner, navOptions)

fun NavController.navigateToSubjectDetail(
    navOptions: NavOptions? = null,
) = navigate(SubjectDetail, navOptions)

fun NavController.navigateToSharePlanner(
    year: Int,
    month: Int,
    day: Int,
    navOptions: NavOptions? = null,
) = navigate(PlannerShare(year, month, day), navOptions)

const val SHOULD_OPEN_SUBJECT_ADD_KEY = "should_open_subject_add"

fun NavGraphBuilder.plannerGraph(
    navigateToUp: () -> Unit,
    navigateToGallery: (String) -> Unit,
    navigateToTimer: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    composable<Planner> { entry ->
        val shouldOpenSubjectAdd =
            entry.savedStateHandle.get<Boolean>(SHOULD_OPEN_SUBJECT_ADD_KEY) == true

        PlannerScreen(
            modifier = modifier,
            shouldOpenSubjectAdd = shouldOpenSubjectAdd,
            onClearSubjectAddFlag = {
                entry.savedStateHandle.remove<Boolean>(SHOULD_OPEN_SUBJECT_ADD_KEY)
            },
            onShareNavigate = navController::navigateToSharePlanner,
            onTimerNavigate = navigateToTimer,
            onEditSubjectNavigate = navController::navigateToSubjectDetail,
            onImageEditNavigate = navigateToGallery,
        )
    }

    composable<SubjectDetail> {
        SubjectEditRoute(
            onBackButtonClick = navigateToUp,
            modifier = modifier,
        )
    }

    composable<PlannerShare> {
        PlannerShareRoute(
            onBackButtonClick = navigateToUp,
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
