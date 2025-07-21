package com.together.study.calendar.schedule_bottomsheet.state

import androidx.compose.runtime.Immutable
import com.together.study.calendar.model.Category
import java.time.LocalDate

@Immutable
data class ScheduleBottomSheetUiState(
    val originalInfo: UserScheduleInfo = UserScheduleInfo(),
    val newInfo: UserScheduleInfo = UserScheduleInfo(),
    val isDoneActivated: Boolean = false,
)

data class UserScheduleInfo(
    var userScheduleName: String = "",
    var startDateValue: LocalDate = LocalDate.now(),
    var startTimeValue: String? = null,
    var endDateValue: LocalDate? = null,
    var endTimeValue: String? = null,
    var categoryValue: Category? = null,
    var memoValue: String? = null,
    var dDayValue: Boolean = false,
)
