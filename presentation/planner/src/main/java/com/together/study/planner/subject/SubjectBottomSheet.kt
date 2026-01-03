package com.together.study.planner.subject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.component.TogedyBottomSheet
import com.together.study.designsystem.component.button.AddButton
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.planner.component.SubjectItem
import com.together.study.planner.model.PlannerSubject
import com.together.study.util.noRippleClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SubjectBottomSheet(
    plannerSubject: PlannerSubject?,
    plannerSubjects: List<PlannerSubject>,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onDismissRequest: () -> Unit,
    onDoneClick: (PlannerSubject) -> Unit,
    onAddSubjectClick: () -> Unit,
    onEditSubjectClick: () -> Unit,
) {
    var selectedSubject by remember { mutableStateOf(plannerSubject) }

    LaunchedEffect(Unit) { sheetState.expand() }

    TogedyBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        title = "과목",
        showDone = true,
        isDoneActivate = selectedSubject != null,
        onDoneClick = { onDoneClick(selectedSubject!!) },
        modifier = modifier.fillMaxHeight(0.528f),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                AddButton(
                    title = "과목 추가",
                    onClick = onAddSubjectClick,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                ) {
                    Spacer(Modifier.weight(1f))

                    Text(
                        text = "과목 편집 >",
                        style = TogedyTheme.typography.toast12sb.copy(TogedyTheme.colors.green),
                        modifier = Modifier.noRippleClickable(onEditSubjectClick),
                    )
                }
            }

            items(plannerSubjects) { subjectItem ->
                SubjectItem(
                    plannerSubject = subjectItem,
                    isSubjectSelected = subjectItem.id == selectedSubject?.id,
                    onSubjectClick = { selectedSubject = subjectItem },
                )
            }

            item {
                Spacer(Modifier.height(20.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun SubjectBottomSheetPreview(modifier: Modifier = Modifier) {
    TogedyTheme {
        SubjectBottomSheet(
            plannerSubject = null,
            plannerSubjects = emptyList(),
            onDismissRequest = {},
            onDoneClick = {},
            onAddSubjectClick = {},
            onEditSubjectClick = {},
            modifier = modifier,
        )
    }
}
