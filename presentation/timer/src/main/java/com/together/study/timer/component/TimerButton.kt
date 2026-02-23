package com.together.study.timer.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@Composable
internal fun TimerButton(
    isPlaying: Boolean,
    onButtonClick: () -> Unit,
) {
    val text = if (isPlaying) "중지" else "시작"
    val textColor =
        if (isPlaying) TogedyTheme.colors.white
        else TogedyTheme.colors.green500
    val borderColor =
        if (isPlaying) TogedyTheme.colors.gray600
        else TogedyTheme.colors.green.copy(0.8f)
    val backgroundColor =
        if (isPlaying) TogedyTheme.colors.gray600.copy(0.6f)
        else TogedyTheme.colors.green.copy(0.8f)

    Text(
        text = text,
        style = TogedyTheme.typography.body14b,
        color = textColor,
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape(30.dp))
            .border(1.dp, borderColor, RoundedCornerShape(30.dp))
            .padding(horizontal = 50.dp, vertical = 10.dp)
            .noRippleClickable(onButtonClick),
    )
}

@Preview
@Composable
private fun TimerButtonPreview() {
    TogedyTheme {
        TimerButton(
            isPlaying = false,
            onButtonClick = {},
        )
    }
}
