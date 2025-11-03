package com.together.study.studysettings.subsettings.event

sealed class MemberCountEditEvent {
    data object UpdateSuccess : MemberCountEditEvent()
    data class ShowError(val message: String) : MemberCountEditEvent()
}
