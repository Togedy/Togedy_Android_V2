package com.together.study.planner.share

import androidx.lifecycle.ViewModel
import com.together.study.planner.model.PlannerItem
import com.together.study.planner.share.state.PlannerShareUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class PlannerShareViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(PlannerShareUiState())
    val uiState = _uiState.asStateFlow()

    private var _subjects = listOf<PlannerItem>().map { it.subjectId }
    private val _selectedSubjects: MutableStateFlow<List<Long>> = MutableStateFlow(emptyList())
    val selectedSubjects = _selectedSubjects.asStateFlow()
    private val _isAllSelected: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isAllSelected = _isAllSelected.asStateFlow()
    private val _showTodo: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val showTodo = _showTodo.asStateFlow()

    fun updateIsAllSelected() {
        _isAllSelected.value = !_isAllSelected.value

        if (_isAllSelected.value) _selectedSubjects.value = _subjects
        else _selectedSubjects.value = emptyList()
    }

    fun updateShowTodo() {
        _showTodo.value = !_showTodo.value
    }

    fun updateSelectedSubjects(new: Long) {
        _selectedSubjects.value += new
    }


}
