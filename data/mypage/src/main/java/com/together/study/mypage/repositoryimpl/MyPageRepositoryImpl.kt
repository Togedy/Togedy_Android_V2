package com.together.study.mypage.repositoryimpl

import com.together.study.mypage.datasource.MyPageDataSource
import com.together.study.mypage.dto.ContactUsRequest
import com.together.study.mypage.mapper.toDomain
import com.together.study.mypage.mapper.toDomainList
import com.together.study.mypage.model.Notice
import com.together.study.mypage.repository.MyPageRepository
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(
    private val myPageDataSource: MyPageDataSource,
) : MyPageRepository {
    override suspend fun getPolicyPrivacy(): Result<String> =
        runCatching {
            val response = myPageDataSource.getPrivacyPolicy().response
            response.privacyPolicy
        }

    override suspend fun getTermsOfService(): Result<String> =
        runCatching {
            val response = myPageDataSource.getTermsOfService().response
            response.termsOfService
        }

    override suspend fun getNotices(): Result<List<Notice>> =
        runCatching {
            val response = myPageDataSource.getNotices().response
            response.map { it.toDomainList() }
        }

    override suspend fun getNoticeDetail(noticeId: Long): Result<Notice> =
        runCatching {
            val response = myPageDataSource.getNoticeDetail(noticeId).response
            response.toDomain()
        }

    override suspend fun postContactUs(
        inquiryType: String,
        inquiryContent: String,
        replyEmail: String
    ): Result<Unit> = runCatching {
        val request = ContactUsRequest(
            inquiryType = inquiryType,
            inquiryContent = inquiryContent,
            replyEmail = replyEmail,
        )

        myPageDataSource.postContactUs(request)
    }
}
