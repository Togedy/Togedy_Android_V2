package com.together.study.studydetail.detailmain.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.together.study.designsystem.R.drawable.img_character_goal
import com.together.study.designsystem.theme.TogedyTheme

@Composable
internal fun JoinCompleteDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
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
            Spacer(Modifier.height(10.dp))

            Image(
                painter = painterResource(img_character_goal),
                contentDescription = null,
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "스터디에 가입했어요!",
                style = TogedyTheme.typography.title18b,
                color = TogedyTheme.colors.gray900,
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "새로운 시작을 축하해요!\n스터디 멤버들과 함께 성장하는 과정을 경험해볼까요?",
                style = TogedyTheme.typography.body12m,
                color = TogedyTheme.colors.gray600,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(28.dp))

            Button(
                onClick = onDismissRequest,
                enabled = true,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = TogedyTheme.colors.greenBg,
                    contentColor = TogedyTheme.colors.green,
                ),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(
                    text = "완료",
                    style = TogedyTheme.typography.title16sb,
                    modifier = Modifier.padding(vertical = 10.dp),
                )
            }
        }
    }
}

@Preview
@Composable
private fun JoinCompleteDialogPreview() {
    TogedyTheme {
        JoinCompleteDialog(
            modifier = Modifier,
            onDismissRequest = {},
        )
    }
}
