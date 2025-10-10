package com.together.study.studydetail.detailmain.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.component.textchip.TogedyBasicTextChip
import com.together.study.designsystem.theme.TogedyTheme

@Composable
internal fun DailyCompletionBar(
    completedMemberCount: Int,
    studyMemberCount: Int,
    modifier: Modifier = Modifier,
) {
    val barHeight = 8.dp
    val backgroundColor = TogedyTheme.colors.green400
    val progressColor = TogedyTheme.colors.green
    val roundedCornerShape = RoundedCornerShape(30.dp)
    val completionRate =
        if (studyMemberCount > 0) {
            completedMemberCount.toFloat() / studyMemberCount.toFloat()
        } else {
            0f
        }


    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(TogedyTheme.colors.greenBg)
            .padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.End,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(barHeight)
                .background(backgroundColor, roundedCornerShape)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(completionRate)
                    .height(barHeight)
                    .background(progressColor, roundedCornerShape)
            )
        }

        Spacer(Modifier.height(12.dp))

        TogedyBasicTextChip(
            text = "${studyMemberCount - completedMemberCount}명 남았어요!",
            textColor = TogedyTheme.colors.green,
            backgroundColor = TogedyTheme.colors.green400,
        )
    }
}

@Preview
@Composable
private fun DailyCompletionBarPreview() {
    TogedyTheme {
        DailyCompletionBar(
            completedMemberCount = 5,
            studyMemberCount = 20,
            modifier = Modifier,
        )
    }
}
