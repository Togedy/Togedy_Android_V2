package com.together.study.study.main.state

import androidx.compose.runtime.Immutable
import com.together.study.common.state.UiState

@Immutable
data class StudyMainUiState(
    val mainState: UiState<MainInformation>,
)

data class MainInformation(
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
    val studyType: String,
    val challengeGoalTime: String,
    val challengeAcheivement: Int,
    val studyName: String,
    val completedMemberCount: Int,
    val studyMemberCount: Int,
    val activeMemberList: List<User>,
)

/* TODO: domain으로 이동 예정 */
data class User(
    val userName: String,
    val userProfileImageUrl: String,
)
