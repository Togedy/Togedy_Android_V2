package com.together.study.login.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.together.study.common.navigation.Route
import com.together.study.login.LoginRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToLogin(
    navOptions: NavOptions? = null,
) = navigate(Login, navOptions)

fun NavGraphBuilder.loginGraph(
    modifier: Modifier = Modifier,
    navigateToCalendar: () -> Unit,
    navigateToOnboarding: () -> Unit,
) {
    composable<Login> {
        LoginRoute(
            onNavigateToCalendar = navigateToCalendar,
            onNavigateToOnboarding = navigateToOnboarding,
            modifier = modifier,
        )
    }
}

@Serializable
data object Login: Route