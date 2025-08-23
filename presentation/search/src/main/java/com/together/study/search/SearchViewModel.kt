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

    private val allList = dummySearchList().toMutableList()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _filteredList = MutableStateFlow<List<SearchDummy>>(emptyList())
    val filteredList: StateFlow<List<SearchDummy>> = _filteredList

    private val _admissionType = MutableStateFlow(AdmissionType.ALL)
    val admissionType: StateFlow<AdmissionType> = _admissionType

    private var searchJob: Job? = null

    init {
        // 초기 데이터 로드
        updateFilteredList()
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            updateFilteredList()
        }
    }

    fun onAdmissionTypeChanged(type: AdmissionType) {
        _admissionType.value = type
        updateFilteredList()
    }

    private fun updateFilteredList() {
        val query = _searchQuery.value
        val type = _admissionType.value
        
        _filteredList.value = allList
            .filter { searchDummy ->
                // 검색어 필터링
                val filteredQuery = query.isEmpty() ||
                    searchDummy.universityName.contains(query, ignoreCase = true)
                
                // 입학 전형 타입 필터링
                val filteredAdmissionType = when (type) {
                    AdmissionType.ALL -> true
                    AdmissionType.EARLY -> searchDummy.universityAdmissionType == "수시"
                    AdmissionType.REGULAR -> searchDummy.universityAdmissionType == "정시"
                }

                // 검색어, 전형 일치하는 값 반환
                filteredQuery && filteredAdmissionType
            }
            .sortedByDescending { it.addedAdmissionMethodList.isNotEmpty() }
    }
}
