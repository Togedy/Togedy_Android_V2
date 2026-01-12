package com.together.study.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.search.model.UnivScheduleList
import com.together.study.search.repository.UnivScheduleRepository
import com.together.study.search.type.AdmissionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "SearchViewModel"

@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val univScheduleRepository: UnivScheduleRepository
) : ViewModel() {
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()
    
    private val _admissionType = MutableStateFlow(AdmissionType.ALL)
    val admissionType = _admissionType.asStateFlow()

    private val _univScheduleState = MutableStateFlow<UiState<UnivScheduleList>>(UiState.Loading)
    val univScheduleState = _univScheduleState.asStateFlow()

    private var searchJob: Job? = null

    init {
        // 초기 데이터 로드
        fetchUnivScheduleList()
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            fetchUnivScheduleList()
        }
    }

    fun onAdmissionTypeChanged(type: AdmissionType) {
        _admissionType.value = type
        fetchUnivScheduleList()
    }

    private fun fetchUnivScheduleList() {
        viewModelScope.launch {
            _univScheduleState.value = UiState.Loading
            
            univScheduleRepository.getUnivScheduleList(
                name = _searchQuery.value,
                admissionType = _admissionType.value.toApiString(),
                page = 0,
                size = 20
            )
                .onSuccess { scheduleList ->
                    val sortedSchedules =
                        scheduleList.schedules.sortedByDescending { univSchedule ->
                            univSchedule.addedAdmissionMethodList.isNotEmpty()
                        }
                    _univScheduleState.value = UiState.Success(
                        scheduleList.copy(schedules = sortedSchedules)
                    )
                }
                .onFailure { exception -> 
                    _univScheduleState.value = UiState.Failure(exception.message.toString())
                }
        }
    }
}

private fun AdmissionType.toApiString(): String = when (this) {
    AdmissionType.ALL -> ""
    AdmissionType.EARLY -> "수시"
    AdmissionType.REGULAR -> "정시"
}
