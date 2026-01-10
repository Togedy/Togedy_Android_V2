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
import com.together.study.designsystem.R.drawable.ic_add_24
import com.together.study.designsystem.R.drawable.ic_kebap_menu
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.planner.model.PlannerSubject
import com.together.study.planner.model.Todo
import com.together.study.planner.type.toPlannerSubjectColorOrDefault
import com.together.study.util.noRippleClickable

@Composable
internal fun PlannerItemsScreen(
    subjects: List<PlannerSubject> = listOf(
        PlannerSubject(
            0,
            "카테고리1",
            "SUBJECT_COLOR1",
            listOf(Todo(0, "할 일1"), Todo(1, null), Todo(2, "할 일3"))
        )
    ),
    modifier: Modifier = Modifier,
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
            .padding(14.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { focusManager.clearFocus() }
                )
            },
    ) {
        item {
            Text(
                text = "할 일",
                style = TogedyTheme.typography.body12m,
                color = TogedyTheme.colors.gray800,
            )

            Spacer(Modifier.height(8.dp))
        }

        itemsIndexed(subjects) { index, subject ->
            var todoItems by remember { mutableStateOf(subject.todoItems) }

            SubjectSection(
                subjectName = subject.name,
                subjectColor = subject.color,
                todoItems = todoItems ?: emptyList(),
                onPlusButtonClick = {
                    todoItems = todoItems?.plus(Todo())
                },
                onTodoContentChange = { id, content -> },
                onTodoEditButtonClick = {},
            )

            Spacer(Modifier.height(8.dp))

            if (index == subjects.lastIndex) Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
fun SubjectSection(
    subjectName: String,
    subjectColor: String,
    timer: String? = null,
    todoItems: List<Todo>,
    modifier: Modifier = Modifier,
    onPlusButtonClick: () -> Unit,
    onTodoContentChange: (Long?, String) -> Unit,
    onTodoEditButtonClick: () -> Unit,
) {
    val subjectColor = subjectColor.toPlannerSubjectColorOrDefault()

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

        if (todoItems.isNotEmpty()) Spacer(Modifier.height(16.dp))

        todoItems.forEachIndexed { index, todoItem ->
            var currentTodoContent by remember { mutableStateOf(todoItem.content) }
            val bottomPadding = if (index == todoItems.lastIndex) 0.dp else 12.dp

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = bottomPadding),
                verticalAlignment = Alignment.Top,
            ) {
                val stateColor =
                    when (todoItem.state) {
                        0 -> TogedyTheme.colors.gray300
                        else -> subjectColor
                    }

                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(stateColor, RoundedCornerShape(4.dp))
                )

                Spacer(Modifier.width(8.dp))

                BasicTextField(
                    value = currentTodoContent ?: "",
                    onValueChange = { it ->
                        currentTodoContent = it
                        onTodoContentChange(todoItem.id, it)
                    },
                    textStyle = TogedyTheme.typography.body13m,
                    decorationBox = { innerTextField ->
                        if (currentTodoContent.isNullOrEmpty()) {
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
                        .noRippleClickable(onTodoEditButtonClick),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PlannerItemsScreenPreview() {
    TogedyTheme {
        PlannerItemsScreen()
    }
}