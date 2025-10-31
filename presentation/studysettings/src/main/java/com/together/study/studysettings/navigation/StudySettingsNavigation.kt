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
    studyMemberCount: Int,
    studyMemberLimit: Int,
    navOptions: NavOptions? = null,
) = navigate(LeaderSettings(studyId, studyMemberCount, studyMemberLimit), navOptions)

fun NavController.navigateToMemberSettingsScreen(
    studyId: Long,
    studyName: String,
    navOptions: NavOptions? = null,
) = navigate(MemberSettings(studyId, studyName), navOptions)

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
    modifier: Modifier = Modifier,
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
            onMemberCountClick = { count, limit ->
                navController.navigateToMemberCountEditScreen(memberCount = count)
            },
            onLeaderEditClick = {
                navController.navigateToMemberEditScreen(
                    studyId = it,
                    type = MemberEditType.LEADER_CHANGE,
                )
            },
            modifier = modifier,
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
            modifier = modifier,
        )
    }

    composable<MemberEdit> {
        MemberEditScreen(
            onBackClick = navigateToUp,
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
data class LeaderSettings(
    val studyId: Long,
    val studyMemberCount: Int,
    val studyMemberLimit: Int,
) : Route

@Serializable
data class MemberSettings(
    val studyId: Long,
    val studyName: String,
) : Route

@Serializable
data class MemberEdit(val studyId: Long, val type: MemberEditType) : Route

@Serializable
data class MemberCountEdit(val memberCount: Int) : Route
