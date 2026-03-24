package com.together.study.planner.subject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.planner.model.PlannerSubject
import com.together.study.planner.model.SubjectItem
import com.together.study.planner.usecase.DeleteSubjectUseCase
import com.together.study.planner.usecase.GetSubjectsUseCase
import com.together.study.planner.usecase.PatchSubjectUseCase
import com.together.study.planner.usecase.PostSubjectUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubjectDetailViewModel @Inject constructor(
    private val getSubjectsUseCase: GetSubjectsUseCase,
    private val postSubjectUseCase: PostSubjectUseCase,
    private val patchSubjectUseCase: PatchSubjectUseCase,
    private val deleteSubjectUseCase: DeleteSubjectUseCase,
) : ViewModel() {
    private val _subjectState = MutableStateFlow<UiState<List<SubjectItem>>>(UiState.Loading)
    val subjectState = _subjectState.asStateFlow()

    private var lastedSubjectItems = emptyList<SubjectItem>()

    fun fetchSubjectItems() = viewModelScope.launch {
        getSubjectsUseCase()
            .onSuccess { result ->
                updateState(UiState.Success(result))
                lastedSubjectItems = result
            }
            .onFailure { updateState(UiState.Failure(it.message.toString())) }
    }

    fun saveNewSubject(name: String, color: String) = viewModelScope.launch {
        postSubjectUseCase(name, color)
            .onSuccess {
                val updatedList = lastedSubjectItems +
                        SubjectItem(
                            subjectId = null,
                            subjectName = name,
                            subjectColor = color,
                        )
                updateState(UiState.Success(updatedList))
            }
            .onFailure {
                // toast
            }
    }

    fun updateSubject(new: SubjectItem) = viewModelScope.launch {
        val origin = lastedSubjectItems
            .find { it.subjectId == new.subjectId }
            ?: return@launch

        patchSubjectUseCase(
            id = new.subjectId!!,
            originName = origin.subjectName,
            originColor = origin.subjectColor,
            name = new.subjectName,
            color = new.subjectColor
        )
            .onSuccess {
                val updatedList = lastedSubjectItems.map { subject ->
                    if (subject.subjectId == new.subjectId) {
                        subject.copy(
                            subjectName = new.subjectName,
                            subjectColor = new.subjectColor,
                        )
                    } else subject
                }

                updateState(UiState.Success(updatedList))
            }
            .onFailure {
                // toast
            }

    }

    fun deleteSubject(id: Long) = viewModelScope.launch {
        deleteSubjectUseCase(id)
            .onSuccess {
                val updatedList = lastedSubjectItems.filter { it.subjectId != id }
                updateState(UiState.Success(updatedList))
            }
            .onFailure {
                //toast
            }

    }


    private fun updateState(newState: UiState<List<SubjectItem>>) =
        _subjectState.update { newState }
}
