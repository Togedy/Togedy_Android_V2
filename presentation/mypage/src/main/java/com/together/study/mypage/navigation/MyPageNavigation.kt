package com.together.study.mypage.navigation

import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.together.study.common.navigation.MainTabRoute
import com.together.study.common.navigation.Route
import com.together.study.mypage.ui.MyPageRoute
import com.together.study.mypage.ui.account.AccountSettingsRoute
import com.together.study.mypage.ui.account.DeleteAccountScreen
import com.together.study.mypage.ui.account.ProfileEditRoute
import com.together.study.mypage.ui.settings.FeedbackRoute
import com.together.study.mypage.ui.settings.NoticeDetailRoute
import com.together.study.mypage.ui.settings.NoticeMainRoute
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

fun NavController.navigateToProfileEdit(
    navOptions: NavOptions? = null,
) = navigate(ProfileEdit, navOptions)

fun NavController.navigateToNoticeMain(
    navOptions: NavOptions? = null,
) = navigate(NoticeMain, navOptions)

fun NavController.navigateToNoticeDetail(
    noticeId: Long,
    navOptions: NavOptions? = null,
) = navigate(NoticeDetail(noticeId), navOptions)

fun NavController.navigateToFeedback(
    navOptions: NavOptions? = null,
) = navigate(Feedback, navOptions)

fun NavGraphBuilder.myPageGraph(
    navigateToUp: () -> Unit,
    navigateToCreateStudy: () -> Unit,
    navigateToStudyDetail: (Long) -> Unit,
    navigateToStudy: () -> Unit,
    navigateToGallery: () -> Unit,
    navigateToLogin: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    composable<MyPage> {
        MyPageRoute(
            onSettingNavigate = navController::navigateToAccountSettings,
            onProfileEditNavigate = navController::navigateToProfileEdit,
            onCreateStudyNavigate = navigateToCreateStudy,
            onStudyDetailNavigate = navigateToStudyDetail,
            onStudyMainNavigate = navigateToStudy,
            onNoticeNavigate = navController::navigateToNoticeMain,
            onContactUsNavigate = navController::navigateToFeedback,
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
            onLogoutNavigate = navigateToLogin,
            modifier = modifier,
        )
    }

    composable<DeleteAccount> {
        DeleteAccountScreen(
            userName = "투게디짱", // 추후 변경
            onBackClick = navigateToUp,
            modifier = modifier,
        )
    }

    composable<ProfileEdit> { backStackEntry ->
        val croppedImagePath by backStackEntry.savedStateHandle
            .getStateFlow<String?>(CROPPED_IMAGE_PATH_KEY, null)
            .collectAsStateWithLifecycle()

        ProfileEditRoute(
            onBackClick = navigateToUp,
            onGalleryNavigate = navigateToGallery,
            croppedImagePath = croppedImagePath,
            onCroppedImageConsumed = {
                backStackEntry.savedStateHandle.remove<String>(CROPPED_IMAGE_PATH_KEY)
            },
            modifier = modifier,
        )
    }

    composable<NoticeMain> {
        NoticeMainRoute(
            onBackButtonClick = navigateToUp,
            onNoticeDetailNavigate = navController::navigateToNoticeDetail,
            modifier = modifier,
        )
    }

    composable<NoticeDetail> {
        NoticeDetailRoute(
            onBackButtonClick = navigateToUp,
            modifier = modifier,
        )
    }

    composable<Feedback> {
        FeedbackRoute(
            onBackButtonClick = navigateToUp,
            modifier = modifier,
        )
    }
}

@Serializable
data object MyPage : MainTabRoute

@Serializable
data object AccountSettings : Route

@Serializable
data object DeleteAccount : Route

@Serializable
data object ProfileEdit : Route

@Serializable
data object NoticeMain : Route

@Serializable
data class NoticeDetail(
    val noticeId: Long,
) : Route

@Serializable
data object Feedback : Route

const val CROPPED_IMAGE_PATH_KEY = "croppedImagePath"