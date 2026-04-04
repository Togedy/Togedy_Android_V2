package com.together.study.planner.share

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.together.study.common.state.UiState
import com.together.study.planner.model.PlannerSubject
import com.together.study.planner.navigation.PlannerShare
import com.together.study.planner.share.state.PlannerShareUiState
import com.together.study.planner.usecase.GetShareInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
internal class PlannerShareViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getShareInfoUseCase: GetShareInfoUseCase,
) : ViewModel() {
    private val route: PlannerShare = savedStateHandle.toRoute<PlannerShare>()
    val date = "${route.year}-${route.month.toString().padStart(2, '0')}-${
        route.day.toString().padStart(2, '0')
    }"

    private val _uiState = MutableStateFlow(PlannerShareUiState())
    val uiState = _uiState.asStateFlow()

    private val _subjects: MutableStateFlow<List<PlannerSubject>> = MutableStateFlow(emptyList())
    val subjects = _subjects.asStateFlow()
    private val _selectedSubjects: MutableStateFlow<List<Long>> = MutableStateFlow(emptyList())
    val selectedSubjects = _selectedSubjects.asStateFlow()
    private val _isAllSelected: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isAllSelected = _isAllSelected.asStateFlow()
    private val _showTask: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val showTask = _showTask.asStateFlow()

    suspend fun getPlannerShareInfo() {
        getShareInfoUseCase(date)
            .onSuccess { result ->
                _uiState.update { it.copy(plannerShareInfo = UiState.Success(result)) }
                _subjects.value =
                    (_uiState.value.plannerShareInfo as UiState.Success).data.plannerItems.map {
                        PlannerSubject(
                            it.subjectId,
                            it.subjectName,
                            it.subjectColor,
                        )
                    }
                _selectedSubjects.value = _subjects.value.mapNotNull { it.subjectId }
            }
            .onFailure { e ->
                _uiState.update {
                    it.copy(plannerShareInfo = UiState.Failure(e.message.toString()))
                }
            }
    }

    fun updateIsAllSelected() {
        _isAllSelected.value = !_isAllSelected.value

        if (_isAllSelected.value) _selectedSubjects.value =
            subjects.value.mapNotNull { it.subjectId }
        else _selectedSubjects.value = emptyList()
    }

    fun updateShowTask() {
        _showTask.value = !_showTask.value
    }

    fun updateSelectedSubjects(new: Long) {
        if (_selectedSubjects.value.contains(new)) _selectedSubjects.value -= new
        else _selectedSubjects.value += new

        _isAllSelected.value = _selectedSubjects.value.size == subjects.value.size
    }
}
