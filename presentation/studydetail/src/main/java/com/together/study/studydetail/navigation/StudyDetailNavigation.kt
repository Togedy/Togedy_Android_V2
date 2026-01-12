package com.together.study.studydetail.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.together.study.common.navigation.Route
import com.together.study.study.type.StudyRole
import com.together.study.studydetail.detailmain.StudyDetailRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToStudyDetail(
    studyId: Long,
    navOptions: NavOptions? = null,
) = navigate(StudyDetail(studyId), navOptions)

fun NavGraphBuilder.studyDetailGraph(
    navigateToUp: () -> Unit,
    navigateToStudySettings: (Long, StudyRole) -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<StudyDetail> {
        StudyDetailRoute(
            onBackClick = navigateToUp,
            onSettingsNavigate = navigateToStudySettings,
            modifier = modifier,
        )
    }
}

@Serializable
data class StudyDetail(val studyId: Long) : Route
