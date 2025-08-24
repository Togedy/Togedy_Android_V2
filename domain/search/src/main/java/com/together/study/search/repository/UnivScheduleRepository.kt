package com.together.study.search.repository

import com.together.study.search.model.AdmissionMethod
import com.together.study.search.model.UnivDetailSchedule
import com.together.study.search.model.UnivSchedule

interface UnivScheduleRepository {
    suspend fun getUnivScheduleList(
        name: String,
        admissionType: String,
        page: Int,
        size: Int
    ): Result<List<UnivSchedule>>
    
    suspend fun getUnivDetailSchedule(universityId: Int): Result<UnivDetailSchedule>
    
    suspend fun deleteUnivDetailSchedule(universityAdmissionMethodId: Int): Result<Unit>
    
    suspend fun addUnivDetailSchedule(request: AdmissionMethod): Result<Unit>
}
