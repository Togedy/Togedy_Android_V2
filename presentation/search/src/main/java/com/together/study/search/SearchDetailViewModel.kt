package com.together.study.search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SearchDetailViewModel @Inject constructor() : ViewModel() {

    private val _searchDummy = MutableStateFlow<SearchDetailDummy?>(null)
    val searchDummy: StateFlow<SearchDetailDummy?> = _searchDummy

    fun loadUniversityDetail(universityId: Int) {
        // TODO: API 연결 시 universityId로 실제 데이터를 가져오도록 수정
        _searchDummy.value = getUniversityDetailById(universityId)
    }

    fun deleteAddedItem(admissionMethodId: Int) {
        val currentData = _searchDummy.value ?: return

        // 해당 admissionMethodId 삭제
        val updatedAddedList = currentData.addedAdmissionMethodList.filter { addedItem ->
            currentData.universityAdmissionMethodList.any { method ->
                method.universityAdmissionMethod == addedItem && method.universityAdmissionMethodId != admissionMethodId
            }
        }

        // TODO: API 연결 시 데이터 다시 가져오게 수정
        val updatedData = currentData.copy(
            addedAdmissionMethodList = updatedAddedList
        )

        _searchDummy.value = updatedData
    }

    fun addAdmissionMethod(admissionMethodId: Int) {
        val currentData = _searchDummy.value ?: return
        
        // 해당 admissionMethodId를 가진 전형 찾기
        val admissionMethod = currentData.universityAdmissionMethodList.find { 
            it.universityAdmissionMethodId == admissionMethodId 
        } ?: return
        
        // 이미 추가된 전형인지 확인
        val isAlreadyAdded = currentData.addedAdmissionMethodList.contains(admissionMethod.universityAdmissionMethod)
        if (isAlreadyAdded) return
        
        // 새로운 전형 추가
        val updatedAddedList = currentData.addedAdmissionMethodList + admissionMethod.universityAdmissionMethod
        
        // TODO: API 연결 시 데이터 다시 가져오게 수정
        val updatedData = currentData.copy(
            addedAdmissionMethodList = updatedAddedList
        )
        
        _searchDummy.value = updatedData
    }

    private fun getUniversityDetailById(universityId: Int): SearchDetailDummy {
        // TODO: API 연결 시 실제 데이터베이스에서 가져오도록 수정
        return when (universityId) {
            1 -> universityData
            else -> universityData
        }
    }
}
