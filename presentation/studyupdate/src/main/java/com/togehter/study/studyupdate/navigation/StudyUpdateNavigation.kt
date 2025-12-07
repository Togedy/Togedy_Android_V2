package com.togehter.study.studyupdate.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.togehter.study.studyupdate.StudyUpdateDoneRoute
import com.togehter.study.studyupdate.StudyUpdateRoute
import com.together.study.common.navigation.Route
import kotlinx.serialization.Serializable

fun NavController.navigateToStudyUpdate(
    isChallenge: Boolean = false,
    studyId: Long = 0,
    navOptions: NavOptions? = null,
) = navigate(StudyUpdate(studyId, isChallenge), navOptions)

fun NavController.navigateToStudyUpdateDone(
    studyId: Long = 0L,
    navOptions: NavOptions? = null,
) = navigate(StudyUpdateDone(studyId), navOptions)

fun NavGraphBuilder.studyUpdateGraph(
    navigateToUp: () -> Unit,
    navigateToStudyUpdateDone: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<StudyUpdate> {
        StudyUpdateRoute(
            onBackClick = navigateToUp,
            onNextClick = { studyId ->
                navigateToStudyUpdateDone(studyId)
            },
            modifier = modifier
        )
    }

    composable<StudyUpdateDone> {
        StudyUpdateDoneRoute(
            onBackClick = navigateToUp,
            modifier = modifier
        )
    }
}

@Serializable
data class StudyUpdate(val studyId: Long, val isChallenge: Boolean = false) : Route

@Serializable
data class StudyUpdateDone(val studyId: Long) : Route
