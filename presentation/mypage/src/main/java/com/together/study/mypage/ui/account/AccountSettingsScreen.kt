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
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@Composable
internal fun AccountSettingsRoute(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit,
    onDeleteAccountNavigate: () -> Unit,
) {
    AccountSettingsScreen(
        modifier = modifier,
        onBackButtonClick = onBackButtonClick,
        onDeleteAccountClick = onDeleteAccountNavigate,
    )
}

@Composable
private fun AccountSettingsScreen(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit,
    onDeleteAccountClick: () -> Unit,
) {
    Column(
        modifier = Modifier
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
            userEmail = "togedy@gmail.com",
            onLogoutClick = {},
            onDeleteAccountClick = onDeleteAccountClick,
        )
    }
}

@Composable
internal fun AccountSection(
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
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

        Spacer(Modifier.width(12.dp))

        HorizontalDivider(color = TogedyTheme.colors.gray50)

        Spacer(Modifier.height(8.dp))

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
