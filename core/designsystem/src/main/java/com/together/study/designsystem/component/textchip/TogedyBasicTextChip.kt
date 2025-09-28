package com.together.study.designsystem.component.textchip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme

/**
 * basicTextChip : 흰 글씨 + 배경색 (클릭X) 기본 칩
 *
 * @param text Chip 내용
 * @param textStyle 글씨 크기(default : body12m)
 * @param textColor 글씨 색상(default : white)
 * @param backgroundColor 배경 색상(default : gray700)
 * @param roundedCornerShape 테두리 곡률(default : 30.dp)
 * @param horizontalPadding 가로 여백(default : 10.dp)
 */
@Composable
fun TogedyBasicTextChip(
    text: String,
    textStyle: TextStyle = TogedyTheme.typography.body12m,
    textColor: Color = TogedyTheme.colors.white,
    backgroundColor: Color = TogedyTheme.colors.gray700,
    roundedCornerShape: RoundedCornerShape = RoundedCornerShape(30.dp),
    horizontalPadding: Dp = 10.dp,
    verticalPadding: Dp = 3.dp,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = textStyle,
        color = textColor,
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = roundedCornerShape
            )
            .padding(horizontal = horizontalPadding, vertical = verticalPadding)
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
