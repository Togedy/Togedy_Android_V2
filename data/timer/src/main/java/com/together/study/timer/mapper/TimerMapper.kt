package com.together.study.timer.mapper

import com.together.study.timer.dto.RunningTimerResponse
import com.together.study.timer.dto.SubjectTimerResponse
import com.together.study.timer.dto.TimerResponse
import com.together.study.timer.model.RunningTimer
import com.together.study.timer.model.SubjectTimer
import com.together.study.timer.model.Timer
import kotlin.collections.map

fun RunningTimerResponse.toDomain(): RunningTimer {
    return RunningTimer(
        timerId = timerId,
        subjectId = subjectId,
        startTime = startTime,
    )
}

fun List<SubjectTimerResponse>.toDomainList(): List<SubjectTimer> {
    return this.map { it.toDomain() }
}

fun SubjectTimerResponse.toDomain(): SubjectTimer {
    return SubjectTimer(
        subjectId = subjectId,
        subjectName = subjectName,
        studyTime = studyTime,
    )
}

fun TimerResponse.toDomain(): Timer {
    return Timer(
        timerId = timerId,
        startTime = startTime,
        endTime = endTime,
    )
}
