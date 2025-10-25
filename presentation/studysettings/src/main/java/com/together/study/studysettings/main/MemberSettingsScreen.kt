package com.together.study.studysettings.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.TogedyTheme

@Composable
fun MemberSettingsRoute(
    modifier: Modifier = Modifier,
    onMemberClick: () -> Unit,
    onReportClick: () -> Unit,
) {
    val memberEdit = listOf(
        Settings(title = "멤버 보기", onClick = onMemberClick),
    )
    val deleteEdit = listOf(
        Settings(title = "문제 신고하기", icon = null, onClick = onReportClick),
        Settings(title = "스터디 나가기", icon = null, isTextRed = true, onClick = { /* dialog */ })
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
            sectionTitle = "스터디 멤버관리",
            items = memberEdit,
        )

        SettingsSection(
            sectionTitle = "스터디 삭제 설정",
            items = deleteEdit,
        )
    }
}

@Preview
@Composable
private fun MemberSettingsRoutePreview() {
    TogedyTheme {
        MemberSettingsRoute(
            onMemberClick = {},
            onReportClick = {},
        )
    }
}