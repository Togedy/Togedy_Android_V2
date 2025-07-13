package com.together.study.calendar.bottomSheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.calendar.model.Category
import com.together.study.calendar.model.UserSchedule
import com.together.study.calendar.repository.UserScheduleRepository
import com.together.study.calendar.state.ScheduleBottomSheetUiState
import com.together.study.calendar.state.ScheduleSubBottomSheetState
import com.together.study.calendar.state.ScheduleSubBottomSheetType
import com.together.study.calendar.state.UserScheduleInfo
import com.together.study.common.state.UiState
import com.together.study.util.toLocalDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class ScheduleBottomSheetViewModel @Inject constructor(
    private val userScheduleRepository: UserScheduleRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ScheduleBottomSheetUiState())
    val uiState = _uiState.asStateFlow()
    private val _bottomSheetState: MutableStateFlow<ScheduleSubBottomSheetState> =
        MutableStateFlow(ScheduleSubBottomSheetState())
    val bottomSheetState: StateFlow<ScheduleSubBottomSheetState> = _bottomSheetState.asStateFlow()


    fun getUserSchedule(scheduleId: Long, date: LocalDate) =
        viewModelScope.launch {
            userScheduleRepository.getUserSchedule(scheduleId)
                .onSuccess { it ->
                    val response = UserScheduleInfo(
                        userScheduleName = it.userScheduleName,
                        startDateValue = it.startDate.toLocalDate() ?: date,
                        startTimeValue = it.startTime,
                        endDateValue = it.endDate.toLocalDate() ?: date,
                        endTimeValue = it.endTime,
                        memoValue = it.memo,
                        categoryIdValue = it.categoryId,
                        dDayValue = it.dDay,
                    )
                    _uiState.update { it.copy(originalInfo = response, newInfo = response) }
                }
                .onFailure { UiState.Failure(it.message.toString()) }
        }

    fun updateScheduleName(new: String) = { _uiState.value.newInfo.userScheduleName = new }
    fun updateStartDate(new: LocalDate) = { _uiState.value.newInfo.startDateValue = new }
    fun updateStartTime(new: String?) = { _uiState.value.newInfo.startTimeValue = new }
    fun updateEndDate(new: LocalDate?) = { _uiState.value.newInfo.endDateValue = new }
    fun updateEndTime(new: String?) = { _uiState.value.newInfo.endTimeValue = new }
    fun updateCategory(new: Category) = { _uiState.value.newInfo.categoryIdValue = new.categoryId }
    fun updateMemo(new: String?) = { _uiState.value.newInfo.memoValue = new }
    fun updateDDay() = { _uiState.value.newInfo.dDayValue = !_uiState.value.newInfo.dDayValue }

    fun checkDoneActivated() {
        _uiState.update {
            it.copy(
                isDoneActivated = _uiState.value.originalInfo != _uiState.value.newInfo
                        && _uiState.value.newInfo.userScheduleName.isNotEmpty()
                        && _uiState.value.newInfo.categoryIdValue != null
            )
        }
    }

    fun postUserSchedule() = viewModelScope.launch {
        userScheduleRepository.postUserSchedule(toUserSchedule())
            .onSuccess { Timber.tag("[okhttp] Schedule API - SUCCESS").d("$it") }
            .onFailure { Timber.tag("[okhttp] Schedule API - FAILURE").d("${it.message}") }

    }

    fun patchUserSchedule(scheduleId: Long) = viewModelScope.launch {
        userScheduleRepository.patchUserSchedule(scheduleId, toUserSchedule())
            .onSuccess { Timber.tag("[okhttp] Schedule API - SUCCESS").d("$it") }
            .onFailure { Timber.tag("[okhttp] Schedule API - FAILURE").d("${it.message}") }

    }

    fun updateBottomSheetVisibility(type: ScheduleSubBottomSheetType) {
        when (type) {
            ScheduleSubBottomSheetType.CALENDAR -> {
                _bottomSheetState.update {
                    it.copy(isCalendarOpen = !_bottomSheetState.value.isCalendarOpen)
                }
            }

            ScheduleSubBottomSheetType.CATEGORY -> {
                _bottomSheetState.update {
                    it.copy(isCategoryOpen = !_bottomSheetState.value.isCategoryOpen)
                }
            }

            ScheduleSubBottomSheetType.MEMO -> {
                _bottomSheetState.update {
                    it.copy(isMemoOpen = !_bottomSheetState.value.isMemoOpen)
                }
            }

            else -> {}
        }
    }

    fun toUserSchedule(): UserSchedule {
        with(_uiState.value.newInfo) {
            return UserSchedule(
                userScheduleName = userScheduleName,
                startDate = startDateValue.toString(),
                startTime = startTimeValue,
                endDate = endDateValue.toString(),
                endTime = endTimeValue,
                memo = memoValue,
                categoryId = categoryIdValue!!,
                dDay = dDayValue,
            )
        }
    }
}
