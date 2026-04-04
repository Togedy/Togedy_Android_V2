package com.together.study.studydetail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.img_character_sleeping
import com.together.study.designsystem.theme.TogedyTheme

@Composable
internal fun CurrentMonthlyStudyCount(
    userName: String,
    count: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 12.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(
                    color = TogedyTheme.colors.greenBg,
                    shape = RoundedCornerShape(8.dp),
                )
                .clip(RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 10.dp),
            contentAlignment = Alignment.TopEnd,
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = TogedyTheme.colors.gray800)) {
                        append("${userName}님")
                    }
                    withStyle(SpanStyle(color = TogedyTheme.colors.gray600)) {
                        append("은 이번 달 총 ")
                    }
                    withStyle(SpanStyle(color = TogedyTheme.colors.green)) {
                        append("${count}일")
                    }
                    withStyle(SpanStyle(color = TogedyTheme.colors.gray600)) {
                        append(" 공부했어요 \uD83D\uDD25")
                    }
                },
                style = TogedyTheme.typography.body13b,
                modifier = Modifier.align(Alignment.CenterStart),
            )

            Image(
                painter = painterResource(img_character_sleeping),
                contentDescription = null,
                modifier = Modifier
                    .width(57.dp)
                    .aspectRatio(1f)
                    .alpha(0.2f),
            )
        }
    }
}
