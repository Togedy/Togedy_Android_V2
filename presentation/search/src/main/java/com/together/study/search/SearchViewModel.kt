package com.together.study.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.search.model.UnivSchedule
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

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore = _isLoadingMore.asStateFlow()

    private var searchJob: Job? = null
    private var currentPage = 0
    private var hasNext = false
    private val currentScheduleList = mutableListOf<UnivSchedule>()

    init {
        // 초기 데이터 로드
        fetchUnivScheduleList()
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            resetAndFetch()
        }
    }

    fun onAdmissionTypeChanged(type: AdmissionType) {
        _admissionType.value = type
        resetAndFetch()
    }

    private fun resetAndFetch() {
        currentPage = 0
        hasNext = false
        currentScheduleList.clear()
        fetchUnivScheduleList()
    }

    private fun fetchUnivScheduleList() {
        viewModelScope.launch {
            _univScheduleState.value = UiState.Loading

            univScheduleRepository.getUnivScheduleList(
                name = _searchQuery.value,
                admissionType = _admissionType.value.toApiString(),
                page = 1,
                size = 20
            )
                .onSuccess { scheduleList ->
                    val sortedSchedules =
                        scheduleList.schedules.sortedByDescending { univSchedule ->
                            univSchedule.addedAdmissionMethodList.isNotEmpty()
                        }
                    currentScheduleList.clear()
                    currentScheduleList.addAll(sortedSchedules)
                    hasNext = scheduleList.hasNext
                    currentPage = 1
                    _univScheduleState.value = UiState.Success(
                        UnivScheduleList(
                            hasNext = hasNext,
                            schedules = currentScheduleList.toList()
                        )
                    )
                }
                .onFailure { exception ->
                    _univScheduleState.value = UiState.Failure(exception.message.toString())
                }
        }
    }

    fun onLoadNextPage() {
        if (!hasNext || _isLoadingMore.value) return

        viewModelScope.launch {
            _isLoadingMore.value = true

            univScheduleRepository.getUnivScheduleList(
                name = _searchQuery.value,
                admissionType = _admissionType.value.toApiString(),
                page = currentPage + 1,
                size = 20
            )
                .onSuccess { scheduleList ->
                    currentPage += 1
                    hasNext = scheduleList.hasNext
                    currentScheduleList.addAll(scheduleList.schedules)
                    _univScheduleState.value = UiState.Success(
                        UnivScheduleList(
                            hasNext = hasNext,
                            schedules = currentScheduleList.toList()
                        )
                    )
                }
                .onFailure {}

            _isLoadingMore.value = false
        }
    }
}

private fun AdmissionType.toApiString(): String = when (this) {
    AdmissionType.ALL -> ""
    AdmissionType.EARLY -> "수시"
    AdmissionType.REGULAR -> "정시"
}
