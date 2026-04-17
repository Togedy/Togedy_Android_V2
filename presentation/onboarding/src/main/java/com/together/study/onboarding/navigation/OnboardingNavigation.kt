package com.together.study.onboarding.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.together.study.common.navigation.Route
import com.together.study.onboarding.OnboardingRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToOnboarding(
    navOptions: NavOptions? = null,
) = navigate(Onboarding, navOptions)

fun NavGraphBuilder.onboardingGraph(
    modifier: Modifier = Modifier,
    navigateToCalendar: () -> Unit,
) {
    composable<Onboarding> {
        OnboardingRoute(
            onNavigateToCalendar = navigateToCalendar,
            modifier = modifier,
        )
    }
}

@Serializable
data object Onboarding : Route
