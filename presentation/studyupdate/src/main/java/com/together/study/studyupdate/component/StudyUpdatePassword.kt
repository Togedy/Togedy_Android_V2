package com.together.study.studyupdate.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.component.textfield.TogedyTextField
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.studyupdate.StudyUpdateTextItem

@Composable
internal fun StudyUpdatePassword(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    StudyUpdateTextItem(
        modifier = modifier.padding(top = 32.dp),
        inputTitle = "비밀번호(선택)",
        inputTitleSub = "4자리 설정",
        inputEssential = false
    ) {
        TogedyTextField(
            value = value,
            onValueChange = { newValue ->
                val filtered = newValue.filter { it.isDigit() }
                onValueChange(filtered)
            },
            showBorder = false,
            backgroundColor = TogedyTheme.colors.white,
            visualTransformation = PasswordVisualTransformation(),
            placeholderText = "비밀번호를 입력해주세요",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
        )
    }
}

