package com.together.study.timer.mapper

import com.together.study.timer.dto.RunningTimerResponse
import com.together.study.timer.dto.SubjectTimerResponse
import com.together.study.timer.dto.TimerResponse
import com.together.study.timer.model.RunningTimer
import com.together.study.timer.model.SubjectTimer
import com.together.study.timer.model.Timer
import com.together.study.timer.model.TimerHeartbeatException
import retrofit2.Response

private val INVALIDATED_STATUS_CODES = setOf(403, 404, 409) // 서버가 이미 정리한 타이머

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
        subjectColor = subjectColor,
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

fun Response<Unit>.toHeartbeatException(): TimerHeartbeatException {
    val statusCode = code()

    return if (statusCode in INVALIDATED_STATUS_CODES) {
        TimerHeartbeatException.Invalidated(statusCode)
    } else {
        TimerHeartbeatException.Transient(statusCode)
    }
}