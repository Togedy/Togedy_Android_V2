package com.together.study.mypage.usecase

import com.together.study.mypage.repository.MyPageRepository
import javax.inject.Inject

class PostContactUsUseCase @Inject constructor(
    private val myPageRepository: MyPageRepository,
) {
    suspend operator fun invoke(
        inquiryType: String,
        inquiryContent: String,
        replyEmail: String,
    ): Result<Unit> {
        return myPageRepository.postContactUs(
            inquiryType = inquiryType,
            inquiryContent = inquiryContent,
            replyEmail = replyEmail,
        )
    }
}
