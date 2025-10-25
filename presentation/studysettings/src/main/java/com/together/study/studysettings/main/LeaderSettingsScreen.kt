package com.together.study.studysettings.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.studysettings.component.SettingsItem

@Composable
fun LeaderSettingsRoute(
    modifier: Modifier = Modifier,
    onInfoClick: () -> Unit,
    onMemberClick: () -> Unit,
    onMemberCountClick: () -> Unit,
    onLeaderEditClick: () -> Unit,
) {
    val studyEdit = listOf(
        Settings("계정 센터", "비밀번호, 배경이미지, 스터디 태그 변경", onClick = onInfoClick)
    )
    val memberEdit = listOf(
        Settings(title = "멤버관리", onClick = onMemberClick),
        Settings(title = "스터디 인원 수 설정", onClick = onMemberCountClick)
    )
    val deleteEdit = listOf(
        Settings(title = "방장위임", onClick = onLeaderEditClick),
        Settings(title = "스터디 삭제하기", icon = null, isTextRed = true, onClick = { /* dialog */ })
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.white)
            .padding(top = 22.dp),
    ) {
        TogedyTopBar(
            title = "스터디 설정",
            leftIcon = ImageVector.vectorResource(id = ic_left_chevron),
            modifier = Modifier.padding(bottom = 4.dp),
        )

        Spacer(Modifier.height(8.dp))

        SettingsSection(
            sectionTitle = "스터디 수정",
            items = studyEdit,
        )

        SettingsSection(
            sectionTitle = "스터디 멤버관리",
            items = memberEdit,
        )

        SettingsSection(
            sectionTitle = "스터디 삭제 설정",
            items = deleteEdit,
        )
    }
}

@Composable
fun SettingsSection(
    sectionTitle: String,
    items: List<Settings>,
    isEndDividerNeeded: Boolean = true,
    modifier: Modifier = Modifier,
    horizontalPaddingModifier: Modifier = Modifier.padding(horizontal = 20.dp),
) {
    Column(
        modifier = modifier,
    ) {
        Spacer(Modifier.height(16.dp))

        Text(
            text = sectionTitle,
            style = TogedyTheme.typography.body10m,
            color = TogedyTheme.colors.gray500,
            modifier = horizontalPaddingModifier,
        )

        Spacer(Modifier.height(12.dp))

        items.forEach { item ->
            SettingsItem(
                title = item.title,
                subtitle = item.subtitle,
                icon = item.icon,
                textColor = if (item.isTextRed) TogedyTheme.colors.red else TogedyTheme.colors.gray700,
                onItemClick = { item.onClick() },
                modifier = horizontalPaddingModifier,
            )

            Spacer(Modifier.height(12.dp))
        }

        Spacer(Modifier.height(4.dp))

        if (isEndDividerNeeded) {
            HorizontalDivider(thickness = 8.dp, color = TogedyTheme.colors.gray50)
        }
    }
}

@Preview
@Composable
private fun LeaderSettingsRoutePreview() {
    TogedyTheme {
        LeaderSettingsRoute(
            onInfoClick = {},
            onMemberClick = {},
            onMemberCountClick = {},
            onLeaderEditClick = {},
        )
    }
}
