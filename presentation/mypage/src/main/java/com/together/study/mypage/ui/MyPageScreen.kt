package com.together.study.mypage.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.together.study.common.state.UiState
import com.together.study.designsystem.R.drawable.ic_settings_24
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.mypage.component.MenuList
import com.together.study.mypage.component.UserProfile
import com.together.study.mypage.component.UserStudyInfoSection
import com.together.study.mypage.type.LegalMenu
import com.together.study.mypage.type.SupportMenu
import com.together.study.user.model.UserInfo
import com.together.study.util.noRippleClickable


@Composable
internal fun MyPageRoute(
    onBackButtonClick: () -> Unit,
    onSettingNavigate: () -> Unit,
    onProfileEditNavigate: () -> Unit,
    onNoticeNavigate: () -> Unit,
    onContactUsNavigate: () -> Unit,
    onLeaveReviewNavigate: () -> Unit,
    onTermsOfServiceNavigate: () -> Unit,
    onPrivacyPolicyNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState.value.userInfoState) {
        is UiState.Loading -> {}
        is UiState.Success ->
            MyPageScreen(
                userInfo = (uiState.value.userInfoState as UiState.Success<UserInfo>).data,
                modifier = modifier,
                onSettingClick = onSettingNavigate,
                onEditProfileClick = onProfileEditNavigate,
                onNoticeClick = onNoticeNavigate,
                onContactUsClick = onContactUsNavigate,
                onLeaveReviewClick = onLeaveReviewNavigate,
                onTermsOfServiceClick = onTermsOfServiceNavigate,
                onPrivacyPolicyClick = onPrivacyPolicyNavigate,
            )

        is UiState.Failure -> {}
        is UiState.Empty -> {}
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MyPageScreen(
    userInfo: UserInfo,
    modifier: Modifier = Modifier,
    onSettingClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onNoticeClick: () -> Unit,
    onContactUsClick: () -> Unit,
    onLeaveReviewClick: () -> Unit,
    onTermsOfServiceClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.gray50)
            .padding(horizontal = 14.dp),
    ) {
        stickyHeader {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TogedyTheme.colors.gray50)
                    .padding(top = 14.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "프로필",
                    style = TogedyTheme.typography.title18b,
                    color = TogedyTheme.colors.gray800,
                )

                Icon(
                    imageVector = ImageVector.vectorResource(ic_settings_24),
                    contentDescription = "설정",
                    modifier = Modifier.noRippleClickable(onSettingClick),
                )
            }
        }

        item {
            Spacer(Modifier.height(10.dp))

            UserProfile(
                userName = userInfo.userName,
                userEmail = userInfo.userEmail,
                userProfileImageUrl = userInfo.userProfileImageUrl,
                totalStudyTime = userInfo.totalStudyTime,
                attendanceStreak = userInfo.attendanceStreak,
                onEditProfileClick = onEditProfileClick,
            )

            Spacer(Modifier.height(12.dp))

            UserStudyInfoSection(
                studies = userInfo.studies,
            )
        }

        item {
            MenuList(
                title = "고객 지원",
                items = SupportMenu.entries,
                onClick = { menu ->
                    when (menu) {
                        SupportMenu.NOTICE -> onNoticeClick()
                        SupportMenu.CONTACT_US -> onContactUsClick()
                        SupportMenu.LEAVE_REVIEW -> onLeaveReviewClick()
                    }
                }
            )
        }

        item {
            MenuList(
                title = "앱 이용",
                items = LegalMenu.entries,
                onClick = { menu ->
                    when (menu) {
                        LegalMenu.TERMS_OF_SERVICE -> onTermsOfServiceClick()
                        LegalMenu.PRIVACY_POLICY -> onPrivacyPolicyClick()
                    }
                }
            )

            Spacer(Modifier.height(14.dp))
        }
    }
}

@Preview
@Composable
private fun MyPageRoutePreview() {
    TogedyTheme {
        MyPageScreen(
            userInfo = UserInfo(
                userName = "유저입니당",
                userEmail = "user@gmail.com",
                userProfileImageUrl = "http://~~",
                totalStudyTime = "100:00:00",
                attendanceStreak = 4,
                studies = listOf(),
            ),
            onSettingClick = {},
            onEditProfileClick = {},
            onNoticeClick = {},
            onContactUsClick = {},
            onLeaveReviewClick = {},
            onTermsOfServiceClick = {},
            onPrivacyPolicyClick = {},
        )
    }
}
