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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.planner.model.PlannerSubject
import com.together.study.planner.model.Todo
import com.together.study.planner.type.toPlannerSubjectColorOrDefault

@Composable
internal fun PlannerContent(
    showTodo: Boolean,
    plans: List<PlannerSubject>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.wrapContentHeight(),
    ) {
        plans.forEach { plan ->
            val subjectColor = plan.color.toPlannerSubjectColorOrDefault()

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

                Text(
                    text = plan.name,
                    style = TogedyTheme.typography.body12m,
                    color = TogedyTheme.colors.gray700,
                )

                Spacer(Modifier.weight(1f))

                Text(
                    text = "${plan.todoItems?.size ?: 0}",
                    style = TogedyTheme.typography.body10m,
                    color = TogedyTheme.colors.green,
                )

                Text(
                    text = "/${plan.todoItems?.size ?: 0}",
                    style = TogedyTheme.typography.body10m,
                    color = TogedyTheme.colors.gray700,
                )
            }

            HorizontalDivider(color = TogedyTheme.colors.gray200)

            if (showTodo && plan.todoItems != null) {
                plan.todoItems!!.take(5).forEach { todo ->
                    val textColor =
                        if (todo.state == 0) TogedyTheme.colors.gray500
                        else TogedyTheme.colors.black
                    val textDeco =
                        if (todo.state != 0) TextDecoration.LineThrough
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
                            text = todo.content ?: "알수없음",
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
            showTodo = true,
            plans = listOf(
                PlannerSubject(
                    id = 1,
                    name = "국어",
                    color = "SUBJECT_COLOR1",
                    todoItems = listOf(
                        Todo(1, "할 일1", 0),
                        Todo(2, "EBS 수능", 1),
                    )
                ),
                PlannerSubject(
                    id = 1,
                    name = "수학",
                    color = "SUBJECT_COLOR2",
                    todoItems = null,
                ),
            ),
        )
    }
}
