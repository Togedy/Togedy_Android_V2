package com.together.study.studysettings.subsettings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.study.repository.StudySettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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
    private val _uiState = MutableStateFlow<UiState<Boolean?>>(UiState.Success(null))
    val uiState = _uiState.asStateFlow()

    fun postNewMemberLimit(new: Int) = viewModelScope.launch {
        _uiState.update { UiState.Loading }
        studySettingsRepository.updateStudyMemberLimit(studyId, memberLimit.value)
            .onSuccess {
                _uiState.update { UiState.Success(true) }
                updateSelectedValue(new)
            }
            .onFailure {
                Timber.tag(TAG).d("postNewMemberLimit failed: $it")
                _uiState.update { UiState.Success(false) }
            }

    }

    fun updateSelectedValue(new: Int) = {
        _memberLimit.update { new }
    }

    companion object {
        const val TAG = "MemberCountEditViewModel"
        const val STUDY_ID_KEY = "studyId"
        const val STUDY_MEMBER_LIMIT_KEY = "studyMemberLimit"
        const val STUDY_MEMBER_COUNT_KEY = "studyMemberCount"
    }
}