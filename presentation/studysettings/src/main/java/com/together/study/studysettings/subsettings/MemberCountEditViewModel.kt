package com.together.study.studysettings.subsettings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemberCountEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val studyId: Long = savedStateHandle.get<Long>(STUDY_ID_KEY) ?: 0
    private val studyMemberLimit: Int = savedStateHandle.get<Int>(STUDY_MEMBER_LIMIT_KEY) ?: 0
    val studyMemberCount: Int = savedStateHandle.get<Int>(STUDY_MEMBER_COUNT_KEY) ?: 0

    private val _selectedValue = MutableStateFlow(studyMemberLimit)
    val selectedValue = _selectedValue.asStateFlow()

    fun postNewMemberLimit() = viewModelScope.launch {

    }

    fun updateSelectedValue(new: Int) = {
        _selectedValue.update { new }
    }

    companion object {
        const val STUDY_ID_KEY = "studyId"
        const val STUDY_MEMBER_LIMIT_KEY = "studyMemberLimit"
        const val STUDY_MEMBER_COUNT_KEY = "studyMemberCount"
    }
}