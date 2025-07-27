package com.together.study.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.search.type.AdmissionType
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val allList = dummyScheduleList().toMutableList()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _filteredList = MutableStateFlow<List<SearchDummy>>(emptyList())
    val filteredList: StateFlow<List<SearchDummy>> = _filteredList

    private val _admissionType = MutableStateFlow(AdmissionType.ALL)
    val admissionType: StateFlow<AdmissionType> = _admissionType

    private var searchJob: Job? = null

    init {
        // 초기 데이터 로드
        _filteredList.value =
            allList.sortedByDescending { it.addedAdmissionMethodList.isNotEmpty() }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            _filteredList.value = allList
                .filter {
                    it.universityName.contains(query, ignoreCase = true)
                }
                .sortedByDescending { it.addedAdmissionMethodList.isNotEmpty() }
        }
    }


    fun onAdmissionTypeChanged(type: AdmissionType) {
        _admissionType.value = type
    }
}
