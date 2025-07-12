package com.together.study.calendar.maincalendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.calendar.model.Schedule
import com.together.study.calendar.repository.UserScheduleRepository
import com.together.study.common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class DailyDialogViewModel @Inject constructor(
    private val userScheduleRepository: UserScheduleRepository,
) : ViewModel() {
    private val _dailySchedules = MutableStateFlow<List<Schedule>>(emptyList())
    val dailySchedules = _dailySchedules.asStateFlow()

    private var lastDailySchedules = emptyList<Schedule>()

    fun fetchDailySchedules(date: LocalDate) = viewModelScope.launch {
        // TODO: 추후 API 연결
        lastDailySchedules = Schedule.mock
    }

    fun deleteSchedule(scheduleId: Long) = viewModelScope.launch {
        userScheduleRepository.deleteUserSchedule(scheduleId)
            .onSuccess {
                _dailySchedules.update {
                    lastDailySchedules.filterNot { it.scheduleId == scheduleId }
                }
            }
            .onFailure { UiState.Failure(it.message.toString()) }
    }
}
