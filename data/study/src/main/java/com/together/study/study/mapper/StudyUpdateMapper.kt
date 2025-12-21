package com.together.study.study.mapper

import com.together.study.study.dto.StudyDuplicateResponse
import com.together.study.study.model.StudyNameDuplicate

fun StudyDuplicateResponse.toDomain(): StudyNameDuplicate =
    StudyNameDuplicate(
        isDuplicate = isDuplicate,
    )

