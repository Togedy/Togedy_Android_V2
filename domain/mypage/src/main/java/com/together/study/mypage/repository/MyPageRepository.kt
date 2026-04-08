package com.together.study.mypage.repository

import com.together.study.mypage.model.Notice

interface MyPageRepository {
    suspend fun getPolicyPrivacy(): Result<String>
    suspend fun getTermsOfService(): Result<String>
    suspend fun getNotices(): Result<List<Notice>>
    suspend fun getNoticeDetail(noticeId: Long): Result<Notice>
    suspend fun postContactUs(
        inquiryType: String,
        inquiryContent: String,
        replyEmail: String,
    ): Result<Unit>
}
