package com.together.study.designsystem.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.together.study.designsystem.theme.TogedyTheme

/**
 * TogedyBasicDialog
 * 취소 버튼과 확인 버튼이 있는 다이얼로그입니다.
 *
 * @param title 상단 굵게 표시되는 제목
 * @param subTitle 다이얼로그 내용을 나타내는 Composable
 * @param buttonText 확인 버튼 내용
 * @param onDismissRequest 취소 버튼 클릭 또는 여백 클릭 시 호출되는 콜백
 * @param onButtonClick 확인 버튼 클릭 시 호출되는 콜백
 * @param buttonColor 확인 버튼 배경 색상(default : green)
 * @param modifier 수정자
 */
@Composable
fun TogedyBasicDialog(
    title: String,
    subTitle: @Composable () -> Unit,
    buttonText: String,
    onDismissRequest: () -> Unit,
    onButtonClick: () -> Unit,
    buttonColor: Color = TogedyTheme.colors.green,
    modifier: Modifier = Modifier,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = modifier
                .background(color = TogedyTheme.colors.white, shape = RoundedCornerShape(16.dp))
                .padding(vertical = 20.dp, horizontal = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title,
                style = TogedyTheme.typography.title18b,
                color = TogedyTheme.colors.gray900,
            )

            Spacer(Modifier.height(16.dp))

            subTitle()

            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DialogButton(
                    text = "취소",
                    contentColor = TogedyTheme.colors.gray600,
                    containerColor = TogedyTheme.colors.gray400,
                    onButtonClick = onDismissRequest,
                    modifier = Modifier.weight(1f),
                )

                DialogButton(
                    text = buttonText,
                    contentColor = TogedyTheme.colors.white,
                    onButtonClick = onButtonClick,
                    containerColor = buttonColor,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun DialogButton(
    text: String,
    contentColor: Color,
    containerColor: Color,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onButtonClick,
        enabled = true,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ),
        shape = RoundedCornerShape(12.dp),
    ) {
        Text(
            text = text,
            style = TogedyTheme.typography.body14b,
            color = contentColor,
            modifier = Modifier.padding(vertical = 6.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TogedyBasicDialogPreview() {
    TogedyTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            TogedyBasicDialog(
                title = "멤버 내보내기",
                subTitle = { Text("멤버를 내보내시겠습니까?") },
                buttonText = "내보내기",
                onDismissRequest = {},
                onButtonClick = {},
                modifier = Modifier,
            )
        }
    }
}
