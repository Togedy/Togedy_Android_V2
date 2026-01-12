package com.together.study.calendar.schedule_bottomsheet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.together.study.calendar.category.CategoryDetailBottomSheet
import com.together.study.calendar.model.Category
import com.together.study.calendar.schedule_bottomsheet.state.ScheduleBottomSheetUiState
import com.together.study.calendar.schedule_bottomsheet.state.ScheduleSubBottomSheetState
import com.together.study.calendar.schedule_bottomsheet.state.ScheduleSubSheetType
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ScheduleSubScreen(
    bottomSheetState: ScheduleSubBottomSheetState,
    uiState: ScheduleBottomSheetUiState,
    onDismissRequest: (ScheduleSubSheetType) -> Unit,
    onMemoChange: (String) -> Unit,
    onPostCategory: (String, String) -> Unit,
    onEditCategoryClick: () -> Unit,
    onCategoryChange: (Category) -> Unit,
    onStartDateChange: (LocalDate) -> Unit,
    onEndDateChange: (LocalDate?) -> Unit,
) {
    with(bottomSheetState) {
        if (isMemoOpen) {
            MemoBottomSheet(
                scheduleMemo = uiState.newInfo.memoValue ?: "",
                onValueChange = onMemoChange,
                onDismissRequest = {
                    onDismissRequest(ScheduleSubSheetType.MEMO)
                },
            )
        }

        if (isCategoryOpen) {
            CategoryBottomSheet(
                category = uiState.newInfo.categoryValue,
                categories = uiState.categories,
                onDismissRequest = {
                    onDismissRequest(ScheduleSubSheetType.CATEGORY)
                },
                onAddCategoryClick = {
                    onDismissRequest(ScheduleSubSheetType.CATEGORY_ADD)
                },
                onEditCategoryClick = onEditCategoryClick,
                onDoneClick = { category ->
                    onCategoryChange(category)
                    onDismissRequest(ScheduleSubSheetType.CATEGORY)
                },
            )
        }

        if (isCategoryAddOpen) {
            CategoryDetailBottomSheet(
                category = null,
                onDismissRequest = {
                    onDismissRequest(ScheduleSubSheetType.CATEGORY_ADD)
                },
                onDoneClick = { category ->
                    onPostCategory(category.categoryName!!, category.categoryColor!!)
                    onDismissRequest(ScheduleSubSheetType.CATEGORY_ADD)
                },
            )
        }

        if (isCalendarOpen) {
            CalendarBottomSheet(
                startDate = uiState.newInfo.startDateValue,
                endDate = uiState.newInfo.endDateValue,
                onDismissRequest = {
                    onDismissRequest(ScheduleSubSheetType.CALENDAR)
                },
                onDoneClick = { start, end ->
                    onDismissRequest(ScheduleSubSheetType.CALENDAR)
                    onStartDateChange(start)
                    onEndDateChange(end)
                }
            )
        }
    }
}
