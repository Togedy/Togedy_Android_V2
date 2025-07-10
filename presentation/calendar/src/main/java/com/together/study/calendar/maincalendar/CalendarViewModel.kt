package com.together.study.calendar.maincalendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.calendar.maincalendar.state.CalendarUiState
import com.together.study.calendar.model.DDay
import com.together.study.calendar.model.Schedule
import com.together.study.calendar.repository.CalendarRepository
import com.together.study.common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

const val TAG = "CalendarViewModel"

@HiltViewModel
internal class CalendarViewModel @Inject constructor(
    private val calendarRepository: CalendarRepository,
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
        calendarRepository.getAnnouncement()
            .onSuccess {
                _noticeState.value =
                    if (it.hasAnnouncement) UiState.Success(it.announcement)
                    else UiState.Success("")
            }
            .onFailure { _noticeState.value = UiState.Failure(it.message.toString()) }
    }

    suspend fun getDDay() {
        calendarRepository.getDDay()
            .onSuccess { _dDayState.value = UiState.Success(it) }
            .onFailure { _dDayState.value = UiState.Failure(it.message.toString()) }
    }

    fun getSchedule() = viewModelScope.launch {
        calendarRepository.getMonthlySchedule(month = "2025-01")
            .onSuccess { _scheduleState.value = UiState.Success(it) }
            .onFailure { _scheduleState.value = UiState.Failure(it.message.toString()) }
    }

    fun updateCurrentDate(newDate: LocalDate) {
        _currentDate.update { newDate }
        getSchedule()
    }

    fun updateDailyDialog(date: LocalDate) {
        // TODO: 일별 상세 다이얼로그 open
        Timber.tag("chrin").d("$date 클릭됨")
    }
}
