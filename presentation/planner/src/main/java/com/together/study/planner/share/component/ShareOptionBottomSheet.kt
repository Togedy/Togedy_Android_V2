package com.together.study.planner.share.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.ic_check_green
import com.together.study.designsystem.component.TogedyBottomSheet
import com.together.study.designsystem.component.button.TogedyCheckBoxButton
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.planner.model.PlannerSubject
import com.together.study.planner.type.toPlannerSubjectColorOrDefault
import com.together.study.util.noRippleClickable

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
internal fun ShareOptionBottomSheet(
    subjects: List<PlannerSubject>,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    showTodo: Boolean = true,
    selectAllSubject: Boolean = true,
    selectedSubjects: List<Long> = listOf(),
    onShowTodoChanged: () -> Unit,
    onSelectAllSubjectChanged: () -> Unit,
    onSubjectClick: (Long) -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
) {
    TogedyBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        modifier = modifier.padding(bottom = 20.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            Text(
                text = "타임테이블에 표시할 카테고리를 선택하세요",
                style = TogedyTheme.typography.body12m,
                color = TogedyTheme.colors.gray700,
            )

            Spacer(Modifier.height(10.dp))

            TogedyCheckBoxButton(
                isChecked = selectAllSubject,
                text = "전체 선택",
                onCheckBoxClick = onSelectAllSubjectChanged,
            )

            Spacer(Modifier.height(10.dp))

            FlowRow(
                modifier = Modifier,
                overflow = FlowRowOverflow.Visible,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                subjects.forEach { subject ->
                    SubjectItem(
                        subjectName = subject.name,
                        subjectColor = subject.color,
                        isSelected = subject.id in selectedSubjects,
                        onItemClick = { subject.id?.let { onSubjectClick(it) } },
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TogedyCheckBoxButton(
                    isChecked = showTodo,
                    text = "할 일 표시하기",
                    onCheckBoxClick = onShowTodoChanged,
                )

                Text(
                    text = "상위 5개까지 보여요",
                    style = TogedyTheme.typography.body10m,
                    color = TogedyTheme.colors.gray500,
                )
            }
        }
    }
}

@Composable
private fun SubjectItem(
    subjectName: String,
    subjectColor: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit,
) {
    val subjectColor = subjectColor.toPlannerSubjectColorOrDefault()
    val mainItemColor =
        if (isSelected) TogedyTheme.colors.green
        else TogedyTheme.colors.gray500

    Row(
        modifier = modifier
            .border(1.3.dp, mainItemColor, RoundedCornerShape(20.dp))
            .padding(vertical = 6.dp, horizontal = 12.dp)
            .noRippleClickable(onItemClick),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(subjectColor, RoundedCornerShape(2.dp))
                .padding(end = 4.dp),
        )

        Text(
            text = subjectName,
            style = TogedyTheme.typography.body14m,
            color = mainItemColor,
        )

        if (isSelected) {
            Icon(
                imageVector = ImageVector.vectorResource(ic_check_green),
                contentDescription = null,
                tint = TogedyTheme.colors.green,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ShareOptionBottomSheetPreview() {
    TogedyTheme {
        ShareOptionBottomSheet(
            subjects = listOf(
                PlannerSubject(
                    id = 1,
                    name = "국어",
                    color = "SUBJECT_COLOR1",
                    todoItems = null,
                ),
                PlannerSubject(1, "수학1234", "SUBJECT_COLOR2", null),
                PlannerSubject(1, "수asdfasdfsd학", "SUBJECT_COLOR2", null),
                PlannerSubject(1, "수학", "SUBJECT_COLOR2", null),
                PlannerSubject(1, "수sd학", "SUBJECT_COLOR4", null),
                PlannerSubject(1, "수학", "SUBJECT_COLOR7", null),
                PlannerSubject(1, "수학asdfasdfsdf", "SUBJECT_COLOR2", null),
            ),
            onDismissRequest = {},
            showTodo = true,
            selectAllSubject = true,
            selectedSubjects = listOf(),
            onShowTodoChanged = {},
            onSelectAllSubjectChanged = {},
            onSubjectClick = {},
        )
    }
}
