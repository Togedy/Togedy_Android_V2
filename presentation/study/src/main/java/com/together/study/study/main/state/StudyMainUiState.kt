package com.together.study.study.main.state

import androidx.compose.runtime.Immutable
import com.together.study.common.state.UiState

@Immutable
data class StudyMainUiState(
    val myStudyState: UiState<MyStudyInfo>,
    val exploreState: UiState<MyStudyInfo>,
) {
    val isLoaded: UiState<Unit>
        get() = when {
            myStudyState is UiState.Loading || exploreState is UiState.Loading
                -> UiState.Loading

            myStudyState is UiState.Success && exploreState is UiState.Success
                -> UiState.Success(Unit)

            myStudyState is UiState.Failure && exploreState is UiState.Failure
                -> UiState.Failure("failed to load")

            else -> UiState.Empty
        }
}

data class MyStudyInfo(
    val timerInfo: TimerInfo,
    val studyList: List<Study>,
)

data class TimerInfo(
    val hasChallenge: Boolean,
    val goalTime: String?,
    val studyTime: String?,
    val achievement: Int?,
) {
    companion object {
        val mock1 = TimerInfo(true, "10:00:00", "10:00:00", 0)
        val mock2 = TimerInfo(false, null, "01:00:00", 0)
    }
}

/* TODO: domain으로 이동 예정 */
data class Study(
    val studyId: Long,
    val studyType: String,
    val challengeGoalTime: String?,
    val challengeAchievement: Int?,
    val studyName: String,
    val completedMemberCount: Int?,
    val studyMemberCount: Int,
    val activeMemberList: List<User>,
) {
    companion object {
        val mock1 = Study(
            studyId = 1,
            studyType = "CHALLENGE",
            challengeGoalTime = "10:00:00",
            challengeAchievement = 75,
            studyName = "을지중학교 1-1",
            completedMemberCount = 3,
            studyMemberCount = 5,
            activeMemberList = listOf(User.mock1, User.mock2)
        )
        val mock2 = Study(
            studyId = 2,
            studyType = "NORMAL",
            challengeGoalTime = null,
            challengeAchievement = null,
            studyName = "고1 내신 대비반",
            completedMemberCount = null,
            studyMemberCount = 5,
            activeMemberList = listOf(User.mock1, User.mock2, User.mock1, User.mock2, User.mock1)
        )
    }
}

/* TODO: domain으로 이동 예정 */
data class User(
    val userName: String,
    val userProfileImageUrl: String,
) {
    companion object {
        val mock1 = User(userName = "토마토마토", userProfileImageUrl = "")
        val mock2 = User(userName = "더블치즈감자", userProfileImageUrl = "")
    }
}
