package com.together.study.study.main.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.img_character_sleeping
import com.together.study.designsystem.theme.TogedyTheme

@Composable
internal fun EmptyMyStudy(
    onJoinButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(img_character_sleeping),
            contentDescription = null,
        )

        Spacer(Modifier.height(14.dp))

        Text(
            text = "내 스터디가 없어요!",
            style = TogedyTheme.typography.title16sb,
            color = TogedyTheme.colors.gray900,
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = "스터디를 둘러보거나 직접 만들어\n함께 공부를 시작해보세요",
            style = TogedyTheme.typography.body13m,
            color = TogedyTheme.colors.gray600,
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.height(14.dp))

        Button(
            onClick = onJoinButtonClick,
            enabled = true,
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = TogedyTheme.colors.black,
                contentColor = TogedyTheme.colors.white,
            ),
            shape = RoundedCornerShape(30.dp),
        ) {
            Text(
                text = "스터디 가입하기",
                style = TogedyTheme.typography.title16sb,
                color = TogedyTheme.colors.white,
                modifier = Modifier.padding(horizontal = 22.dp, vertical = 6.dp)
            )
        }
    }
}
