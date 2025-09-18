package com.together.study.designsystem.component.textchip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme

/**
 * basicTextChip : 흰 글씨 + 배경색 (클릭X) 기본 칩
 *
 * @param text Chip 내용
 * @param textColor 글씨 색상(default : 흰색)
 * @param backgroundColor 배경 색상(default : gray700)
 */
@Composable
fun TogedyBasicTextChip(
    text: String,
    textColor: Color = TogedyTheme.colors.white,
    backgroundColor: Color = TogedyTheme.colors.gray700,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = TogedyTheme.typography.body12m.copy(textColor),
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(30.dp)
            )
            .padding(horizontal = 10.dp, vertical = 3.dp)
    )
}

@Preview
@Composable
private fun TogedyBasicTextChipPreview(modifier: Modifier = Modifier) {
    TogedyTheme {
        TogedyBasicTextChip(
            text = "공부중",
            modifier = modifier,
        )
    }
}
