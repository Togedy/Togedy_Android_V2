package com.together.study.mypage.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.component.textfield.TogedyTextField
import com.together.study.designsystem.theme.TogedyTheme

@Composable
internal fun MyTextField(
    modifier: Modifier = Modifier,
    inputTitle: String,
    inputEssential: Boolean = true,
    inputTitleSub: String = "",
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = inputTitle,
                    style = TogedyTheme.typography.body14b.copy(
                        color = TogedyTheme.colors.gray800
                    )
                )

                if (inputEssential) {
                    if (inputTitleSub != "") {
                        Text(
                            text = inputTitleSub,
                            style = TogedyTheme.typography.body10m.copy(
                                color = TogedyTheme.colors.gray500
                            ),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
        content()
    }
}

@Preview
@Composable
private fun MyTextFieldPreview() {
    TogedyTheme {
        MyTextField(
            inputTitle = "닉네임",
            inputEssential = true,
            inputTitleSub = "2~10글자",
            content = {
                TogedyTextField(
                    value = "",
                    onValueChange = { },
                    backgroundColor = TogedyTheme.colors.white,
                    placeholderText = "닉네임을 입력해주세요",
                    showBorder = false,
                    showDupCheck = true,
                    onDupCheckClick = { },
                    isError = true,
                    errorMessage = "이미 존재하는 닉네임입니다.",
                )
            }
        )
    }
}