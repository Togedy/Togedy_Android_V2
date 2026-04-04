package com.together.study.mypage.dto

import kotlinx.serialization.Serializable

@Serializable
data class ContactUsRequest(
    val inquiryType: String,
    val inquiryContent: String,
    val replyEmail: String,
)
