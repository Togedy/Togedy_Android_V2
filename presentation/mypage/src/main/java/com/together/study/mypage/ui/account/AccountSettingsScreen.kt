package com.together.study.mypage.ui.account

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.together.study.common.state.UiState
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.component.loading.TogedyLoadingScreen
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.user.model.UserSettingInfo
import com.together.study.util.noRippleClickable

@Composable
internal fun AccountSettingsRoute(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit,
    onDeleteAccountNavigate: () -> Unit,
    viewModel: AccountSettingsViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState.value.uiState) {
        is UiState.Loading -> TogedyLoadingScreen()

        is UiState.Success<*> -> AccountSettingsScreen(
            userEmail = (uiState.value.uiState as UiState.Success<UserSettingInfo>).data.userEmail,
            modifier = modifier,
            onBackButtonClick = onBackButtonClick,
            onLogoutClick = viewModel::logout,
            onDeleteAccountClick = onDeleteAccountNavigate,
        )

        is UiState.Failure -> {}
        is UiState.Empty -> {}
    }

}

@Composable
private fun AccountSettingsScreen(
    userEmail: String,
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onDeleteAccountClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.gray50)
            .padding(top = 24.dp),
    ) {
        TogedyTopBar(
            title = "계정 관리",
            leftIcon = ImageVector.vectorResource(id = ic_left_chevron),
            onLeftClicked = onBackButtonClick,
            modifier = Modifier.padding(vertical = 10.dp),
        )

        AccountSection(
            userEmail = userEmail,
            onLogoutClick = onLogoutClick,
            onDeleteAccountClick = onDeleteAccountClick,
        )
    }
}

@Composable
private fun AccountSection(
    userEmail: String,
    modifier: Modifier = Modifier,
    onLogoutClick: () -> Unit,
    onDeleteAccountClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(TogedyTheme.colors.white)
            .padding(horizontal = 18.dp),
    ) {
        Text(
            text = "계정 연결",
            style = TogedyTheme.typography.title16sb,
            color = TogedyTheme.colors.gray800,
            modifier = Modifier.padding(top = 14.dp),
        )

        Spacer(Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "연결된 계정",
                style = TogedyTheme.typography.body14m,
                color = TogedyTheme.colors.gray800,
            )

            Spacer(Modifier.weight(1f))

            Text(
                text = userEmail,
                style = TogedyTheme.typography.body12m,
                color = TogedyTheme.colors.gray500,
            )

            Spacer(Modifier.width(12.dp))

            Text(
                text = "로그아웃",
                style = TogedyTheme.typography.chip10sb,
                color = TogedyTheme.colors.gray600,
                modifier = Modifier
                    .noRippleClickable(onLogoutClick)
                    .border(1.dp, TogedyTheme.colors.gray200, RoundedCornerShape(4.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
            )
        }

        HorizontalDivider(color = TogedyTheme.colors.gray50)

        Spacer(Modifier.height(10.dp))

        Text(
            text = "탈퇴하기",
            style = TogedyTheme.typography.body14m,
            color = TogedyTheme.colors.gray800,
            modifier = Modifier
                .noRippleClickable(onDeleteAccountClick)
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        )

        Spacer(Modifier.height(10.dp))
    }
}
