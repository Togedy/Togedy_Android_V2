package com.together.study.study.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.together.study.common.navigation.MainTabRoute
import com.together.study.study.main.StudyMainRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToStudy(
    navOptions: NavOptions? = null,
) = navigate(Study, navOptions)

fun NavGraphBuilder.studyGraph(
    navigateToUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<Study> {
        StudyMainRoute(
            modifier = modifier,
        )
    }
}

@Serializable
data object Study : MainTabRoute