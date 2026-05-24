package com.together.study.timer.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.together.study.designsystem.component.dialog.TogedyBasicDialog
import com.together.study.designsystem.theme.TogedyTheme

@Composable
fun SubjectChangeDialog(
    subjectName: String,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit,
) {
    TogedyBasicDialog(
        title = subjectName,
        titleColor = TogedyTheme.colors.green,
        subTitle = {
            Text(
                text ="해당 과목으로 변경하시겠어요?",
                style = TogedyTheme.typography.body14r,
                color = TogedyTheme.colors.gray600,
            )
        },
        buttonText = "변경하기",
        onDismissRequest = onDismissRequest,
        onButtonClick = onConfirmClick,
        modifier = modifier,
    )
}
