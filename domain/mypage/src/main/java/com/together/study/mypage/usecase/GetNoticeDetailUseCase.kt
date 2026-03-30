package com.together.study.mypage.usecase

import com.together.study.mypage.model.Notice
import com.together.study.mypage.repository.MyPageRepository
import javax.inject.Inject

class GetNoticeDetailUseCase @Inject constructor(
    private val myPageRepository: MyPageRepository,
) {
    suspend operator fun invoke(noticeId: Long): Result<Notice> {
        return myPageRepository.getNoticeDetail(noticeId)
    }
}
