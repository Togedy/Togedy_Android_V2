package com.together.study.designsystem.component.toast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.ic_search_24
import com.together.study.designsystem.theme.TogedyTheme

@Composable
fun WarningToast(
    message: String,
    textColor: Color = TogedyTheme.colors.red,
    textStyle: TextStyle = TogedyTheme.typography.body14m,
    backgroundColor: Color = TogedyTheme.colors.red30,
    shape: Shape = RoundedCornerShape(8.dp),
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(backgroundColor, shape)
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(ic_search_24),
            contentDescription = null,
            tint = TogedyTheme.colors.red,
        )

        Spacer(Modifier.width(8.dp))

        Text(
            text = message,
            style = textStyle.copy(textColor),
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun WarningSnackBarPreview(modifier: Modifier = Modifier) {
    TogedyTheme {
        WarningToast(
            message = "최대 30자까지만 작성할 수 있어요.",
            modifier = modifier.fillMaxWidth()
        )
    }
}