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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme

/**
 * 기본 토스트 메시지
 *
 * @param message 토스트 메세지 내용
 * @param textColor 토스트 메시지 글자색
 * @param textStyle 토스트 메시지 텍스트 스타일
 * @param backgroundColor 토스트 배경색
 * @param icon 토스트 icon
 * @param shape 토스트 RoundedCornerShape값
 * @param modifier 수정자
 */
@Composable
fun CommonToast(
    message: String,
    textColor: Color = TogedyTheme.colors.white,
    textStyle: TextStyle = TogedyTheme.typography.body14m,
    backgroundColor: Color = TogedyTheme.colors.gray600,
    icon: ImageVector? = null,
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
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Unspecified,
            )

            Spacer(Modifier.width(8.dp))
        }

        Text(
            text = message,
            style = textStyle.copy(textColor),
        )
    }
}

@Preview
@Composable
private fun CommonToastPreview(modifier: Modifier = Modifier) {
    TogedyTheme {
        CommonToast(
            message = "건국대학교(서울캠퍼스)를 캘린더에 추가했어요",
            modifier = modifier.fillMaxWidth()
        )
    }
}
