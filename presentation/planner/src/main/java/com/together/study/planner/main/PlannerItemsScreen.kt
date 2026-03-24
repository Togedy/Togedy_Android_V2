package com.together.study.planner.main

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.common.state.UiState
import com.together.study.common.type.planner.toPlannerSubjectColorOrDefault
import com.together.study.designsystem.R.drawable.ic_add_24
import com.together.study.designsystem.R.drawable.ic_kebap_menu
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.planner.model.PlannerSubject
import com.together.study.planner.model.TaskItem
import com.together.study.util.asColor
import com.together.study.util.noRippleClickable

@Composable
internal fun PlannerItemsScreen(
    plannerSubjectState: UiState<List<PlannerSubject>>,
    modifier: Modifier = Modifier,
    onTaskNameChange: (Long?, String) -> Unit,
) {
    val listState = rememberLazyListState()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(listState.isScrollInProgress) {
        if (listState.isScrollInProgress) {
            focusManager.clearFocus()
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.gray100)
            .padding(top = 14.dp)
            .padding(horizontal = 14.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { focusManager.clearFocus() }
                )
            },
    ) {
        when (plannerSubjectState) {
            is UiState.Loading -> {}

            is UiState.Failure -> {}

            is UiState.Success -> {
                val subjects = plannerSubjectState.data

                item {
                    Text(
                        text = "할 일",
                        style = TogedyTheme.typography.body12m,
                        color = TogedyTheme.colors.gray800,
                    )

                    Spacer(Modifier.height(8.dp))
                }

                itemsIndexed(subjects) { index, subject ->
                    var taskItems by remember(subject.tasks) { mutableStateOf(subject.tasks) }

                    SubjectSection(
                        subjectName = subject.subjectName,
                        subjectColor = subject.subjectColor,
                        taskItems = taskItems,
                        onPlusButtonClick = {
                            taskItems = taskItems.plus(TaskItem())
                        },
                        onTaskNameChange = onTaskNameChange,
                        onTaskEditButtonClick = {},
                    )

                    Spacer(Modifier.height(8.dp))

                    if (index == subjects.lastIndex) Spacer(Modifier.height(20.dp))
                }
            }

            else -> {}
        }
    }
}

@Composable
fun SubjectSection(
    subjectName: String,
    subjectColor: String,
    taskItems: List<TaskItem>,
    modifier: Modifier = Modifier,
    timer: String? = null,
    onPlusButtonClick: () -> Unit,
    onTaskNameChange: (Long?, String) -> Unit,
    onTaskEditButtonClick: () -> Unit,
) {
    val subjectColor = subjectColor.toPlannerSubjectColorOrDefault().asColor()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(TogedyTheme.colors.white, RoundedCornerShape(8.dp))
            .padding(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(2.dp, 16.dp)
                    .background(subjectColor, RoundedCornerShape(4.dp))
            )

            Spacer(Modifier.width(8.dp))

            Text(
                text = subjectName,
                style = TogedyTheme.typography.body14b,
                color = TogedyTheme.colors.black,
            )

            Spacer(Modifier.width(8.dp))

            Icon(
                imageVector = ImageVector.vectorResource(ic_add_24),
                contentDescription = "투두 추가 버튼",
                tint = TogedyTheme.colors.gray700,
                modifier = Modifier
                    .background(
                        TogedyTheme.colors.gray200,
                        RoundedCornerShape(50.dp)
                    )
                    .size(18.dp)
                    .noRippleClickable(onPlusButtonClick)
            )

            Spacer(Modifier.weight(1f))

            Text(
                text = timer ?: "00:00:00",
                style = TogedyTheme.typography.body13m,
                color = TogedyTheme.colors.gray500,
            )
        }

        if (taskItems.isNotEmpty()) Spacer(Modifier.height(16.dp))

        taskItems.forEachIndexed { index, task ->
            var currentName by remember(task.taskName) { mutableStateOf(task.taskName) }
            val bottomPadding = if (index == taskItems.lastIndex) 0.dp else 12.dp

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = bottomPadding),
                verticalAlignment = Alignment.Top,
            ) {
                val stateColor =
                    if (task.isChecked) subjectColor
                    else TogedyTheme.colors.gray300

                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(stateColor, RoundedCornerShape(4.dp))
                )

                Spacer(Modifier.width(8.dp))

                BasicTextField(
                    value = currentName ?: "",
                    onValueChange = { new ->
                        currentName = new
                        onTaskNameChange(task.taskId, new)
                    },
                    textStyle = TogedyTheme.typography.body13m,
                    decorationBox = { innerTextField ->
                        if (currentName.isNullOrEmpty()) {
                            Text(
                                text = "To do...",
                                style = TogedyTheme.typography.body13m,
                                color = TogedyTheme.colors.gray400,
                            )
                        }
                        innerTextField()
                    },
                    modifier = Modifier.weight(1f),
                )

                Spacer(Modifier.width(8.dp))

                Icon(
                    imageVector = ImageVector.vectorResource(ic_kebap_menu),
                    contentDescription = "투두 편집 버튼",
                    tint = TogedyTheme.colors.gray800,
                    modifier = Modifier
                        .size(16.dp)
                        .noRippleClickable(onTaskEditButtonClick),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PlannerItemsScreenPreview() {
    TogedyTheme {
        PlannerItemsScreen(
            plannerSubjectState = UiState.Success(
                listOf(
                    PlannerSubject(
                        subjectId = 1L,
                        subjectName = "과목명",
                        subjectColor = "#FF0000",
                        tasks = listOf(
                            TaskItem(
                                taskId = 1L,
                                taskName = "투두명",
                                isChecked = false,
                            ),
                            TaskItem(
                                taskId = 2L,
                                taskName = "투두명",
                                isChecked = true,
                            ),
                        )
                    )
                )
            ),
            onTaskNameChange = { _, _ -> },
        )
    }
}