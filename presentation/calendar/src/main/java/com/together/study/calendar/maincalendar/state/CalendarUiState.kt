package com.together.study.calendar.maincalendar.state

import androidx.compose.runtime.Immutable
import com.together.study.calendar.model.DDay
import com.together.study.calendar.model.Schedule
import com.together.study.common.state.UiState

@Immutable
data class CalendarUiState(
    val noticeState: UiState<String?>,
    val dDayState: UiState<DDay>,
    val scheduleState: UiState<List<Schedule>>,
) {
    val isLoaded: UiState<Unit>
        get() = when {
            noticeState is UiState.Loading || dDayState is UiState.Loading || scheduleState is UiState.Loading
                -> UiState.Loading

            noticeState is UiState.Success && dDayState is UiState.Success && scheduleState is UiState.Success
                -> UiState.Success(Unit)

            noticeState is UiState.Failure || dDayState is UiState.Failure || scheduleState is UiState.Failure
                -> UiState.Failure("failed to load")

            else -> UiState.Empty
        }
}
