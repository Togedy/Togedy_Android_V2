package com.together.study.calendar.bottomSheet

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.calendar.component.GrayBoxText
import com.together.study.calendar.component.ScheduleDateTimeSection
import com.together.study.calendar.model.Category
import com.together.study.calendar.model.Schedule
import com.together.study.calendar.type.toCategoryColorOrDefault
import com.together.study.common.ScheduleType
import com.together.study.designsystem.component.TogedyBottomSheet
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.presentation.calendar.R.drawable.ic_category_box
import com.together.study.presentation.calendar.R.drawable.ic_empty_category_box
import com.together.study.util.noRippleClickable
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ScheduleBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    onDoneClick: (Schedule) -> Unit,
    scheduleId: Long? = null,
    scheduleName: String = "",
    startDate: LocalDate = LocalDate.now(),
    startTime: String? = null,
    endDate: LocalDate? = null,
    endTime: String? = null,
    category: Category? = null,
    onEditCategoryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val bottomSheetHeight = screenHeight * 0.63f
    val title = if (scheduleId == null) "일정추가" else "일정수정"

    var scheduleName by remember { mutableStateOf(scheduleName) }
    var startDate by remember { mutableStateOf(startDate) }
    var startTime by remember { mutableStateOf(startTime) }
    var endDate by remember { mutableStateOf(endDate) }
    var endTime by remember { mutableStateOf(endTime) }

    var category by remember { mutableStateOf(category) }
    var isCategoryOpen by remember { mutableStateOf(false) }
    var categorySheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var scheduleMemo by remember { mutableStateOf("") }
    var isMemoOpen by remember { mutableStateOf(false) }
    var memoSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    TogedyBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        title = title,
        showDone = true,
        isDoneActivate = scheduleName.isNotEmpty() && category != null,
        onDoneClick = {
            val startDateTime =
                if (startTime != null) startDate.toString() + startTime
                else startDate.toString()
            val endDateTime =
                if (endTime != null) endDate.toString() + endTime
                else endDate.toString()

            onDoneClick(
                Schedule(
                    scheduleId = scheduleId ?: -1,
                    scheduleType = ScheduleType.USER.label,
                    scheduleName = scheduleName,
                    startDate = startDateTime,
                    endDate = endDateTime,
                    category = category,
                )
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .height(bottomSheetHeight),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            ScheduleNameSection(
                scheduleName = scheduleName,
                categoryColor = TogedyTheme.colors.gray300,
                onNameChange = { scheduleName = it },
            )

            Spacer(Modifier.height(24.dp))

            ScheduleDateTimeSection(
                startDateTime = Pair(startDate, startTime),
                endDateTime = Pair(endDate, endTime),
                onCalendarOpen = { },
                onClockOpen = { },
            )

            Spacer(Modifier.height(24.dp))

            ScheduleCategorySection(
                category = category,
                onCategoryClick = { isCategoryOpen = true },
            )

            Spacer(Modifier.height(24.dp))

            ScheduleMemoSection(
                memo = scheduleMemo,
                onMemoClick = { isMemoOpen = true },
            )
        }

        if (isMemoOpen) {
            MemoBottomSheet(
                sheetState = memoSheetState,
                scheduleMemo = scheduleMemo,
                onValueChange = { scheduleMemo = it },
                onDismissRequest = { isMemoOpen = false },
            )
        }

        if (isCategoryOpen) {
            CategoryBottomSheet(
                sheetState = categorySheetState,
                category = category,
                onDismissRequest = { isCategoryOpen = false },
                onAddCategoryClick = {},
                onEditCategoryClick = onEditCategoryClick,
                onDoneClick = {
                    category = it
                    isCategoryOpen = false
                },
            )
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
