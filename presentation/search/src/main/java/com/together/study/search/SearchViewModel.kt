package com.together.study.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val allList = dummyScheduleList().toMutableList()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _filteredList = MutableStateFlow<List<SearchScheduleData>>(emptyList())
    val filteredList: StateFlow<List<SearchScheduleData>> = _filteredList

    private var searchJob: Job? = null

    init {
        // 초기 데이터 로드
        _filteredList.value = allList.sortedByDescending { it.isAdded }
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
                .sortedByDescending { it.isAdded }
        }
    }

    fun toggleScheduleStatus(data: SearchScheduleData) {
        val currentList = _filteredList.value.toMutableList()
        val index = currentList.indexOfFirst { it.universityName == data.universityName }

        if (index != -1) {
            val updatedData = data.copy(isAdded = !data.isAdded)
            currentList[index] = updatedData

            // allList도 업데이트
            val allListIndex = allList.indexOfFirst { it.universityName == data.universityName }
            if (allListIndex != -1) {
                allList[allListIndex] = updatedData
            }

            _filteredList.value = currentList.sortedByDescending { it.isAdded }
        }
    }
}
