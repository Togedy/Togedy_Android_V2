package com.together.study.studydetail.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.component.dialog.TogedyBasicDialog
import com.together.study.designsystem.theme.TogedyTheme

@Composable
fun JoinStudyDialog(
    studyName: String,
    hasPassword: Boolean,
    modifier: Modifier = Modifier,
    errorMessage: String = "",
    onDismissRequest: () -> Unit,
    onJoinStudyClick: (String?) -> Unit,
) {
    var inputValue: String? by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(errorMessage.isNotEmpty()) }
    var errorMessage by remember { mutableStateOf(errorMessage) }

    TogedyBasicDialog(
        title = "스터디 가입",
        subTitle = {
            if (hasPassword) {
                Text(
                    text = studyName,
                    style = TogedyTheme.typography.body14b,
                    color = TogedyTheme.colors.gray900,
                )

                Spacer(Modifier.height(10.dp))

                PasswordTextField(
                    inputValue = inputValue ?: "",
                    onValueChange = { inputValue = it },
                )

                if (showError) {
                    Spacer(Modifier.height(2.dp))

                    Text(
                        text = errorMessage,
                        style = TogedyTheme.typography.chip10sb,
                        color = TogedyTheme.colors.red,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

            } else {
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
                    textAlign = TextAlign.Center,
                )
            }
        },
        buttonText = "가입하기",
        onDismissRequest = onDismissRequest,
        onButtonClick = { onJoinStudyClick(inputValue) },
        modifier = modifier,
    )
}

@Composable
private fun PasswordTextField(
    inputValue: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {
    val borderColor =
        if (inputValue.isEmpty()) TogedyTheme.colors.gray200
        else TogedyTheme.colors.green

    BasicTextField(
        value = inputValue,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(10.dp),
        textStyle = TogedyTheme.typography.body14m,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = { innerTextField ->
            if (inputValue.isEmpty()) {
                Text(
                    text = "비밀번호를 입력해주세요",
                    style = TogedyTheme.typography.body14m,
                    color = TogedyTheme.colors.gray500,
                )
            }

            innerTextField()
        }
    )
}

@Preview
@Composable
private fun JoinStudyDialogPreview() {
    TogedyTheme {
        JoinStudyDialog(
            studyName = "햄버거파이터즈",
            hasPassword = true,
            modifier = Modifier,
            onDismissRequest = {},
            onJoinStudyClick = {},
        )
    }
}
