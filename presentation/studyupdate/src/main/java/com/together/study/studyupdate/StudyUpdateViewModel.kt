package com.together.study.studyupdate

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.type.study.StudyUpdateType
import com.together.study.study.repository.StudyDetailRepository
import com.together.study.study.repository.StudyUpdateRepository
import com.together.study.studyupdate.component.StudyTimeOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class StudyUpdateViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val studyUpdateRepository: StudyUpdateRepository,
    private val studyDetailRepository: StudyDetailRepository,
) : ViewModel() {
    val studyId: Long = savedStateHandle.get<Long>("studyId") ?: 0L
    val updateType: StudyUpdateType = savedStateHandle.get<StudyUpdateType>("updateType") ?: StudyUpdateType.CREATE

    // 상태 관리
    private val _isChallenge = MutableStateFlow(savedStateHandle.get<Boolean>("isChallenge") == true)
    val isChallenge: StateFlow<Boolean> = _isChallenge.asStateFlow()

    // 스터디 수정 원본 데이터 저장
    private var originalStudyName: String = ""
    private var originalStudyIntroduce: String = ""
    private var originalStudyCategory: String? = null
    private var originalStudyImageUri: Uri? = null
    private var originalStudyPassword: String = ""
    private var originalSelectedMemberCount: Int? = null

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

    // 추후 중복확인 로딩 상태 설정
    private val _isDuplicateCheckLoading = MutableStateFlow(false)
    val isDuplicateCheckLoading: StateFlow<Boolean> = _isDuplicateCheckLoading.asStateFlow()

    private val _isStudyNameDuplicate = MutableStateFlow<Boolean?>(null)
    val isStudyNameDuplicate: StateFlow<Boolean?> = _isStudyNameDuplicate.asStateFlow()

    private val _studyNameErrorMessage = MutableStateFlow<String?>(null)
    val studyNameErrorMessage: StateFlow<String?> = _studyNameErrorMessage.asStateFlow()

    // 상태 저장 함수
    fun updateStudyName(name: String) {
        _studyName.update { name }
        _isStudyNameDuplicate.update { null }
        _studyNameErrorMessage.update { null }
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
        _studyCategory,
        _isStudyNameDuplicate
    ) { name, introduce, memberCount, category, isDuplicate ->
        val isValid = name.isNotBlank() &&
                introduce.isNotBlank() &&
                memberCount != null &&
                category != null &&
                (isDuplicate == false)

        if (updateType == StudyUpdateType.UPDATE) {
            isValid && hasChanges()
        } else {
            isValid
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )

    // 스터디 수정 시 변경사항 확인
    private fun hasChanges(): Boolean {
        return _studyName.value != originalStudyName ||
                _studyIntroduce.value != originalStudyIntroduce ||
                _studyCategory.value != originalStudyCategory ||
                _studyImage.value?.toString() != originalStudyImageUri?.toString() ||
                _studyPassword.value != originalStudyPassword ||
                _selectedMemberCount.value != originalSelectedMemberCount
    }

    // 스터디 이름 중복 확인
    fun checkStudyNameDuplicate() = viewModelScope.launch {
        val name = _studyName.value
        if (name.isBlank()) {
            _studyNameErrorMessage.update { "스터디 이름을 입력해주세요" }
            _isStudyNameDuplicate.update { true }
            return@launch
        }

        _isDuplicateCheckLoading.update { true }
        _studyNameErrorMessage.update { null }

        studyUpdateRepository.checkStudyNameDuplicate(name).fold(
            onSuccess = { studyNameDuplicate ->
                val isDuplicate = studyNameDuplicate.isDuplicate
                _isStudyNameDuplicate.update { isDuplicate }
                if (isDuplicate) {
                    _studyNameErrorMessage.update { "이미 사용 중인 스터디 이름입니다" }
                } else {
                    _studyNameErrorMessage.update { null }
                }
            },
            onFailure = { throwable ->
                _studyNameErrorMessage.update { "중복 확인에 실패했습니다" }
                _isStudyNameDuplicate.update { null }
            }
        )

        _isDuplicateCheckLoading.update { false }
    }

    // 스터디 수정 정보 로드
    fun loadStudyDetailInfo() = viewModelScope.launch {
        if (studyId == 0L || updateType != StudyUpdateType.UPDATE) return@launch

        studyDetailRepository.getStudyDetailInfo(studyId).fold(
            onSuccess = { studyDetailInfo ->
                // 원본 데이터 저장
                originalStudyName = studyDetailInfo.studyName
                originalStudyIntroduce = studyDetailInfo.studyDescription ?: ""
                originalStudyCategory = studyDetailInfo.studyTag
                originalStudyImageUri = studyDetailInfo.studyImageUrl?.toUri()
                originalStudyPassword = studyDetailInfo.studyPassword ?: ""
                originalSelectedMemberCount = studyDetailInfo.studyMemberLimit

                // 현재 상태 업데이트
                _studyName.update { studyDetailInfo.studyName }
                _studyIntroduce.update { studyDetailInfo.studyDescription ?: "" }
                _studyCategory.update { studyDetailInfo.studyTag }
                _studyImage.update { studyDetailInfo.studyImageUrl?.toUri() }
                _studyPassword.update { studyDetailInfo.studyPassword ?: "" }
                _selectedMemberCount.update { studyDetailInfo.studyMemberLimit }
                _isChallenge.update { studyDetailInfo.studyType == "CHALLENGE" }
            },
            onFailure = {
                // 에러 처리
            }
        )
    }

    // 스터디 생성
    fun createStudy(
        challengeGoalTime: Int?,
        studyName: String,
        studyDescription: String?,
        studyMemberLimit: Int,
        studyTag: String,
        studyPassword: String?,
        studyImageUri: Uri?,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) = viewModelScope.launch {
        studyUpdateRepository.createStudy(
            challengeGoalTime = challengeGoalTime,
            studyName = studyName,
            studyDescription = studyDescription,
            studyMemberLimit = studyMemberLimit,
            studyTag = studyTag,
            studyPassword = studyPassword,
            studyImageUri = studyImageUri?.toString(),
        ).fold(
            onSuccess = {
                onSuccess()
            },
            onFailure = { throwable ->
                onFailure(throwable.message ?: "스터디 생성에 실패했습니다.")
            }
        )
    }

    // 스터디 정보 수정
    fun updateStudy(
        challengeGoalTime: Int?,
        studyName: String,
        studyDescription: String?,
        studyMemberLimit: Int,
        studyTag: String,
        studyPassword: String?,
        studyImageUri: Uri?,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) = viewModelScope.launch {
        studyUpdateRepository.updateStudy(
            studyId = studyId,
            challengeGoalTime = challengeGoalTime,
            studyName = studyName,
            studyDescription = studyDescription,
            studyMemberLimit = studyMemberLimit,
            studyTag = studyTag,
            studyPassword = studyPassword,
            studyImageUri = studyImageUri?.toString(),
        ).fold(
            onSuccess = {
                onSuccess()
            },
            onFailure = { throwable ->
                onFailure(throwable.message ?: "스터디 수정에 실패했습니다.")
            }
        )
    }
}

