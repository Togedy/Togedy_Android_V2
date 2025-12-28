package com.together.study.studyupdate.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import com.together.study.common.navigation.Route
import com.together.study.study.navigation.Study
import com.together.study.study.navigation.navigateToStudy
import com.together.study.studyupdate.StudyUpdateDoneRoute
import com.together.study.studyupdate.StudyUpdateRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToStudyUpdate(
    isChallenge: Boolean = false,
    studyId: Long = 0,
    navOptions: NavOptions? = null,
) = navigate(StudyUpdate(studyId, isChallenge), navOptions)

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
        selectedStudyTime = selectedStudyTime
    ),
    navOptions
)

fun NavGraphBuilder.studyUpdateGraph(
    navigateToUp: () -> Unit,
    navigateToStudyUpdateDone: (Long, String, String, String?, String?, String, Int?, Boolean, String) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    composable<StudyUpdate> {
        StudyUpdateRoute(
            onBackClick = navigateToUp,
            onNextClick = { studyId, studyName, studyIntroduce, studyCategory, studyImageUri, studyPassword, memberCount, isChallenge, selectedStudyTime ->
                navigateToStudyUpdateDone(
                    studyId,
                    studyName,
                    studyIntroduce,
                    studyCategory,
                    studyImageUri,
                    studyPassword,
                    memberCount,
                    isChallenge,
                    selectedStudyTime
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
                navController.navigateToStudy(
                    navOptions = navOptions {
                        popUpTo(Study) { inclusive = true }
                        launchSingleTop = true
                    }
                )
            },
            onStartClick = {
                navController.navigateToStudy(
                    navOptions = navOptions {
                        popUpTo(Study) { inclusive = true }
                        launchSingleTop = true
                    }
                )
            },
            studyName = route.studyName,
            studyIntroduce = route.studyIntroduce,
            studyCategory = route.studyCategory,
            studyImageUri = route.studyImageUri,
            studyPassword = route.studyPassword,
            memberCount = route.memberCount,
            isChallenge = route.isChallenge,
            selectedStudyTime = route.selectedStudyTime,
            modifier = modifier
        )
    }
}

@Serializable
data class StudyUpdate(val studyId: Long, val isChallenge: Boolean = false) : Route

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
    val selectedStudyTime: String = "FIVE_HOURS"
) : Route
