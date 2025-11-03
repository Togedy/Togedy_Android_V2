package com.together.study.studysettings.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.study.model.StudyDetailInfo
import com.together.study.study.repository.StudyDetailRepository
import com.together.study.study.repository.StudySettingsRepository
import com.together.study.studysettings.main.event.MemberSettingsEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MemberSettingsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val studyDetailRepository: StudyDetailRepository,
    private val studySettingsRepository: StudySettingsRepository,
) : ViewModel() {
    val studyId: Long = savedStateHandle.get<Long>(STUDY_ID_KEY) ?: 0

    private val _studyInfo = MutableStateFlow<StudyDetailInfo?>(null)
    val studyInfo = _studyInfo.asStateFlow()

    private val _eventFlow = MutableSharedFlow<MemberSettingsEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getStudyInfo()
    }

    fun getStudyInfo() = viewModelScope.launch {
        studyDetailRepository.getStudyDetailInfo(studyId)
            .onSuccess { newInfo -> _studyInfo.update { newInfo } }
            .onFailure { Timber.tag(TAG).d("getStudyInfo failed: $it") }
    }

    fun deleteStudyAsMember() = viewModelScope.launch {
        studySettingsRepository.deleteStudyAsMember(studyId)
            .onSuccess { _eventFlow.emit(MemberSettingsEvent.DeleteStudySuccess) }
            .onFailure { _eventFlow.emit(MemberSettingsEvent.ShowError("스터디 나가기에 실패했습니다.")) }
    }

    companion object {
        const val TAG = "MemberSettingsViewModel"
        const val STUDY_ID_KEY = "studyId"
    }
}