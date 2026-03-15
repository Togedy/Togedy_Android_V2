package com.together.study.mypage.type

enum class FeedbackType(
    val displayText: String,
    val serverValue: String,
) {
    BUG("버그 신고", "BUG"),
    FEATURE("기능 제안", "FEATURE"),
    USAGE("사용 문의", "USAGE"),
    ACCOUNT("계정/로그인", "ACCOUNT"),
    ETC("기타", "ETC");

    companion object {
        fun fromServerValue(value: String): FeedbackType? {
            return entries.find { it.serverValue == value }
        }
    }
}
