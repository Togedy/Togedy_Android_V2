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
    studyName: String,
    navOptions: NavOptions? = null,
) = navigate(MemberSettings(studyId, studyName), navOptions)

fun NavController.navigateToMemberEditScreen(
    studyId: Long,
    type: MemberEditType,
    studyMemberCount: Int = 0,
    studyMemberLimit: Int = 0,
    navOptions: NavOptions? = null,
) = navigate(MemberEdit(studyId, type, studyMemberCount, studyMemberLimit), navOptions)

fun NavController.navigateToMemberCountEditScreen(
    memberCount: Int,
    navOptions: NavOptions? = null,
) = navigate(MemberCountEdit(memberCount), navOptions)


fun NavGraphBuilder.studySettingsGraph(
    navigateToUp: () -> Unit,
    navigateToStudyMain: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    composable<LeaderSettings> {
        LeaderSettingsRoute(
            onBackClick = navigateToUp,
            onInfoClick = {},
            onMemberClick = { id, count, limit ->
                navController.navigateToMemberEditScreen(
                    studyId = id,
                    type = MemberEditType.EDIT,
                    studyMemberCount = count,
                    studyMemberLimit = limit,
                )
            },
            onMemberCountClick = { id, count, limit ->
                navController.navigateToMemberCountEditScreen(memberCount = count)
            },
            onLeaderEditClick = { id ->
                navController.navigateToMemberEditScreen(
                    studyId = id,
                    type = MemberEditType.LEADER_CHANGE,
                )
            },
            onStudyMainNavigate = navigateToStudyMain,
            modifier = modifier,
        )
    }

    composable<MemberSettings> {
        MemberSettingsRoute(
            onBackClick = navigateToUp,
            onMemberNavigate = { id ->
                navController.navigateToMemberEditScreen(
                    studyId = id,
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
data class LeaderSettings(val studyId: Long) : Route

@Serializable
data class MemberSettings(
    val studyId: Long,
    val studyName: String,
) : Route

@Serializable
data class MemberEdit(
    val studyId: Long,
    val type: MemberEditType,
    val studyMemberCount: Int,
    val studyMemberLimit: Int,
) : Route

@Serializable
data class MemberCountEdit(val memberCount: Int) : Route
