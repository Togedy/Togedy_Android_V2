package com.together.study.mypage.usecase

import com.together.study.mypage.model.Notice
import com.together.study.mypage.repository.MyPageRepository
import javax.inject.Inject

data class GetNoticesUseCase @Inject constructor(
    private val myPageRepository: MyPageRepository,
) {
    suspend operator fun invoke(): Result<List<Notice>> {
        return myPageRepository.getNotices()
    }
}
