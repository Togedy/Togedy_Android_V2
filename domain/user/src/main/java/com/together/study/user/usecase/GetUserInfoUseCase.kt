package com.together.study.user.usecase

import com.together.study.user.model.UserInfo
import com.together.study.user.repository.UserRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): Result<UserInfo> {
        return userRepository.getUserInfo()
    }
}
