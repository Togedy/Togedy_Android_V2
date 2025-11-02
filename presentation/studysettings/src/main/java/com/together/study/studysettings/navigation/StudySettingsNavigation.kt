package com.together.study.studysettings.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.together.study.common.navigation.Route
import com.together.study.studysettings.main.LeaderSettingsRoute
import com.together.study.studysettings.main.MemberSettingsRoute
import com.together.study.studysettings.subsettings.MemberCountEditScreen
import com.together.study.studysettings.subsettings.MemberEditScreen
import com.together.study.studysettings.type.MemberEditType
import kotlinx.serialization.Serializable

fun NavController.navigateToLeaderSettingsScreen(
    studyId: Long,
    navOptions: NavOptions? = null,
) = navigate(LeaderSettings(studyId), navOptions)

fun NavController.navigateToMemberSettingsScreen(
    studyId: Long,
    navOptions: NavOptions? = null,
) = navigate(MemberSettings(studyId), navOptions)

fun NavController.navigateToMemberEditScreen(
    studyId: Long,
    type: MemberEditType,
    navOptions: NavOptions? = null,
) = navigate(MemberEdit(studyId, type), navOptions)

fun NavController.navigateToMemberCountEditScreen(
    studyId: Long,
    navOptions: NavOptions? = null,
) = navigate(MemberCountEdit(studyId), navOptions)


fun NavGraphBuilder.studySettingsGraph(
    navigateToUp: () -> Unit,
    navigateToStudyMain: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    composable<LeaderSettings> {
        LeaderSettingsRoute(
            onBackClick = navigateToUp,
            onInfoClick = { /* 정보 수정 화면 */ },
            onMemberClick = { id ->
                navController.navigateToMemberEditScreen(id, MemberEditType.EDIT)
            },
            onMemberCountClick = { id ->
                navController.navigateToMemberCountEditScreen(id)
            },
            onLeaderEditClick = { id ->
                navController.navigateToMemberEditScreen(id, MemberEditType.LEADER_CHANGE)
            },
            onStudyMainNavigate = navigateToStudyMain,
            modifier = modifier,
        )
    }

    composable<MemberSettings> {
        MemberSettingsRoute(
            onBackClick = navigateToUp,
            onMemberNavigate = { id ->
                navController.navigateToMemberEditScreen(id, MemberEditType.SHOW)
            },
            onReportNavigate = { /* 추후 신고화면 연결*/ },
            onStudyMainNavigate = navigateToStudyMain,
            modifier = modifier,
        )
    }

    composable<MemberEdit> {
        MemberEditScreen(
            onBackClick = navigateToUp,
            onMemberSettingsNavigate = navController::navigateToMemberSettingsScreen,
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
data class MemberEdit(val studyId: Long, val type: MemberEditType) : Route

@Serializable
data class MemberCountEdit(val studyId: Long) : Route
