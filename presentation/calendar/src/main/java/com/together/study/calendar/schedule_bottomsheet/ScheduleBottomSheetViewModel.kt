package com.together.study.calendar.schedule_bottomsheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.calendar.model.Category
import com.together.study.calendar.model.UserSchedule
import com.together.study.calendar.repository.UserScheduleRepository
import com.together.study.calendar.schedule_bottomsheet.state.ScheduleBottomSheetUiState
import com.together.study.calendar.schedule_bottomsheet.state.ScheduleSubBottomSheetState
import com.together.study.calendar.schedule_bottomsheet.state.ScheduleSubSheetType
import com.together.study.calendar.schedule_bottomsheet.state.UserScheduleInfo
import com.together.study.calendar.usecase.GetCategoryUseCase
import com.together.study.calendar.usecase.PostCategoryUseCase
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
    private val getCategoryUseCase: GetCategoryUseCase,
    private val postCategoryUseCase: PostCategoryUseCase,
    private val userScheduleRepository: UserScheduleRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ScheduleBottomSheetUiState())
    val uiState = _uiState.asStateFlow()
    private val _bottomSheetState: MutableStateFlow<ScheduleSubBottomSheetState> =
        MutableStateFlow(ScheduleSubBottomSheetState())
    val bottomSheetState: StateFlow<ScheduleSubBottomSheetState> = _bottomSheetState.asStateFlow()

    fun getUserSchedule(scheduleId: Long, date: LocalDate) = viewModelScope.launch {
        userScheduleRepository.getUserSchedule(scheduleId)
            .onSuccess { it ->
                val response = UserScheduleInfo(
                    userScheduleName = it.userScheduleName,
                    startDateValue = it.startDate.toLocalDate() ?: date,
                    startTimeValue = it.startTime,
                    endDateValue = it.endDate.toLocalDate() ?: date,
                    endTimeValue = it.endTime,
                    memoValue = it.memo,
                    categoryValue = it.category,
                    dDayValue = it.dDay,
                )
                _uiState.update { it.copy(originalInfo = response, newInfo = response) }
            }
            .onFailure { UiState.Failure(it.message.toString()) }
    }

    fun initUserSchedule() {
        _uiState.update { it.copy(originalInfo = UserScheduleInfo(), newInfo = UserScheduleInfo()) }
    }

    fun updateScheduleName(new: String) {
        _uiState.update { it.copy(newInfo = it.newInfo.copy(userScheduleName = new)) }
    }

    fun updateStartDate(new: LocalDate) {
        _uiState.update { it.copy(newInfo = it.newInfo.copy(startDateValue = new)) }
    }

    fun updateStartTime(new: String?) {
        _uiState.update { it.copy(newInfo = it.newInfo.copy(startTimeValue = new)) }
    }

    fun updateEndDate(new: LocalDate?) {
        _uiState.update { it.copy(newInfo = it.newInfo.copy(endDateValue = new)) }
    }

    fun updateEndTime(new: String?) {
        _uiState.update { it.copy(newInfo = it.newInfo.copy(endTimeValue = new)) }
    }

    fun updateCategory(new: Category) {
        _uiState.update { it.copy(newInfo = it.newInfo.copy(categoryValue = new)) }
    }

    fun updateMemo(new: String?) {
        _uiState.update { it.copy(newInfo = it.newInfo.copy(memoValue = new)) }
    }

    fun updateDDay() {
        _uiState.update { it.copy(newInfo = it.newInfo.copy(dDayValue = !_uiState.value.newInfo.dDayValue)) }
    }

    fun checkDoneActivated() {
        _uiState.update {
            it.copy(
                isDoneActivated = _uiState.value.originalInfo != _uiState.value.newInfo
                        && _uiState.value.newInfo.userScheduleName.isNotEmpty()
                        && _uiState.value.newInfo.categoryValue != null
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

    fun updateBottomSheetVisibility(type: ScheduleSubSheetType) {
        when (type) {
            ScheduleSubSheetType.CALENDAR -> {
                _bottomSheetState.update {
                    it.copy(isCalendarOpen = !_bottomSheetState.value.isCalendarOpen)
                }
            }

            ScheduleSubSheetType.CATEGORY -> {
                if (!_bottomSheetState.value.isCategoryOpen) viewModelScope.launch { getCategoryItems() }
                _bottomSheetState.update {
                    it.copy(isCategoryOpen = !_bottomSheetState.value.isCategoryOpen)
                }
            }

            ScheduleSubSheetType.CATEGORY_ADD -> {
                _bottomSheetState.update {
                    it.copy(isCategoryAddOpen = !_bottomSheetState.value.isCategoryAddOpen)
                }
            }

            ScheduleSubSheetType.MEMO -> {
                _bottomSheetState.update {
                    it.copy(isMemoOpen = !_bottomSheetState.value.isMemoOpen)
                }
            }

            else -> {}
        }
    }

    fun toUserSchedule(): UserSchedule {
        with(_uiState.value.newInfo) {
            val endDate = if (endDateValue != null) endDateValue.toString() else null

            return UserSchedule(
                userScheduleName = userScheduleName,
                startDate = startDateValue.toString(),
                startTime = startTimeValue,
                endDate = endDate,
                endTime = endTimeValue,
                memo = memoValue,
                category = categoryValue!!,
                dDay = dDayValue,
            )
        }
    }

    private suspend fun getCategoryItems() {
        getCategoryUseCase()
            .onSuccess { result ->
                _uiState.update { it.copy(categories = result) }
            }
            .onFailure(Timber::e)
    }

    fun postCategory(name: String, color: String) = viewModelScope.launch {
        postCategoryUseCase(name, color)
            .onSuccess { getCategoryItems() }
            .onFailure(Timber::e)
    }
}
