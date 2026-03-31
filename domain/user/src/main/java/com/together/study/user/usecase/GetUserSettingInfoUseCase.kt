package com.together.study.user.usecase

import com.together.study.user.model.UserSettingInfo
import com.together.study.user.repository.UserRepository
import javax.inject.Inject

class GetUserSettingInfoUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): Result<UserSettingInfo> {
        return userRepository.getUserSettingInfo()
    }
}
