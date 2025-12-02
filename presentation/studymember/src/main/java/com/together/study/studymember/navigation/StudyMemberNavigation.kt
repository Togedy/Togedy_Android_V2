package com.together.study.studymember.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.together.study.common.navigation.Route
import com.together.study.study.type.MemberEditType
import com.together.study.studymember.memberdetail.MemberDetailScreen
import com.together.study.studymember.settings.MemberEditScreen
import com.together.study.studymember.settings.MemberListScreen
import kotlinx.serialization.Serializable

fun NavController.navigateToMemberEditScreen(
    studyId: Long,
    type: MemberEditType,
    navOptions: NavOptions? = null,
) = navigate(MemberEdit(studyId, type), navOptions)

fun NavController.navigateToMemberListScreen(
    studyId: Long,
    navOptions: NavOptions? = null,
) = navigate(MemberList(studyId), navOptions)

fun NavController.navigateToMemberDetailScreen(
    studyId: Long,
    memberId: Long,
    navOptions: NavOptions? = null,
) = navigate(MemberDetail(studyId, memberId), navOptions)

fun NavGraphBuilder.studyMemberGraph(
    navigateToUp: () -> Unit,
    navigateToMemberSettings: (Long) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    composable<MemberEdit> {
        MemberEditScreen(
            onBackClick = navigateToUp,
            onMemberSettingsNavigate = navigateToMemberSettings,
            modifier = modifier,
        )
    }

    composable<MemberList> {
        MemberListScreen(
            onBackClick = navigateToUp,
            onMemberDetailNavigate = { studyId, memberId ->
                navController.navigateToMemberDetailScreen(studyId, memberId)
            },
            modifier = modifier,
        )
    }

    composable<MemberDetail> {
        MemberDetailScreen(
            onBackClick = navigateToUp,
            modifier = modifier,
        )
    }
}

@Serializable
data class MemberEdit(val studyId: Long, val type: MemberEditType) : Route

@Serializable
data class MemberList(val studyId: Long) : Route

@Serializable
data class MemberDetail(val studyId: Long, val memberId: Long) : Route
