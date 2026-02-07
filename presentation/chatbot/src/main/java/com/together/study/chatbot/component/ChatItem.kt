package com.together.study.chatbot.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.img_character_heart
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@Composable
internal fun ChatItem(
    message: String,
    isMine: Boolean,
    hasIcon: Boolean = false,
    textColor: Color? = null,
    onItemClick: () -> Unit = {},
) {
    val cornerShape =
        if (isMine) RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp)
        else RoundedCornerShape(topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
    val textColor =
        textColor ?: if (isMine) TogedyTheme.colors.gray700
        else TogedyTheme.colors.gray600

    Column(
        modifier = Modifier.noRippleClickable(onItemClick),
        horizontalAlignment = if (isMine) Alignment.End else Alignment.Start,
    ) {
        if (hasIcon) {
            Image(
                painter = painterResource(img_character_heart),
                contentDescription = null,
                modifier = Modifier.size(54.dp),
            )

            Spacer(Modifier.height(4.dp))
        }

        Box(
            modifier = Modifier
                .background(
                    color = TogedyTheme.colors.white,
                    shape = cornerShape
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            Text(
                text = message,
                style = TogedyTheme.typography.body13m,
                color = textColor,
            )
        }
    }
}

@Preview
@Composable
private fun MyChatItemPreview() {
    TogedyTheme {
        ChatItem(
            message = "수능까지 몇 일 남았나요?",
            isMine = true,
        )
    }
}

@Preview
@Composable
private fun AnswerChatItemPreview() {
    TogedyTheme {
        ChatItem(
            message = "000님, 안녕하세요! 건국대학교 2025학년도 편입학 원서 접수 기간에 대해 궁금해하시는군요.\n" +
                    "2025학년도 건국대학교 편입학 원서 접수는 이미 마감되었습니다. 접수 기간은 2024년 12월 11일(수) 09:30부터 2024년 12월 17일(화) 18:00까지 온라인으로 진행되었었습니다. \n" +
                    "더 자세한 정보는 건국대학교 입학처 홈페이지에서 2025학년도 편입학 모집요강을 확인해 보시는 것이 가장 정확합니다.",
            isMine = false,
            hasIcon = true,
        )
    }
}

@Preview
@Composable
private fun LoadingChatItemPreview() {
    TogedyTheme {
        ChatItem(
            message = "생각중...",
            isMine = false,
            hasIcon = true,
            textColor = TogedyTheme.colors.gray500,
        )
    }
}