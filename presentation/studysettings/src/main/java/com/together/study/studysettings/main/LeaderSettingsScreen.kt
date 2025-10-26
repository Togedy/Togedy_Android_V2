package com.together.study.studysettings.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.component.dialog.TogedyBasicDialog
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.studysettings.component.SettingsSection

@Composable
fun LeaderSettingsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    onInfoClick: () -> Unit,
    onMemberClick: () -> Unit,
    onMemberCountClick: () -> Unit,
    onLeaderEditClick: () -> Unit,
) {
    var isDeleteDialogVisible by remember { mutableStateOf(false) }

    val studyEdit = listOf(
        Settings("계정 센터", "비밀번호, 배경이미지, 스터디 태그 변경", onClick = onInfoClick)
    )
    val memberEdit = listOf(
        Settings(title = "멤버관리", onClick = onMemberClick),
        Settings(title = "스터디 인원 수 설정", onClick = onMemberCountClick)
    )
    val deleteEdit = listOf(
        Settings(title = "방장위임", onClick = onLeaderEditClick),
        Settings(
            title = "스터디 삭제하기",
            icon = null,
            isTextRed = true,
            onClick = { isDeleteDialogVisible = true }
        ),
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
            onLeftClicked = onBackClick,
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

    if (isDeleteDialogVisible) {
        TogedyBasicDialog(
            title = "스터디삭제 안내",
            subTitle = {
                Text(
                    text = buildAnnotatedString {
                        append("더이상 스터디를 운영하지 않으려면 ")
                        withStyle(style = TogedyTheme.typography.body14b.toSpanStyle()) { append("방장을 위임") }
                        append("할 수도 있어요.\n이대로 삭제하시겠어요?")
                    },
                    style = TogedyTheme.typography.body14m,
                    color = TogedyTheme.colors.gray900,
                    textAlign = TextAlign.Center,
                )
            },
            buttonText = "삭제하기",
            buttonColor = TogedyTheme.colors.red,
            onDismissRequest = { isDeleteDialogVisible = false },
            onButtonClick = { /*삭제 api */ }
        )
    }
}

@Preview
@Composable
private fun LeaderSettingsRoutePreview() {
    TogedyTheme {
        LeaderSettingsRoute(
            onBackClick = {},
            onInfoClick = {},
            onMemberClick = {},
            onMemberCountClick = {},
            onLeaderEditClick = {},
        )
    }
}
