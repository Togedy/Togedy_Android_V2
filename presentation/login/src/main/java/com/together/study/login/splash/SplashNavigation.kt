package com.together.study.login.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.together.study.common.navigation.Route
import kotlinx.serialization.Serializable

fun NavGraphBuilder.splashGraph(
    onNavigateToLogin: () -> Unit,
    onNavigateToCalendar: () -> Unit,
    onNavigateToStudy: (Long) -> Unit,
) {
    composable<Splash> {
        SplashRoute(
            onNavigateToLogin = onNavigateToLogin,
            onNavigateToCalendar = onNavigateToCalendar,
            onNavigateToStudy = onNavigateToStudy,
        )
    }
}

@Serializable
data object Splash : Route
