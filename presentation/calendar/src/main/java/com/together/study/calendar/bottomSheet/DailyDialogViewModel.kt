package com.together.study.calendar.bottomSheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.calendar.model.DailyScheduleInfo
import com.together.study.calendar.model.Schedule
import com.together.study.calendar.repository.CalendarRepository
import com.together.study.calendar.repository.UserScheduleRepository
import com.together.study.common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class DailyDialogViewModel @Inject constructor(
    private val calendarRepository: CalendarRepository,
    private val userScheduleRepository: UserScheduleRepository,
) : ViewModel() {
    var isUpdateNeeded = MutableStateFlow(true)
    private val _schedulesInfo = MutableStateFlow(DailyScheduleInfo(0, emptyList()))
    val schedulesInfo = _schedulesInfo.asStateFlow()

    private var lastDailySchedules = emptyList<Schedule>()


    fun fetchDailySchedules(date: LocalDate) = viewModelScope.launch {
        calendarRepository.getDailySchedule(date.toString())
            .onSuccess {
                _schedulesInfo.value = it
                lastDailySchedules = it.dailyScheduleList
            }
            .onFailure(Timber::e)
        changeIsUpdateNeeded(false)
    }

    fun deleteSchedule(scheduleId: Long) = viewModelScope.launch {
        userScheduleRepository.deleteUserSchedule(scheduleId)
            .onSuccess {
                _schedulesInfo.value = _schedulesInfo.value.copy(
                    dailyScheduleList = lastDailySchedules.filterNot { it.scheduleId == scheduleId }
                )
            }
            .onFailure { UiState.Failure(it.message.toString()) }
        changeIsUpdateNeeded(true)
    }

    fun changeIsUpdateNeeded(new: Boolean) {
        isUpdateNeeded.value = new
    }
}
