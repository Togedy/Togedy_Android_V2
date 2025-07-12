package com.together.study.calendar.mapper

import com.together.study.calendar.dto.AnnouncementResponse
import com.together.study.calendar.dto.DDayResponse
import com.together.study.calendar.dto.ScheduleResponse
import com.together.study.calendar.model.Announcement
import com.together.study.calendar.model.DDay
import com.together.study.calendar.model.Schedule

fun List<ScheduleResponse>.toDomain(): List<Schedule> =
    this.map { it.toSchedule() }

fun ScheduleResponse.toSchedule() =
    Schedule(
        scheduleId = scheduleId,
        scheduleType = scheduleType,
        scheduleName = scheduleName,
        startDate = startDate,
        startTime = startTime,
        endTime = endTime,
        endDate = endDate,
        universityAdmissionType = universityAdmissionType,
        universityAdmissionStage = universityAdmissionStage,
        category = category.toDomain(),
    )

fun DDayResponse.toDomain(): DDay =
    DDay(
        hasDday = hasDday,
        userScheduleName = userScheduleName,
        remainingDays = remainingDays,
    )

fun AnnouncementResponse.toDomain(): Announcement =
    Announcement(
        hasAnnouncement = hasAnnouncement,
        announcement = announcement,
    )
