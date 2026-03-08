package com.together.study.mypage.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.together.study.common.navigation.MainTabRoute
import com.together.study.mypage.MyPageRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToMyPage(
    navOptions: NavOptions? = null,
) = navigate(MyPage, navOptions)

fun NavGraphBuilder.myPageGraph(
    navigateToUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<MyPage> {
        MyPageRoute(
            onBackButtonClick = navigateToUp,
            onSettingNavigate = {},
            onNoticeNavigate = {},
            onContactUsNavigate = {},
            onLeaveReviewNavigate = {},
            onTermsOfServiceNavigate = {},
            onPrivacyPolicyNavigate = {},
            modifier = modifier,
        )
    }
}

@Serializable
data object MyPage : MainTabRoute
