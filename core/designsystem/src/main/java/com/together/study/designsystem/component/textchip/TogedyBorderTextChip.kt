package com.together.study.designsystem.component.textchip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
 * TogedyBorderTextChip 글씨색 + 흰배경 + 테두리 (클릭X) 텍스트 칩
 *
 * @param text chip 내용
 * @param textStyle 글씨 크기(default : body12m)
 * @param lineColor 글씨 색상(default : green)
 * @param roundedCornerShape 테두리 곡률(default : 30.dp)
 * @param horizontalPadding 가로 여백(default : 10.dp)
 * @param verticalPadding 세로 여백(default : 3.dp)
 * @param modifier 수정자
 */
@Composable
fun TogedyBorderTextChip(
    text: String,
    textStyle: TextStyle = TogedyTheme.typography.body12m,
    lineColor: Color = TogedyTheme.colors.green,
    roundedCornerShape: RoundedCornerShape = RoundedCornerShape(30.dp),
    horizontalPadding: Dp = 10.dp,
    verticalPadding: Dp = 3.dp,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = textStyle,
        color = lineColor,
        modifier = modifier
            .background(
                color = TogedyTheme.colors.white,
                shape = roundedCornerShape,
            )
            .border(1.dp, color = lineColor, shape = roundedCornerShape)
            .padding(horizontal = horizontalPadding, vertical = verticalPadding),

        )
}

@Preview
@Composable
private fun TogedyBorderTextChipPreview(modifier: Modifier = Modifier) {
    TogedyTheme {
        TogedyBorderTextChip(
            text = "공부중",
        )
    }
}
