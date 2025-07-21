package com.together.study.calendar.schedule_bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.together.study.calendar.category.CategoryDetailBottomSheet
import com.together.study.calendar.component.GrayBoxText
import com.together.study.calendar.component.ScheduleDateTimeSection
import com.together.study.calendar.model.Category
import com.together.study.calendar.model.UserSchedule
import com.together.study.calendar.schedule_bottomsheet.state.ScheduleSubBottomSheetType
import com.together.study.calendar.type.toCategoryColorOrDefault
import com.together.study.designsystem.component.TogedyBottomSheet
import com.together.study.designsystem.component.button.TogedyToggleButton
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.presentation.calendar.R.drawable.ic_category_box
import com.together.study.presentation.calendar.R.drawable.ic_empty_category_box
import com.together.study.util.noRippleClickable
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ScheduleBottomSheet(
    onDismissRequest: () -> Unit,
    onDoneClick: (UserSchedule) -> Unit,
    scheduleId: Long? = null,
    startDate: LocalDate = LocalDate.now(),
    onEditCategoryClick: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    modifier: Modifier = Modifier,
    viewModel: ScheduleBottomSheetViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val bottomSheetState by viewModel.bottomSheetState.collectAsStateWithLifecycle()
    val title = if (scheduleId == null) "일정추가" else "일정수정"

    LaunchedEffect(Unit) {
        if (scheduleId != null) viewModel.getUserSchedule(scheduleId, startDate)
        sheetState.expand()
    }

    LaunchedEffect(uiState.newInfo) { viewModel.checkDoneActivated() }

    with(uiState.newInfo) {
        TogedyBottomSheet(
            sheetState = sheetState,
            onDismissRequest = onDismissRequest,
            title = title,
            showDone = true,
            isDoneActivate = uiState.isDoneActivated,
            onDoneClick = {
                if (scheduleId == null) viewModel.postUserSchedule()
                else viewModel.patchUserSchedule(scheduleId)
                onDoneClick(viewModel.toUserSchedule())
            },
            modifier = modifier.fillMaxHeight(0.62f),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                ScheduleNameSection(
                    scheduleName = userScheduleName,
                    categoryColor = categoryValue?.categoryColor.toCategoryColorOrDefault(),
                    onNameChange = { viewModel.updateScheduleName(it) },
                )

                Spacer(Modifier.height(24.dp))

                ScheduleDateTimeSection(
                    startDateTime = Pair(startDateValue, startTimeValue),
                    endDateTime = Pair(endDateValue, endTimeValue),
                    onCalendarOpen = {
                        viewModel.updateBottomSheetVisibility(
                            ScheduleSubBottomSheetType.CALENDAR
                        )
                    },
                    onClockOpen = { },
                )

                Spacer(Modifier.height(24.dp))

                ScheduleCategorySection(
                    category = categoryValue,
                    onCategoryClick = {
                        viewModel.updateBottomSheetVisibility(
                            ScheduleSubBottomSheetType.CATEGORY
                        )
                    },
                )

                Spacer(Modifier.height(24.dp))

                ScheduleMemoSection(
                    memo = memoValue ?: "",
                    onMemoClick = {
                        viewModel.updateBottomSheetVisibility(
                            ScheduleSubBottomSheetType.MEMO
                        )
                    },
                )

                Spacer(Modifier.height(24.dp))

                DDaySection(
                    dDay = dDayValue,
                    onClick = { viewModel.updateDDay() }
                )
            }

            with(bottomSheetState) {
                if (isMemoOpen) {
                    MemoBottomSheet(
                        scheduleMemo = memoValue ?: "",
                        onValueChange = { viewModel.updateMemo(it) },
                        onDismissRequest = {
                            viewModel.updateBottomSheetVisibility(
                                ScheduleSubBottomSheetType.MEMO
                            )
                        },
                    )
                }

                if (isCategoryOpen) {
                    CategoryBottomSheet(
                        category = null,
                        onDismissRequest = {
                            viewModel.updateBottomSheetVisibility(
                                ScheduleSubBottomSheetType.CATEGORY
                            )
                        },
                        onAddCategoryClick = {},
                        onEditCategoryClick = onEditCategoryClick,
                        onDoneClick = {
                            viewModel::updateCategory
                            viewModel.updateBottomSheetVisibility(
                                ScheduleSubBottomSheetType.CATEGORY
                            )
                        },
                    )
                }

                if (isCategoryAddOpen) {
                    CategoryDetailBottomSheet(
                        sheetState = sheetState,
                        category = null,
                        onDismissRequest = {
                            viewModel.updateBottomSheetVisibility(ScheduleSubBottomSheetType.CATEGORY_ADD)
                        },
                        onDoneClick = { category ->
                            viewModel.updateBottomSheetVisibility(ScheduleSubBottomSheetType.CATEGORY_ADD)
                        },
                    )
                }

                if (isCalendarOpen) {
                    CalendarBottomSheet(
                        startDate = startDateValue,
                        endDate = endDateValue,
                        onDismissRequest = {
                            viewModel.updateBottomSheetVisibility(
                                ScheduleSubBottomSheetType.CALENDAR
                            )
                        },
                        onDoneClick = { start, end ->
                            viewModel.updateBottomSheetVisibility(
                                ScheduleSubBottomSheetType.CALENDAR
                            )
                            viewModel.updateStartDate(start)
                            viewModel.updateEndDate(end)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ScheduleNameSection(
    scheduleName: String,
    categoryColor: Color,
    onNameChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val textStyle = TogedyTheme.typography.body14m

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .width(3.dp)
                .height(32.dp)
                .background(color = categoryColor, shape = RoundedCornerShape(16.dp)),
        )

        Spacer(Modifier.width(8.dp))

        BasicTextField( // TODO: 추후 컴포넌트로 분리
            value = scheduleName,
            onValueChange = onNameChange,
            textStyle = textStyle,
            singleLine = true,
            modifier = Modifier.weight(1f),
            decorationBox = { innerTextField ->
                if (scheduleName.isEmpty()) {
                    Text(
                        text = "일정...",
                        style = textStyle.copy(color = TogedyTheme.colors.gray300)
                    )
                }
                innerTextField()
            }
        )
    }
}

@Composable
private fun ScheduleCategorySection(
    category: Category?,
    onCategoryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .noRippleClickable(onCategoryClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (category == null) {
            Icon(
                imageVector = ImageVector.vectorResource(ic_empty_category_box),
                contentDescription = null,
                tint = TogedyTheme.colors.gray300,
            )

            Spacer(Modifier.width(8.dp))

            GrayBoxText(
                text = "카테고리",
                textStyle = TogedyTheme.typography.chip14b,
                textColor = TogedyTheme.colors.gray400,
                onTextClick = onCategoryClick,
            )
        } else {
            Icon(
                imageVector = ImageVector.vectorResource(ic_category_box),
                contentDescription = null,
                tint = category.categoryColor.toCategoryColorOrDefault(),
            )

            Spacer(Modifier.width(8.dp))

            GrayBoxText(
                text = category.categoryName.toString(),
                textStyle = TogedyTheme.typography.chip14b,
                textColor = category.categoryColor.toCategoryColorOrDefault(),
                onTextClick = onCategoryClick,
            )
        }
    }
}

@Composable
private fun ScheduleMemoSection(
    memo: String,
    onMemoClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (memo.isEmpty()) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .noRippleClickable(onMemoClick),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(TogedyTheme.colors.gray500),
            )

            Spacer(Modifier.width(8.dp))

            Text(
                text = "메모...",
                style = TogedyTheme.typography.body14m.copy(TogedyTheme.colors.gray500),
            )
        }
    } else {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(TogedyTheme.colors.gray200, RoundedCornerShape(6.dp))
                .padding(12.dp),
            contentAlignment = Alignment.TopStart,
        ) {
            Text(
                text = memo,
                style = TogedyTheme.typography.body14m.copy(TogedyTheme.colors.gray700),
            )

            Text(
                text = "편집",
                style = TogedyTheme.typography.body12m.copy(TogedyTheme.colors.gray700),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .background(TogedyTheme.colors.gray300, RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .noRippleClickable(onMemoClick),
            )
        }
    }
}

@Composable
private fun DDaySection(
    dDay: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        Text(
            text = "디데이로 설정하기",
            style = TogedyTheme.typography.body14m.copy(TogedyTheme.colors.gray500)
        )

        Spacer(Modifier.width(4.dp))

        TogedyToggleButton(
            isToggleOn = dDay,
            onToggleClick = onClick,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ScheduleBottomSheetPreview(modifier: Modifier = Modifier) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    TogedyTheme {
        ScheduleBottomSheet(
            scheduleId = null,
            sheetState = sheetState,
            onDismissRequest = {},
            onDoneClick = {},
            onEditCategoryClick = {},
            modifier = modifier,
        )
    }
}
