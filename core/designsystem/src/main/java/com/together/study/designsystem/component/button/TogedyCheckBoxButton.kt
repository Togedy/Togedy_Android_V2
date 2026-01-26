package com.together.study.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.ic_check_green
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@Composable
fun TogedyCheckBoxButton(
    isChecked: Boolean,
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TogedyTheme.typography.toast12sb,
    textColor: Color = TogedyTheme.colors.gray700,
    onCheckBoxClick: () -> Unit,
) {
    val buttonColor =
        if (isChecked) TogedyTheme.colors.green
        else TogedyTheme.colors.gray200

    Row(
        modifier = modifier
            .padding(vertical = 4.dp)
            .noRippleClickable(onCheckBoxClick),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(
                    color = buttonColor,
                    shape = RoundedCornerShape(4.dp)
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(ic_check_green),
                contentDescription = null,
                tint = TogedyTheme.colors.white,
            )
        }

        Text(
            text = text,
            style = textStyle,
            color = textColor,
        )
    }
}

@Preview
@Composable
private fun TogedyCheckBoxButtonPreview() {
    TogedyTheme {
        TogedyCheckBoxButton(
            isChecked = false,
            text = "전체 선택",
            onCheckBoxClick = {},
        )
    }
}
