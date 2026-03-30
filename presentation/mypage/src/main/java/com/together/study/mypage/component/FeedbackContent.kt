package com.together.study.mypage.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.component.textfield.TogedyTextField
import com.together.study.designsystem.theme.TogedyTheme


@Composable
internal fun FeedbackContent(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    MyTextField(
        modifier = modifier.padding(top = 32.dp),
        inputTitle = "문의하기",
        inputEssential = true
    ) {
        TogedyTextField(
            value = value,
            onValueChange = onValueChange,
            backgroundColor = TogedyTheme.colors.white,
            placeholderText = "예시) 000은 어떻게 해결하나요?",
            showBorder = false,
            singleLine = false,
            minHeight = 108.dp
        )
    }
}
