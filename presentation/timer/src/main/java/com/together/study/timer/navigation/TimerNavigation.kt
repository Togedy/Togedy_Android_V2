package com.together.study.timer.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.together.study.common.navigation.Route
import com.together.study.timer.TimerRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToTimer(
    navOptions: NavOptions? = null,
) = navigate(Timer, navOptions)

fun NavGraphBuilder.timerGraph(
    navigateToUp: () -> Unit,
    onNavigateToAddSubject: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<Timer> {
        TimerRoute(
            modifier = modifier,
            onBackClick = navigateToUp,
            onNavigateToAddSubject = onNavigateToAddSubject,
        )
    }
}

@Serializable
data object Timer : Route
