package com.together.study.planner.subject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.planner.model.PlannerSubject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubjectDetailViewModel @Inject constructor(

) : ViewModel() {
    private val _subjectState = MutableStateFlow<UiState<List<PlannerSubject>>>(UiState.Loading)
    val subjectState = _subjectState.asStateFlow()

    private var lastedSubjectItems = emptyList<PlannerSubject>()

    fun fetchSubjectItems() = viewModelScope.launch {
        updateState(UiState.Success(emptyList()))
        lastedSubjectItems = emptyList()
    }

    fun saveNewSubject(name: String, color: String) = viewModelScope.launch {
        val updatedList = lastedSubjectItems + PlannerSubject(null, name, color, emptyList())
        updateState(UiState.Success(updatedList))
    }

    fun updateSubject(new: PlannerSubject) = viewModelScope.launch {
        val updatedList = lastedSubjectItems.map { subject ->
            if (subject.id == new.id) subject.copy(name = new.name, color = new.color)
            else subject
        }
        updateState(UiState.Success(updatedList))
    }

    fun deleteSubject(id: Long) = viewModelScope.launch {
        val updatedList = lastedSubjectItems.filter { it.id != id }
        updateState(UiState.Success(updatedList))
    }


    private fun updateState(newState: UiState<List<PlannerSubject>>) =
        _subjectState.update { newState }
}
