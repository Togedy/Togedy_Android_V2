package com.together.study.studysettings.navigation

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
    memberCount: Int,
    navOptions: NavOptions? = null,
) = navigate(MemberCountEdit(memberCount), navOptions)


fun NavGraphBuilder.studySettingsGraph(
    navigateToUp: () -> Unit,
    navController: NavController,
) {
    composable<LeaderSettings> {
        LeaderSettingsRoute(
            onBackClick = navigateToUp,
            onInfoClick = {},
            onMemberClick = {
                navController.navigateToMemberEditScreen(
                    studyId = it,
                    type = MemberEditType.EDIT,
                )
            },
            onMemberCountClick = {
                navController.navigateToMemberCountEditScreen(memberCount = it)
            },
            onLeaderEditClick = {
                navController.navigateToMemberEditScreen(
                    studyId = it,
                    type = MemberEditType.LEADER_CHANGE,
                )
            },
        )
    }

    composable<MemberSettings> {
        MemberSettingsRoute(
            onBackClick = navigateToUp,
            onMemberNavigate = {
                navController.navigateToMemberEditScreen(
                    studyId = it,
                    type = MemberEditType.SHOW,
                )
            },
            onReportNavigate = { /* 추후 신고화면 연결*/ },
        )
    }

    composable<MemberSettings> {
        MemberEditScreen(
            onBackClick = navigateToUp,
        )
    }

    composable<MemberCountEdit> {
        MemberCountEditScreen(
            onBackClick = navigateToUp,
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
data class MemberCountEdit(val memberCount: Int) : Route

