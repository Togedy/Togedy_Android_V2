package com.together.study.studyupdate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.component.textfield.TogedyTextField
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@Composable
fun StudyUpdateTextItem(
    modifier: Modifier = Modifier,
    inputTitle: String,
    inputEssential: Boolean = true,
    inputTitleSub: String = "",
    isDelete: Boolean = false,
    onDeleteClicked: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = inputTitle,
                    style = TogedyTheme.typography.body14b.copy(
                        color = TogedyTheme.colors.gray800
                    )
                )

                if (inputEssential) {
                    Text(
                        text = "*",
                        style = TogedyTheme.typography.body14b.copy(
                            color = TogedyTheme.colors.red50
                        )
                    )

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

            if (isDelete) {
                Text(
                    text = "삭제",
                    style = TogedyTheme.typography.body13m.copy(
                        color = TogedyTheme.colors.green
                    ),
                    modifier = Modifier
                        .noRippleClickable(onDeleteClicked)
                        .padding(horizontal = 5.dp)
                )
            }
        }
        content()
    }
}

@Preview
@Composable
fun StudyUpdateTextItemPreview() {
    TogedyTheme {
        var text by remember { mutableStateOf("") }
        StudyUpdateTextItem(
            inputTitle = "스터디이름",
            inputEssential = true,
            inputTitleSub = "최대 30명까지 가능",
            isDelete = true
        ) {
            TogedyTextField(
                value = text,
                onValueChange = { text = it },
                placeholderText = "스터디 이름을 입력하세요"
            )
        }
    }
}