package com.together.study.designsystem.component.textchip

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

    TogedyBasicTextChip(
        text = text,
        textStyle = textStyle,
        textColor = textColor,
        backgroundColor = backgroundColor,
        roundedCornerShape = RoundedCornerShape(20.dp),
        horizontalPadding = 12.dp,
        verticalPadding = 4.dp,
        modifier = modifier.noRippleClickable(onClick),
    )
}

@Preview
@Composable
fun TogedyClickableTextChipPreview(modifier: Modifier = Modifier) {
    var isSelected by remember { mutableStateOf(false) }
    TogedyTheme {
        TogedyClickableTextChip(
            text = "전체",
            selected = isSelected,
            onClick = { isSelected = !isSelected },
            modifier = modifier,
        )
    }
}
