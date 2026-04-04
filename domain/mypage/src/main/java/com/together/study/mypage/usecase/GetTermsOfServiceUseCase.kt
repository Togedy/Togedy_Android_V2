package com.together.study.mypage.usecase

import com.together.study.mypage.repository.MyPageRepository
import javax.inject.Inject

class GetTermsOfServiceUseCase @Inject constructor(
    private val myPageRepository: MyPageRepository,
) {
    suspend operator fun invoke(): Result<String> {
        return myPageRepository.getTermsOfService()
    }
}
