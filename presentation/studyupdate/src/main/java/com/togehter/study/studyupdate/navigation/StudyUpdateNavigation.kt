package com.togehter.study.studyupdate.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.togehter.study.studyupdate.StudyUpdateDoneRoute
import com.togehter.study.studyupdate.StudyUpdateRoute
import com.together.study.common.navigation.Route
import com.together.study.common.type.study.StudyUpdateType
import kotlinx.serialization.Serializable

fun NavController.navigateToStudyUpdate(
    isChallenge: Boolean = false,
    studyId: Long = 0,
    updateType: StudyUpdateType = StudyUpdateType.CREATE,
    navOptions: NavOptions? = null,
) = navigate(StudyUpdate(studyId, isChallenge, updateType), navOptions)

fun NavController.navigateToStudyUpdateDone(
    studyId: Long = 0L,
    studyName: String = "",
    studyIntroduce: String = "",
    studyCategory: String? = null,
    studyImageUri: String? = null,
    studyPassword: String = "",
    memberCount: Int? = null,
    isChallenge: Boolean = false,
    selectedStudyTime: String = "FIVE_HOURS",
    updateType: StudyUpdateType = StudyUpdateType.CREATE,
    navOptions: NavOptions? = null,
) = navigate(
    StudyUpdateDone(
        studyId = studyId,
        studyName = studyName,
        studyIntroduce = studyIntroduce,
        studyCategory = studyCategory,
        studyImageUri = studyImageUri,
        studyPassword = studyPassword,
        memberCount = memberCount,
        isChallenge = isChallenge,
        selectedStudyTime = selectedStudyTime,
        updateType = updateType
    ),
    navOptions
)

fun NavGraphBuilder.studyUpdateGraph(
    navigateToUp: () -> Unit,
    navigateToStudyUpdateDone: (Long, String, String, String?, String?, String, Int?, Boolean, String, StudyUpdateType) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    composable<StudyUpdate> { backStackEntry ->
        StudyUpdateRoute(
            onBackClick = navigateToUp,
            onNextClick = { studyId, studyName, studyIntroduce, studyCategory, studyImageUri, studyPassword, memberCount, isChallenge, selectedStudyTime, updateType ->
                navigateToStudyUpdateDone(
                    studyId,
                    studyName,
                    studyIntroduce,
                    studyCategory,
                    studyImageUri,
                    studyPassword,
                    memberCount,
                    isChallenge,
                    selectedStudyTime,
                    updateType
                )
            },
            modifier = modifier
        )
    }

    composable<StudyUpdateDone> { backStackEntry ->
        val route = backStackEntry.toRoute<StudyUpdateDone>()
        StudyUpdateDoneRoute(
            onBackClick = navigateToUp,
            onEditClick = {
                navController.popBackStack()
            },
            onStartClick = {
                navController.popBackStack()
                navController.popBackStack()
            },
            studyName = route.studyName,
            studyIntroduce = route.studyIntroduce,
            studyCategory = route.studyCategory,
            studyImageUri = route.studyImageUri,
            studyPassword = route.studyPassword,
            memberCount = route.memberCount,
            isChallenge = route.isChallenge,
            selectedStudyTime = route.selectedStudyTime,
            updateType = route.updateType,
            modifier = modifier
        )
    }
}

@Serializable
data class StudyUpdate(
    val studyId: Long,
    val isChallenge: Boolean = false,
    val updateType: StudyUpdateType = StudyUpdateType.CREATE
) : Route

@Serializable
data class StudyUpdateDone(
    val studyId: Long,
    val studyName: String = "",
    val studyIntroduce: String = "",
    val studyCategory: String? = null,
    val studyImageUri: String? = null,
    val studyPassword: String = "",
    val memberCount: Int? = null,
    val isChallenge: Boolean = false,
    val selectedStudyTime: String = "FIVE_HOURS",
    val updateType: StudyUpdateType = StudyUpdateType.CREATE
) : Route
