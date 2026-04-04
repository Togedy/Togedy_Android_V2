package com.together.study.mypage.model

data class Notice(
    val noticeId: Long? = null,
    val noticeTitle: String,
    val publishedAt: String,
    val noticeContent: String = "",
)