package com.together.study.designsystem.component.tabbar

sealed interface TabType {
    val typeName: String
}

// STUDY
enum class StudyMainTab(override val typeName: String) : TabType {
    MAIN("진행중"),
    EXPLORE("탐색"),
    BADGE("뱃지"),
}

enum class StudyDetailTab(override val typeName: String) : TabType {
    MEMBER("스터디 멤버"),
    DAILY_CHECK("출석부"),
}

enum class StudyMemberTab(override val typeName: String) : TabType {
    STUDY_TIME("공부시간"),
    PLANNER("플래너"),
}
