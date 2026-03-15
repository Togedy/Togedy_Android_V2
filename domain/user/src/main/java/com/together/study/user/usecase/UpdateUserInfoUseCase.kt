package com.together.study.user.usecase

import com.together.study.user.model.UserProfileSettings
import com.together.study.user.repository.UserRepository
import javax.inject.Inject

class UpdateUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        userName: String?,
        userProfileImage: String?,
        removeUserProfileImage: Boolean,
    ): Result<Unit> {
        val request = UserProfileSettings(
            nickname = userName,
            userProfileImage = userProfileImage,
            removeUserProfileImage = removeUserProfileImage,
        )

        return userRepository.updateUserInfo(request)
    }
}
