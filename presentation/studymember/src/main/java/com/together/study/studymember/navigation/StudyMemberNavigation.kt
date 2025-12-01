package com.together.study.studymember.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.together.study.common.navigation.Route
import com.together.study.studymember.settings.MemberListScreen
import kotlinx.serialization.Serializable

fun NavController.navigateToMemberListScreen(
    studyId: Long,
    navOptions: NavOptions? = null,
) = navigate(MemberList(studyId), navOptions)

fun NavGraphBuilder.studyMemberGraph(
    navigateToUp: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
) {

    composable<MemberList> {
        MemberListScreen(
            onBackClick = navigateToUp,
            onMemberDetailNavigate = {},
            modifier = modifier,
        )
    }
}

@Serializable
data class MemberList(val studyId: Long) : Route
