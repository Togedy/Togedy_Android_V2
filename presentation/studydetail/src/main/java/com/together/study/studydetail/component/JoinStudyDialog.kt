package com.together.study.studydetail.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.together.study.designsystem.component.dialog.TogedyBasicDialog
import com.together.study.designsystem.theme.TogedyTheme

@Composable
fun JoinStudyDialog(
    studyName: String,
    hasPassword: Boolean,
    password: String = "",
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onJoinStudyClick: () -> Unit,
) {
    var input by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    TogedyBasicDialog(
        title = "스터디 가입",
        subTitle = {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        TogedyTheme.typography.body14b.toSpanStyle()
                            .copy(TogedyTheme.colors.gray900)
                    ) {
                        append(studyName)
                    }
                    withStyle(
                        TogedyTheme.typography.body14m.toSpanStyle()
                            .copy(TogedyTheme.colors.gray700)
                    ) {
                        append("\n스터디에 가입할까요?")
                    }
                },
            )
            if (hasPassword) {
                //TODO : 비밀번호 입력
                //오류 메시지 부분
            }
        },
        buttonText = "가입하기",
        onDismissRequest = onDismissRequest,
        onButtonClick = {
            if (!hasPassword) onJoinStudyClick
            else {
                if (password == input) onJoinStudyClick
                else showError = true
            }
        },
        modifier = modifier,
    )
}

@Preview
@Composable
private fun JoinStudyDialogPreview() {
    JoinStudyDialog(
        studyName = "햄버거파이터즈",
        hasPassword = false,
        modifier = Modifier,
        onDismissRequest = {},
        onJoinStudyClick = {},
    )
}
