package com.together.study.login.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.together.study.common.navigation.Route
import com.together.study.login.LoginScreen
import kotlinx.serialization.Serializable

fun NavController.navigateToLogin(
    navOptions: NavOptions? = null,
) = navigate(Login, navOptions)

fun NavGraphBuilder.loginGraph(
    modifier: Modifier = Modifier,
    navigateToCalendar: () -> Unit,
) {
    composable<Login> {
        LoginScreen(
            modifier = modifier,
            onDone = navigateToCalendar,
        )
    }
}

@Serializable
data object Login: Route