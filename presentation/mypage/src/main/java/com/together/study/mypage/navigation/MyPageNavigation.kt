package com.together.study.mypage.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.together.study.common.navigation.MainTabRoute
import com.together.study.common.navigation.Route
import com.together.study.mypage.ui.MyPageRoute
import com.together.study.mypage.ui.account.AccountSettingsRoute
import com.together.study.mypage.ui.account.DeleteAccountScreen
import kotlinx.serialization.Serializable

fun NavController.navigateToMyPage(
    navOptions: NavOptions? = null,
) = navigate(MyPage, navOptions)

fun NavController.navigateToAccountSettings(
    navOptions: NavOptions? = null,
) = navigate(AccountSettings, navOptions)

fun NavController.navigateToDeleteAccount(
    navOptions: NavOptions? = null,
) = navigate(DeleteAccount, navOptions)

fun NavGraphBuilder.myPageGraph(
    navigateToUp: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    composable<MyPage> {
        MyPageRoute(
            onBackButtonClick = navigateToUp,
            onSettingNavigate = navController::navigateToAccountSettings,
            onNoticeNavigate = {},
            onContactUsNavigate = {},
            onLeaveReviewNavigate = {},
            onTermsOfServiceNavigate = {},
            onPrivacyPolicyNavigate = {},
            modifier = modifier,
        )
    }

    composable<AccountSettings> {
        AccountSettingsRoute(
            onBackButtonClick = navigateToUp,
            onDeleteAccountNavigate = navController::navigateToDeleteAccount,
            modifier = modifier,
        )
    }

    composable<DeleteAccount> {
        DeleteAccountScreen(
            userName = "투게디짱", // 추후 변경
            onBackClick = navigateToUp,
        )
    }
}

@Serializable
data object MyPage : MainTabRoute

@Serializable
data object AccountSettings : Route

@Serializable
data object DeleteAccount : Route