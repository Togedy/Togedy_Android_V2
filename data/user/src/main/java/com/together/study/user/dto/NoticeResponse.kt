package com.together.study.user.dto

import kotlinx.serialization.Serializable

@Serializable
data class NoticeResponse(
    val noticeId: Long? = null,
    val noticeTitle: String,
    val publishedAt: String,
    val noticeContent: String = "",
)
