package com.togehter.study.studyupdate

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.togehter.study.studyupdate.component.StudyTimeOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
internal class StudyUpdateViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val studyId: Long = savedStateHandle.get<Long>("studyId") ?: 0L

    // 상태 관리
    private val _isChallenge = MutableStateFlow(
        savedStateHandle.get<Boolean>("isChallenge") == true
    )
    val isChallenge: StateFlow<Boolean> = _isChallenge.asStateFlow()

    private val _studyName = MutableStateFlow("")
    val studyName: StateFlow<String> = _studyName.asStateFlow()

    private val _studyIntroduce = MutableStateFlow("")
    val studyIntroduce: StateFlow<String> = _studyIntroduce.asStateFlow()

    private val _studyCategory = MutableStateFlow<String?>(null)
    val studyCategory: StateFlow<String?> = _studyCategory.asStateFlow()

    private val _studyImage = MutableStateFlow<Uri?>(null)
    val studyImage: StateFlow<Uri?> = _studyImage.asStateFlow()

    private val _studyPassword = MutableStateFlow("")
    val studyPassword: StateFlow<String> = _studyPassword.asStateFlow()

    private val _selectedMemberCount = MutableStateFlow<Int?>(null)
    val selectedMemberCount: StateFlow<Int?> = _selectedMemberCount.asStateFlow()

    private val _selectedStudyTime = MutableStateFlow(StudyTimeOption.FIVE_HOURS)
    val selectedStudyTime: StateFlow<StudyTimeOption> = _selectedStudyTime.asStateFlow()

    // 상태 저장 함수
    fun updateStudyName(name: String) {
        _studyName.update { name }
    }

    fun updateStudyIntroduce(introduce: String) {
        _studyIntroduce.update { introduce }
    }

    fun updateStudyCategory(category: String?) {
        _studyCategory.update { category }
    }

    fun updateStudyImage(image: Uri?) {
        _studyImage.update { image }
    }

    fun updateStudyPassword(password: String) {
        _studyPassword.update { password }
    }

    fun updateSelectedMemberCount(count: Int?) {
        _selectedMemberCount.update { count }
    }

    fun updateSelectedStudyTime(time: StudyTimeOption) {
        _selectedStudyTime.update { time }
    }

    // 다음 버튼 활성화 여부
    val isNextButtonEnabled: StateFlow<Boolean> = combine(
        _studyName,
        _studyIntroduce,
        _selectedMemberCount,
        _studyCategory
    ) { name, introduce, memberCount, category ->
        name.isNotBlank() &&
                introduce.isNotBlank() &&
                memberCount != null &&
                category != null
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )
}

