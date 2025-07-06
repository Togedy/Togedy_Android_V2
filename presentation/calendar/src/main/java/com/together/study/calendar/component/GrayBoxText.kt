package com.together.study.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@Composable
internal fun GrayBoxText(
    text: String,
    textStyle: TextStyle = TogedyTheme.typography.body14m,
    textColor: Color = TogedyTheme.colors.gray500,
    onTextClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = textStyle.copy(textColor),
        modifier = modifier
            .noRippleClickable(onTextClick)
            .background(TogedyTheme.colors.gray200, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
    )
}

@Preview
@Composable
private fun GrayBoxTextPreview(modifier: Modifier = Modifier) {
    TogedyTheme {
        GrayBoxText(
            text = "안녕",
            onTextClick = {},
            modifier = modifier,
        )
    }
}