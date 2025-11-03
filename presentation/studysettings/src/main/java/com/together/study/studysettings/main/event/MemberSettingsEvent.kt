package com.together.study.studysettings.main.event

sealed class MemberSettingsEvent {
    data object DeleteStudySuccess : MemberSettingsEvent()
    data class ShowError(val message: String) : MemberSettingsEvent()
}
