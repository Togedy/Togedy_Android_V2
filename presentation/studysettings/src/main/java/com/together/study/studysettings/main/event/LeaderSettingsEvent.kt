package com.together.study.studysettings.main.event

sealed class LeaderSettingsEvent {
    data object DeleteStudySuccess : LeaderSettingsEvent()
    data class ShowError(val message: String) : LeaderSettingsEvent()
}
