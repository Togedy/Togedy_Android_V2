package com.together.study.mypage.datasource

import com.together.study.mypage.dto.ContactUsRequest
import com.together.study.mypage.service.MyPageService
import javax.inject.Inject

class MyPageDataSource @Inject constructor(
    private val myPageService: MyPageService,
) {
    suspend fun getPrivacyPolicy() = myPageService.getPrivacyPolicy()
    suspend fun getTermsOfService() = myPageService.getTermsOfService()
    suspend fun getNotices() = myPageService.getNotices()
    suspend fun getNoticeDetail(noticeId: Long) = myPageService.getNoticeDetail(noticeId)
    suspend fun postContactUs(request: ContactUsRequest) = myPageService.postContactUs(request)
}
