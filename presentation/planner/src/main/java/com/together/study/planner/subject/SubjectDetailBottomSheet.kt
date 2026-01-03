package com.together.study.planner.subject

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.component.TogedyBottomSheet
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.planner.model.PlannerSubject
import com.together.study.planner.type.PlannerSubjectColor
import com.together.study.planner.type.toPlannerSubjectColorEnum
import com.together.study.planner.type.toPlannerSubjectColorOrDefault
import com.together.study.util.noRippleClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SubjectDetailBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    plannerSubject: PlannerSubject? = null,
    onDismissRequest: () -> Unit,
    onDoneClick: (PlannerSubject) -> Unit,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val bottomSheetHeight = screenHeight * 0.765f

    val title = if (plannerSubject == null) "과목 추가" else "과목 수정"
    val textStyle = TogedyTheme.typography.title16sb

    var subjectName by remember { mutableStateOf(plannerSubject?.name ?: "") }
    var selectedColor by remember { mutableStateOf(plannerSubject?.color ?: "SUBJECT_COLOR1") }

    TogedyBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        title = title,
        showDone = true,
        isDoneActivate = subjectName.isNotEmpty(),
        onDoneClick = {
            if (plannerSubject == null) onDoneClick(
                PlannerSubject(
                    null,
                    subjectName,
                    selectedColor,
                    emptyList()
                )
            )
            else onDoneClick(
                PlannerSubject(
                    plannerSubject.id,
                    subjectName,
                    selectedColor,
                    emptyList()
                )
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .height(bottomSheetHeight),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .padding(2.dp)
                        .size(16.dp)
                        .background(
                            selectedColor.toPlannerSubjectColorOrDefault(),
                            RoundedCornerShape(6.dp)
                        ),
                )

                Spacer(Modifier.width(4.dp))

                BasicTextField(
                    value = subjectName,
                    onValueChange = { if (subjectName.length < 20) subjectName = it },
                    textStyle = textStyle,
                    decorationBox = { innerTextField ->
                        if (subjectName.isEmpty()) {
                            Text(
                                text = "제목을 입력하세요..",
                                style = textStyle.copy(color = TogedyTheme.colors.gray300)
                            )
                        }
                        innerTextField()
                    }
                )
            }

            Spacer(Modifier.height(24.dp))

            ColorPickerGrid(
                selectedColor = selectedColor.toPlannerSubjectColorEnum(),
                onColorSelected = { selectedColor = it.name },
            )
        }
    }
}

@Composable
fun ColorPickerGrid(
    selectedColor: PlannerSubjectColor,
    onColorSelected: (PlannerSubjectColor) -> Unit,
) {
    val colors = PlannerSubjectColor.entries.filter { it != PlannerSubjectColor.UNKNOWN_COLOR }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(TogedyTheme.colors.gray50, RoundedCornerShape(16.dp))
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
    ) {
        for (row in colors.chunked(6)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                for (color in row) {
                    ColorItem(
                        color = color,
                        isSelected = color == selectedColor,
                        onClick = { onColorSelected(color) },
                    )
                }
            }
        }
    }
}

@Composable
fun ColorItem(
    color: PlannerSubjectColor,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val subjectColor = color.color
    val borderColor = if (isSelected) subjectColor else Color.Transparent
    val borderWidth = 2.dp
    val outerBorderWidth = 2.dp
    val roundedCornerShape = RoundedCornerShape(10.dp)

    Box(
        modifier = Modifier
            .size(40.dp)
            .border(outerBorderWidth, borderColor, roundedCornerShape)
            .padding(2.dp)
            .border(borderWidth, Color.Transparent, roundedCornerShape)
            .padding(2.dp)
            .background(subjectColor, RoundedCornerShape(6.dp))
            .noRippleClickable(onClick),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun AddingSubjectDetailBottomSheetPreview(modifier: Modifier = Modifier) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    TogedyTheme {
        SubjectDetailBottomSheet(
            sheetState = sheetState,
            plannerSubject = null,
            onDismissRequest = {},
            onDoneClick = {},
            modifier = modifier,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun EditingSubjectDetailBottomSheetPreview(modifier: Modifier = Modifier) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    TogedyTheme {
        SubjectDetailBottomSheet(
            sheetState = sheetState,
            plannerSubject = PlannerSubject(0, "수학", "SUBJECT_COLOR2", emptyList()),
            onDismissRequest = {},
            onDoneClick = {},
            modifier = modifier,
        )
    }
}