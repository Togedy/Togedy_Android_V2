package com.together.study.study.type

enum class MemberEditType(val title: String, val chipText: String) {
    SHOW("스터디 멤버", ""),
    EDIT("멤버 관리", "내보내기"),
    LEADER_CHANGE("방장 위임 설정", "선택"),
}
