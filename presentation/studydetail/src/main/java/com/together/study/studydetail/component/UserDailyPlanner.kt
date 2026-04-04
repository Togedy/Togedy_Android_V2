package com.together.study.studydetail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.ic_lock
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.study.model.StudyMemberPlanner.DailyPlanner

@Composable
internal fun UserDailyPlanner(
    plans: List<DailyPlanner>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .height(300.dp),
    ) {
        itemsIndexed(plans) { index, item ->
            Column(
                modifier = Modifier.padding(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = item.studyCategoryName,
                    style = TogedyTheme.typography.body14b,
                    color = TogedyTheme.colors.gray900,
                )

                HorizontalDivider(color = TogedyTheme.colors.gray50)

                item.planList.forEach { plan ->
                    val status =
                        if (plan.planStatus == "완료") TextDecoration.LineThrough
                        else TextDecoration.None
                    val color =
                        if (plan.planStatus == "완료") TogedyTheme.colors.gray500
                        else TogedyTheme.colors.gray900

                    Text(
                        text = plan.planName,
                        style = TogedyTheme.typography.body13m,
                        color = color,
                        textDecoration = status
                    )

                    HorizontalDivider(color = TogedyTheme.colors.gray50)
                }
            }
        }
    }
}

@Composable
internal fun EmptyDailyPlanner(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.height(200.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "오늘의 일정이 없습니다.",
            style = TogedyTheme.typography.body14m,
            color = TogedyTheme.colors.gray500,
        )
    }
}

@Composable
internal fun UnOpenedPlanner(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.height(200.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(ic_lock),
            contentDescription = null,
            tint = Color.Unspecified,
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "플래너가 비공개 상태예요",
            style = TogedyTheme.typography.body14b,
            color = TogedyTheme.colors.gray700,
        )

        Text(
            text = "이 멤버가 개인 플래너를\n다른 사람들에게 공개하지 않기로 했어요",
            style = TogedyTheme.typography.body12m,
            color = TogedyTheme.colors.gray500,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun UserDailyPlannerPreview() {
    TogedyTheme {
        UserDailyPlanner(
            plans = listOf(
                DailyPlanner("과목1", listOf())
            )
        )
    }
}

@Preview
@Composable
private fun EmptyDailyPlannerPreview() {
    TogedyTheme {
        EmptyDailyPlanner()
    }
}

@Preview
@Composable
private fun UnOpenedPlannerPreview() {
    TogedyTheme {
        UnOpenedPlanner()
    }
}
