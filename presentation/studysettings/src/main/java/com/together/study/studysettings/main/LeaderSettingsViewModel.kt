package com.together.study.studysettings.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.study.model.StudyDetailInfo
import com.together.study.study.repository.StudyDetailRepository
import com.together.study.study.repository.StudySettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LeaderSettingsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val studyDetailRepository: StudyDetailRepository,
    private val studySettingsRepository: StudySettingsRepository,
) : ViewModel() {
    val studyId: Long = savedStateHandle.get<Long>(STUDY_ID_KEY) ?: 0

    private val _studyInfo = MutableStateFlow<StudyDetailInfo?>(null)
    val studyInfo = _studyInfo.asStateFlow()

    private val _uiState = MutableStateFlow<UiState<Boolean?>>(UiState.Success(null))
    val uiState = _uiState.asStateFlow()

    init {
        getStudyInfo()
    }

    fun getStudyInfo() = viewModelScope.launch {
        studyDetailRepository.getStudyDetailInfo(studyId)
            .onSuccess { _studyInfo.update { it } }
            .onFailure { Timber.tag(TAG).d("getStudyInfo failed: $it") }
    }

    fun deleteStudyAsLeader() = viewModelScope.launch {
        _uiState.update { UiState.Loading }
        studySettingsRepository.deleteStudyAsLeader(studyId)
            .onSuccess { _uiState.update { UiState.Success(true) } }
            .onFailure { _uiState.update { UiState.Success(false) } }
    }

    companion object {
        const val TAG = "LeaderSettingsViewModel"
        const val STUDY_ID_KEY = "studyId"
    }
}
