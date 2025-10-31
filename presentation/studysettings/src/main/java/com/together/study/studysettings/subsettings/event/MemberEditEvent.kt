package com.together.study.studysettings.subsettings.event

sealed class MemberEditEvent {
    data object DeleteMemberSuccess : MemberEditEvent()
    data object DelegateSuccess : MemberEditEvent()
    data class ShowError(val message: String) : MemberEditEvent()
}
