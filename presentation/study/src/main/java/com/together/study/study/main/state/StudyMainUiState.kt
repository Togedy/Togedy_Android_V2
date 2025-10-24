package com.together.study.study.main.state

import androidx.compose.runtime.Immutable
import com.together.study.common.state.UiState
import com.together.study.common.type.study.StudySortingType
import com.together.study.study.model.ExploreStudyItem
import com.together.study.study.model.MyStudyInfo
import com.together.study.study.model.MyStudyItem
import com.together.study.study.type.StudyTagType

@Immutable
data class StudyMainUiState(
    val myStudyState: UiState<MyStudyInfo>,
    val exploreStudyState: UiState<List<ExploreStudyItem>>,
    val exploreFilterState: ExploreFilterState,
) {
    val isLoaded: UiState<Unit>
        get() = when {
            myStudyState is UiState.Loading || exploreStudyState is UiState.Loading
                -> UiState.Loading

            myStudyState is UiState.Success && exploreStudyState is UiState.Success
                -> UiState.Success(Unit)

            myStudyState is UiState.Failure && exploreStudyState is UiState.Failure
                -> UiState.Failure("failed to load")

            else -> UiState.Empty
        }
}

data class ExploreFilterState(
    val tagFilters: List<StudyTagType> = listOf(StudyTagType.ENTIRE),
    val sortOption: StudySortingType = StudySortingType.RECENT,
    val isJoinable: Boolean = false,
    val isChallenge: Boolean = false,
)

/* TODO: domain으로 이동 예정 */
data class Study(
    val studyId: Long = -1,
    val studyType: String,
    val challengeGoalTime: String = "",
    val challengeAchievement: Int = 100,
    val studyName: String,
    val studyDescription: String = "",
    val studyTag: String = "",
    val studyImageUrl: String = "",
    val completedMemberCount: Int = 0,
    val studyMemberCount: Int = 0,
    val studyMemberLimit: Int = 0,
    val studyLeaderImageUrl: String = "",
    val activeMemberList: List<MyStudyItem.Companion.ActiveMember> = emptyList(),
    val isNewlyCreated: Boolean = false,
    val lastActivatedAt: String = "",
    val hasPassword: Boolean = false,
    val password: String = "",
) {
    companion object {
        val mock1 = Study(
            studyId = 1,
            studyType = "CHALLENGE",
            challengeGoalTime = "10:00:00",
            challengeAchievement = 75,
            studyName = "을지중학교 1-1",
            studyDescription = "같이 공부하고 응원해주면서 수험생활 해봐요!! 특별한 사유없이 5일 연속 미측정 → 강퇴입니다.같이 공부하고 응원해주면서 수험생활 해봐요!! 특별한 사유없이 5일 연속 미측정 → 강퇴입니다.같이 공부하고 응원해주면서 수험생활 해봐요!! 특별한 사유없이 5일 연속 미측정 → 강퇴입니다.같이 공부하고 응원해주면서 수험생활 해봐요!! 특별한 사유없이 5일 연속 미측정 → 강퇴입니다.같이 공부하고 응원해주면서 수험생활 해봐요!! 특별한 사유없이 5일 연속 미측정 → 강퇴입니다.같이 공부하고 응원해주면서 수험생활 해봐요!! 특별한 사유없이 5일 연속 미측정 → 강퇴입니다.같이 공부하고 응원해주면서 수험생활 해봐요!! 특별한 사유없이 5일 연속 미측정 → 강퇴입니다.",
            studyTag = "자유 스터디",
            studyImageUrl = "",
            completedMemberCount = 3,
            studyMemberCount = 5,
            studyMemberLimit = 30,
            studyLeaderImageUrl = "",
            activeMemberList = listOf(),
            isNewlyCreated = true,
            lastActivatedAt = "10분 전",
            hasPassword = true,
        )
    }
}
