package com.togehter.study.studyupdate.navigation

import android.net.Uri
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
    studyName: String,
    studyIntroduce: String,
    studyCategory: String?,
    studyImage: Uri?,
    memberCount: Int?,
    isChallenge: Boolean,
    navOptions: NavOptions? = null,
) = navigate(
    StudyUpdateDone(
        studyName = studyName,
        studyIntroduce = studyIntroduce,
        studyCategory = studyCategory ?: "",
        studyImageUri = studyImage?.toString() ?: "",
        memberCount = memberCount ?: 0,
        isChallenge = isChallenge
    ),
    navOptions
)

fun NavGraphBuilder.studyUpdateGraph(
    navigateToUp: () -> Unit,
    navigateToStudyUpdateDone: (String, String, String?, Uri?, Int?, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<StudyUpdate> {
        StudyUpdateRoute(
            onBackClick = navigateToUp,
            onNextClick = navigateToStudyUpdateDone,
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
data class StudyUpdateDone(
    val studyName: String,
    val studyIntroduce: String,
    val studyCategory: String,
    val studyImageUri: String,
    val memberCount: Int,
    val isChallenge: Boolean
) : Route
