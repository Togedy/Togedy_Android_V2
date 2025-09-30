package com.together.study.study.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.together.study.common.navigation.MainTabRoute
import com.together.study.common.navigation.Route
import com.together.study.study.main.StudyMainRoute
import com.together.study.study.search.StudySearchRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToStudy(
    navOptions: NavOptions? = null,
) = navigate(Study, navOptions)

fun NavController.navigateToStudySearch(
    navOptions: NavOptions? = null,
) = navigate(StudySearch, navOptions)

fun NavGraphBuilder.studyGraph(
    navigateToUp: () -> Unit,
    navigateToStudySearch: () -> Unit,
    navigateToStudyDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<Study> {
        StudyMainRoute(
            onStudySearchNavigate = navigateToStudySearch,
            onStudyDetailNavigate = navigateToStudyDetail,
            modifier = modifier,
        )
    }

    composable<StudySearch> {
        StudySearchRoute(
            onBackClick = navigateToUp,
            onStudyDetailNavigate = navigateToStudyDetail,
            modifier = modifier,
        )
    }
}

@Serializable
data object Study : MainTabRoute

@Serializable
data object StudySearch : Route