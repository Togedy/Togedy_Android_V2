package com.together.study.chatbot.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.img_character_heart
import com.together.study.designsystem.theme.TogedyTheme

@Composable
internal fun ChatBotIntroContent(
    onQuestionClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(120.dp))

        Column {
            Image(
                painter = painterResource(img_character_heart),
                contentDescription = null,
            )

            Text(
                text = "입시에 대한",
                style = TogedyTheme.typography.title24b,
                color = TogedyTheme.colors.gray700,
                modifier = Modifier.padding(top = 10.dp, start = 10.dp),
            )
            Text(
                text = "모든 것을 물어보세요!",
                style = TogedyTheme.typography.title24b,
                color = TogedyTheme.colors.gray700,
                modifier = Modifier.padding(start = 10.dp, bottom = 28.dp),
            )

            ChatItem(
                message = "수능까지 몇 일 남았나요?",
                isMine = false,
                onItemClick = { onQuestionClick("수능까지 몇 일 남았나요?") },
            )
            Spacer(Modifier.height(6.dp))
            ChatItem(
                message = "이번 주 원서 접수하는 대학이 어디인가요?",
                isMine = false,
                onItemClick = { onQuestionClick("이번 주 원서 접수하는 대학이 어디인가요?") },
            )
            Spacer(Modifier.height(6.dp))
            ChatItem(
                message = "제게 맞는 공부법을 알고 싶어요",
                isMine = false,
                onItemClick = { onQuestionClick("제게 맞는 공부법을 알고 싶어요") },
            )
            Spacer(Modifier.height(6.dp))
            ChatItem(
                message = "건국대학교 편입 원서 접수 기간이 궁금해요",
                isMine = false,
                onItemClick = { onQuestionClick("건국대학교 편입 원서 접수 기간이 궁금해요") },
            )
        }
    }
}
