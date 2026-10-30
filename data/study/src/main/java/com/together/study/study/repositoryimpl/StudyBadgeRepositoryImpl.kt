package com.together.study.study.repositoryimpl

import com.together.study.local.StudyBadgeDataStore
import com.together.study.study.model.StudyBadge
import com.together.study.study.repository.StudyBadgeRepository
import javax.inject.Inject

class StudyBadgeRepositoryImpl @Inject constructor(
    private val studyBadgeDataStore: StudyBadgeDataStore,
) : StudyBadgeRepository {
    // TODO: 서버 API 연동 시 데이터소스로 교체
    override suspend fun getStudyBadges(): Result<List<StudyBadge>> =
        runCatching { dummyBadges }

    override suspend fun getLastCheckedBadgeId(): Long? =
        studyBadgeDataStore.getLastCheckedBadgeId()

    override suspend fun updateLastCheckedBadgeId(badgeId: Long) {
        studyBadgeDataStore.updateLastCheckedBadgeId(badgeId)
    }

    private val dummyBadges: List<StudyBadge> = buildList {
        add(
            StudyBadge(
                badgeId = 100,
                badgeName = "크리스마스 파이터",
                badgeDescription = "공휴일인 크리스마스에 누적 10시간\n공부를 해서 뱃지를 받았어요",
                badgeImageUrl = null,
                isAcquired = true,
                acquiredAt = "2025-12-25",
            )
        )
        (1..12).forEach { month ->
            val isAcquired = month <= 10
            add(
                StudyBadge(
                    badgeId = month.toLong(),
                    badgeName = "${month}월 출석왕",
                    badgeDescription =
                        if (isAcquired) "${month}월 한 달 동안 매일 출석해서\n뱃지를 받았어요"
                        else "${month}월 한 달 동안 매일 출석하면\n받을 수 있어요",
                    badgeImageUrl = null,
                    isAcquired = isAcquired,
                    acquiredAt = if (isAcquired) "2025-%02d-28".format(month) else null,
                )
            )
        }
        add(
            StudyBadge(
                badgeId = 101,
                badgeName = "겨울방학 마스터",
                badgeDescription = "겨울방학 동안 누적 100시간\n공부하면 받을 수 있어요",
                badgeImageUrl = null,
                isAcquired = false,
                acquiredAt = null,
            )
        )
    }
}
