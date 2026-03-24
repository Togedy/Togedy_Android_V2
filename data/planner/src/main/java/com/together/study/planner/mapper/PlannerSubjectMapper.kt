package com.together.study.planner.mapper

import com.together.study.planner.dto.response.SubjectItemResponse
import com.together.study.planner.model.SubjectItem

fun SubjectItemResponse.toDomain() : SubjectItem =
    SubjectItem(
        subjectId = subjectId,
        subjectName = subjectName,
        subjectColor = subjectColor,
        orderIndex = orderIndex,
    )
