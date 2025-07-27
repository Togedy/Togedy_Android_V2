package com.together.study.calendar.maincalendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.calendar.model.Schedule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class DailyDialogViewModel @Inject constructor(

) : ViewModel() {
    private val _dailySchedules = MutableStateFlow<List<Schedule>>(emptyList())
    val dailySchedules = _dailySchedules.asStateFlow()

    private var lastDailySchedules = emptyList<Schedule>()

    fun fetchDailySchedules(date: LocalDate) = viewModelScope.launch {
        // TODO: 추후 API 연결
        lastDailySchedules = emptyList()
    }

    fun deleteSchedule(scheduleId: Long) = viewModelScope.launch {
        _dailySchedules.update {
            lastDailySchedules.filterNot { it.scheduleId == scheduleId }
        }
        // TODO: 추후 API 연결
    }
}
