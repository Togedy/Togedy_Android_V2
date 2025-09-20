package com.together.study.designsystem.component.textchip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

/**
 * TogedyClickableTextChip 클릭 가능한 텍스트칩
 *
 * @param text chip 내용
 * @param selected chip 선택 여부
 * @param onClick 클릭 시 호출되는 콜백
 * @param modifier 수정자
 */
@Composable
fun TogedyClickableTextChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val textStyle =
        if (selected) TogedyTheme.typography.body14b
        else TogedyTheme.typography.body14m
    val backgroundColor =
        if (selected) TogedyTheme.colors.gray900
        else TogedyTheme.colors.white
    val textColor =
        if (selected) TogedyTheme.colors.white
        else TogedyTheme.colors.gray600

    Text(
        text = text,
        style = textStyle.copy(textColor),
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .noRippleClickable(onClick),
    )
}

@Preview
@Composable
fun TogedyClickableTextChipPreview(modifier: Modifier = Modifier) {
    TogedyTheme {
        Column {
            TogedyClickableTextChip(
                text = "전체",
                selected = true,
                onClick = {},
                modifier = modifier,
            )
            TogedyClickableTextChip(
                text = "전체",
                selected = false,
                onClick = {},
                modifier = modifier,
            )
        }
    }
}
