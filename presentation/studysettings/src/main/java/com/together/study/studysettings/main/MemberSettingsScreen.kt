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
import androidx.hilt.navigation.compose.hiltViewModel
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.component.dialog.TogedyBasicDialog
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.studysettings.component.SettingsSection

@Composable
fun MemberSettingsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    onMemberNavigate: (Long) -> Unit,
    onReportNavigate: (Long) -> Unit,
    viewModel: MemberSettingsViewModel = hiltViewModel(),
) {
    var isExitDialogVisible by remember { mutableStateOf(false) }

    val memberEdit = listOf(
        Settings(title = "멤버 보기", onClick = { onMemberNavigate(viewModel.studyId) }),
    )
    val deleteEdit = listOf(
        Settings(title = "문제 신고하기", icon = null, onClick = { onReportNavigate(viewModel.studyId) }),
        Settings(
            title = "스터디 나가기",
            icon = null,
            isTextRed = true,
            onClick = { isExitDialogVisible = true }
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
            sectionTitle = "스터디 멤버관리",
            items = memberEdit,
        )

        SettingsSection(
            sectionTitle = "스터디 삭제 설정",
            items = deleteEdit,
        )
    }

    if (isExitDialogVisible) {
        TogedyBasicDialog(
            title = "스터디 나가기",
            subTitle = {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = TogedyTheme.typography.body14b.toSpanStyle()) {
                            append(viewModel.studyName)
                        }
                        append(" 스터디를 나갈까요?")
                    },
                    style = TogedyTheme.typography.body14m,
                    color = TogedyTheme.colors.gray900,
                    textAlign = TextAlign.Center,
                )
            },
            buttonText = "나가기",
            buttonColor = TogedyTheme.colors.red,
            onDismissRequest = { isExitDialogVisible = false },
            onButtonClick = viewModel::deleteStudyAsMember,
        )
    }
}

@Preview
@Composable
private fun MemberSettingsRoutePreview() {
    TogedyTheme {
        MemberSettingsRoute(
            onBackClick = {},
            onMemberNavigate = {},
            onReportNavigate = {},
        )
    }
}