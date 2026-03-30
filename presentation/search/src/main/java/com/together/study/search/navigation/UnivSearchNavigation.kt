package com.together.study.search.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.together.study.common.navigation.Route
import com.together.study.search.SearchRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToUnivSearch(
    navOptions: NavOptions? = null,
) = navigate(UnivSearch, navOptions)

fun NavGraphBuilder.univSearchGraph(
    navigateUp: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    composable<UnivSearch> {
        SearchRoute(
            modifier = modifier,
            onBackButtonClicked = navigateUp,
            )
    }
}

@Serializable
data object UnivSearch : Route
