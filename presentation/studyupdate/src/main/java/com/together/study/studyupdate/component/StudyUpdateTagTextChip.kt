package com.together.study.studyupdate.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.component.textchip.TogedyBasicTextChip
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@Composable
fun StudyUpdateTagTextChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .noRippleClickable(onClick)
            .background(
                color = TogedyTheme.colors.white,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = if (selected) TogedyTheme.colors.green else TogedyTheme.colors.gray300,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(vertical = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        TogedyBasicTextChip(
            text = text,
            textStyle = TogedyTheme.typography.body13b,
            textColor = if (selected) TogedyTheme.colors.green else TogedyTheme.colors.gray600,
            backgroundColor = Color.Transparent,
            roundedCornerShape = RoundedCornerShape(16.dp),
            horizontalPadding = 0.dp,
            verticalPadding = 0.dp,
        )
    }
}





