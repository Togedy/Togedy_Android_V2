package com.togehter.study.studyupdate.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.togehter.study.studyupdate.StudyUpdateTextItem
import com.together.study.designsystem.component.textfield.TogedyTextField
import com.together.study.designsystem.theme.TogedyTheme

@Composable
internal fun StudyUpdateName(
    value: String,
    onValueChange: (String) -> Unit,
    onDupCheckClick: () -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null,
    modifier: Modifier = Modifier
) {
    StudyUpdateTextItem(
        modifier = modifier.padding(top = 20.dp),
        inputTitle = "스터디 이름",
        inputEssential = true
    ) {
        TogedyTextField(
            value = value,
            onValueChange = onValueChange,
            backgroundColor = TogedyTheme.colors.white,
            placeholderText = "이름을 입력해주세요 (최대 nn자)",
            showBorder = false,
            showDupCheck = true,
            onDupCheckClick = onDupCheckClick,
            isError = isError,
            errorMessage = errorMessage
        )
    }
}

