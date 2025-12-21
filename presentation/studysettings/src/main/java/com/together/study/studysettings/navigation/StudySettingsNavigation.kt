package com.together.study.studysettings.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.together.study.common.navigation.Route
import com.together.study.common.type.study.StudyUpdateType
import com.together.study.studysettings.main.LeaderSettingsRoute
import com.together.study.studysettings.main.MemberSettingsRoute
import com.together.study.studysettings.subsettings.MemberCountEditScreen
import com.together.study.studysettings.subsettings.MemberEditScreen
import com.together.study.studysettings.subsettings.MemberListScreen
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

fun NavController.navigateToMemberListScreen(
    studyId: Long,
    navOptions: NavOptions? = null,
) = navigate(MemberList(studyId), navOptions)


fun NavGraphBuilder.studySettingsGraph(
    navigateToUp: () -> Unit,
    navigateToStudyMain: () -> Unit,
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
            onMemberNavigate = navController::navigateToMemberListScreen,
            onReportNavigate = { /* 추후 신고화면 연결*/ },
            onStudyMainNavigate = navigateToStudyMain,
            modifier = modifier,
        )
    }

    composable<MemberEdit> {
        MemberEditScreen(
            onBackClick = navigateToUp,
            onMemberSettingsNavigate = { id ->
                navController.navigateToMemberSettingsScreen(
                    studyId = id,
                    navOptions = navOptions {
                        popUpTo(MemberSettings) { inclusive = true }
                        launchSingleTop = true
                    }
                )
            },
            modifier = modifier,
        )
    }

    composable<MemberCountEdit> {
        MemberCountEditScreen(
            onBackClick = navigateToUp,
            modifier = modifier,
        )
    }

    composable<MemberList> {
        MemberListScreen(
            onBackClick = navigateToUp,
            onMemberDetailNavigate = {},
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

@Serializable
data class MemberList(val studyId: Long) : Route
