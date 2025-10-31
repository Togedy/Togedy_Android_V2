package com.together.study.studysettings.subsettings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.study.repository.StudySettingsRepository
import com.together.study.studysettings.subsettings.event.MemberCountEditEvent
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
class MemberCountEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val studySettingsRepository: StudySettingsRepository,
) : ViewModel() {
    val studyId: Long = savedStateHandle.get<Long>(STUDY_ID_KEY) ?: 0
    private val studyMemberLimit: Int = savedStateHandle.get<Int>(STUDY_MEMBER_LIMIT_KEY) ?: 0
    val studyMemberCount: Int = savedStateHandle.get<Int>(STUDY_MEMBER_COUNT_KEY) ?: 0

    private val _memberLimit = MutableStateFlow(studyMemberLimit)
    val memberLimit = _memberLimit.asStateFlow()

    private val _eventFlow = MutableSharedFlow<MemberCountEditEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun postNewMemberLimit(new: Int) = viewModelScope.launch {
        studySettingsRepository.updateStudyMemberLimit(studyId, memberLimit.value)
            .onSuccess {
                _eventFlow.emit(MemberCountEditEvent.UpdateSuccess)
                updateSelectedValue(new)
            }
            .onFailure {
                _eventFlow.emit(MemberCountEditEvent.ShowError("인원 수 변경에 실패했습니다."))
                Timber.tag(TAG).d("postNewMemberLimit failed: $it")
            }
    }

    private fun updateSelectedValue(new: Int) = {
        _memberLimit.update { new }
    }

    companion object {
        const val TAG = "MemberCountEditViewModel"
        const val STUDY_ID_KEY = "studyId"
        const val STUDY_MEMBER_LIMIT_KEY = "studyMemberLimit"
        const val STUDY_MEMBER_COUNT_KEY = "studyMemberCount"
    }
}