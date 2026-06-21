package com.together.study.planner.share.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.common.type.planner.toPlannerSubjectColorOrDefault
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.planner.model.PlannerSubject
import com.together.study.planner.model.TaskItem
import com.together.study.util.asColor

@Composable
internal fun PlannerContent(
    showTask: Boolean,
    plans: List<PlannerSubject>,
    selectedSubjects: List<Long>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.wrapContentHeight(),
    ) {
        plans.forEach { plan ->
            val subjectColor = plan.subjectColor.toPlannerSubjectColorOrDefault().asColor()

            Row(
                modifier = Modifier.padding(bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(subjectColor, RoundedCornerShape(4.dp))
                        .padding(end = 4.dp),
                )

                Spacer(Modifier.width(4.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .then(
                            if (plan.subjectId !in selectedSubjects) Modifier.blur(10.dp)
                            else Modifier
                        )
                ) {
                    Text(
                        text = plan.subjectName,
                        style = TogedyTheme.typography.body12m,
                        color = TogedyTheme.colors.gray700,
                    )
                }

                Spacer(Modifier.width(4.dp))

                Text(
                    text = "${plan.checkedTaskCount ?: ""}",
                    style = TogedyTheme.typography.body10m,
                    color = TogedyTheme.colors.green,
                )

                Text(
                    text = if (plan.totalTaskCount != null) "/${plan.totalTaskCount}" else "",
                    style = TogedyTheme.typography.body10m,
                    color = TogedyTheme.colors.gray700,
                )
            }

            HorizontalDivider(color = TogedyTheme.colors.gray200)

            if (showTask && plan.tasks.isNotEmpty() && plan.subjectId in selectedSubjects) {
                plan.tasks.take(5).forEach { task ->
                    val textColor =
                        if (task.isChecked) TogedyTheme.colors.gray500
                        else TogedyTheme.colors.black
                    val textDeco =
                        if (task.isChecked) TextDecoration.LineThrough
                        else null

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp, bottom = 2.dp),
                    ) {
                        Text(
                            text = "•",
                            style = TogedyTheme.typography.body10m,
                            color = textColor,
                            modifier = Modifier.padding(end = 6.dp),
                        )

                        Text(
                            text = task.taskName ?: "",
                            style = TogedyTheme.typography.body10m,
                            color = textColor,
                            textDecoration = textDeco,
                            modifier = Modifier.padding(end = 6.dp),
                        )
                    }

                }
            }

            Spacer(Modifier.height(12.dp))
        }
    }
}

@Preview
@Composable
private fun PlannerContentPreview() {
    TogedyTheme {
        PlannerContent(
            showTask = true,
            plans = listOf(
                PlannerSubject(
                    subjectId = 1L,
                    subjectName = "수학",
                    subjectColor = "#FF0000",
                    tasks = listOf(
                        TaskItem(1L, "문제집 풀기", true),
                        TaskItem(2L, "문제집 풀기2", true),
                    )
                ),
                PlannerSubject(
                    subjectId = 2L,
                    subjectName = "영어",
                    subjectColor = "#00FF00",
                    tasks = listOf(
                        TaskItem(3L, "문제집 풀기", true),
                    )
                ),
            ),
            selectedSubjects = listOf(),
        )
    }
}
