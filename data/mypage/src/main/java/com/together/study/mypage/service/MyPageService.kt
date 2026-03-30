package com.together.study.mypage.service

import com.together.study.mypage.dto.ContactUsRequest
import com.together.study.mypage.dto.NoticeResponse
import com.together.study.mypage.dto.PrivacyPolicyResponse
import com.together.study.mypage.dto.TermsOfServicesResponse
import com.together.study.remote.model.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MyPageService {
    @GET("policies/privacy")
    suspend fun getPrivacyPolicy(): BaseResponse<PrivacyPolicyResponse>

    @GET("policies/terms-of-service")
    suspend fun getTermsOfService(): BaseResponse<TermsOfServicesResponse>

    @GET("support/notices")
    suspend fun getNotices(): BaseResponse<List<NoticeResponse>>

    @GET("support/notices/{noticeId}")
    suspend fun getNoticeDetail(
        @Path("noticeId") noticeId: Long,
    ): BaseResponse<NoticeResponse>

    @POST("support/inquiries")
    suspend fun postContactUs(
        @Body request: ContactUsRequest,
    ): BaseResponse<Unit>
}
