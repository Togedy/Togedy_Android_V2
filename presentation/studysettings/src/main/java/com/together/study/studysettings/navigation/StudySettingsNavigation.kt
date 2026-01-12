package com.together.study.studysettings.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.together.study.common.navigation.Route
import com.together.study.common.type.study.StudyUpdateType
import com.together.study.study.type.MemberEditType
import com.together.study.studysettings.main.LeaderSettingsRoute
import com.together.study.studysettings.main.MemberSettingsRoute
import com.together.study.studysettings.subsettings.MemberCountEditScreen
import kotlinx.serialization.Serializable

fun NavController.navigateToLeaderSettingsScreen(
    studyId: Long,
    navOptions: NavOptions? = null,
) = navigate(LeaderSettings(studyId), navOptions)

fun NavController.navigateToMemberSettingsScreen(
    studyId: Long,
    navOptions: NavOptions? = null,
) = navigate(MemberSettings(studyId), navOptions)

fun NavController.navigateToMemberCountEditScreen(
    studyId: Long,
    navOptions: NavOptions? = null,
) = navigate(MemberCountEdit(studyId), navOptions)

fun NavGraphBuilder.studySettingsGraph(
    navigateToUp: () -> Unit,
    navigateToStudyMain: () -> Unit,
    navigateToStudyMemberEdit: (Long, MemberEditType) -> Unit,
    navigateToStudyUpdate: (Long, StudyUpdateType) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    composable<LeaderSettings> {
        LeaderSettingsRoute(
            onBackClick = navigateToUp,
            onInfoClick = { studyId ->
                navigateToStudyUpdate(studyId, StudyUpdateType.UPDATE)
            },
            onMemberClick = { id ->
                navigateToStudyMemberEdit(id, MemberEditType.EDIT)
            },
            onMemberCountClick = navController::navigateToMemberCountEditScreen,
            onLeaderEditClick = { id ->
                navigateToStudyMemberEdit(id, MemberEditType.LEADER_CHANGE)
            },
            onStudyMainNavigate = navigateToStudyMain,
            modifier = modifier,
        )
    }

    composable<MemberSettings> {
        MemberSettingsRoute(
            onBackClick = navigateToUp,
            onMemberNavigate = { id ->
                navigateToStudyMemberEdit(id, MemberEditType.SHOW)
            },
            onReportNavigate = { /* 추후 신고화면 연결*/ },
            onStudyMainNavigate = navigateToStudyMain,
            modifier = modifier,
        )
    }

    composable<MemberCountEdit> {
        MemberCountEditScreen(
            onBackClick = navigateToUp,
            modifier = modifier,
        )
    }
}

@Serializable
data class LeaderSettings(val studyId: Long) : Route

@Serializable
data class MemberSettings(val studyId: Long) : Route

@Serializable
data class MemberCountEdit(val studyId: Long) : Route
