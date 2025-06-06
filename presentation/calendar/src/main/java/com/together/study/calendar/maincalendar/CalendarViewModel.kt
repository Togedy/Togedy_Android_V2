package com.together.study.calendar.maincalendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.calendar.maincalendar.state.CalendarUiState
import com.together.study.calendar.model.DDay
import com.together.study.calendar.model.Schedule
import com.together.study.common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class CalendarViewModel @Inject constructor(

) : ViewModel() {
    private val _currentDate = MutableStateFlow(LocalDate.now())
    val currentDate = _currentDate.asStateFlow()

    private val _noticeState = MutableStateFlow<UiState<String?>>(UiState.Loading)
    private val _dDayState = MutableStateFlow<UiState<DDay>>(UiState.Loading)
    private val _scheduleState = MutableStateFlow<UiState<List<Schedule>>>(UiState.Loading)

    val calendarUiState: StateFlow<CalendarUiState> = combine(
        _noticeState,
        _dDayState,
        _scheduleState,
    ) { noticeState, dDayState, scheduleState ->
        CalendarUiState(
            noticeState = noticeState,
            dDayState = dDayState,
            scheduleState = scheduleState
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = CalendarUiState(
            noticeState = UiState.Loading,
            dDayState = UiState.Loading,
            scheduleState = UiState.Loading,
        )
    )

    suspend fun getCalendarInfo() {
        getNotice()
        getDDay()
        getSchedule()
    }

    suspend fun getNotice() {
        // TODO: 추후 API 연결
        _noticeState.value = UiState.Success("알림입니다! 공지사항입니다!")
    }

    suspend fun getDDay() {
        // TODO: 추후 API 연결
        _dDayState.value = UiState.Success(DDay.mock)
    }

    suspend fun getSchedule() {
        // TODO: 추후 API 연결
        _scheduleState.value = UiState.Success(Schedule.mock)
    }

    fun updateCurrentDate(newDate: LocalDate) {
        _currentDate.update { newDate }
    }

    fun updateDailyDialog(date: LocalDate) {
        Timber.tag("chrin").d("$date 클릭됨")
    }
}