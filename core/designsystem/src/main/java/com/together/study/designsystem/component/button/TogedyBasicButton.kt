package com.together.study.designsystem.component.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme

/**
 * TogedyBasicButton
 * 기본적인 투게디 버튼입니다.
 *
 * @param title 버튼 제목
 * @param onClick 버튼 클릭 시 실행되는 콜백함수
 * @param containerColor 버튼 배경색상
 * @param contentColor 버튼 텍스트 색상
 * @param textStyle 버튼 텍스트 스타일
 * @param modifier 수정자
 */
@Composable
fun TogedyBasicButton(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = TogedyTheme.colors.green,
    contentColor: Color = TogedyTheme.colors.white,
    textStyle: TextStyle = TogedyTheme.typography.title16sb,
) {
    Button(
        onClick = onClick,
        enabled = true,
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ),
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = title,
            style = textStyle,
            color = contentColor,
            modifier = Modifier.padding(vertical = 10.dp),
        )
    }
}

@Preview
@Composable
private fun TogedyBasicButtonPreview() {
    TogedyTheme {
        TogedyBasicButton(
            title = "시작하기",
            onClick = {},
        )
    }
}
