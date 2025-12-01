package com.together.study.studymember.settings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.study.model.StudyMemberBriefInfo
import com.together.study.study.repository.StudySettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemberListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val studySettingsRepository: StudySettingsRepository,
) : ViewModel() {
    val studyId: Long = savedStateHandle.get<Long>(STUDY_ID_KEY) ?: 0

    private val _membersState =
        MutableStateFlow<UiState<List<StudyMemberBriefInfo>>>(UiState.Loading)
    val memberState = _membersState.asStateFlow()

    init {
        getMembers()
    }

    fun getMembers() = viewModelScope.launch {
        studySettingsRepository.getStudyMembers(studyId)
            .onSuccess { _membersState.value = UiState.Success(it) }
            .onFailure { _membersState.value = UiState.Failure(it.message.toString()) }
    }

    companion object {
        const val TAG = "MemberListViewModel"
        const val STUDY_ID_KEY = "studyId"
    }
}
