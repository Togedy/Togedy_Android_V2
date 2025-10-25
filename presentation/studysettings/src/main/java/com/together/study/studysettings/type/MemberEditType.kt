package com.together.study.studysettings.type

enum class MemberEditType(val title: String, val chipText: String? = null) {
    EDIT("멤버 관리", "내보내기"),
    SHOW("멤버 보기"),
    LEADER_CHANGE("방장 위임 설정", "선택"),
}
