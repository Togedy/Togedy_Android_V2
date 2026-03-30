package com.together.study.studyupdate.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.component.textfield.TogedyTextField
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.studyupdate.StudyUpdateTextItem

@Composable
internal fun StudyUpdateIntroduce(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    StudyUpdateTextItem(
        modifier = modifier.padding(top = 32.dp),
        inputTitle = "스터디 소개",
        inputEssential = true
    ) {
        TogedyTextField(
            value = value,
            onValueChange = onValueChange,
            backgroundColor = TogedyTheme.colors.white,
            placeholderText = "스터디의 소개를 입력해주세요",
            showBorder = false,
            singleLine = false,
            minHeight = 108.dp
        )
    }
}

