package com.together.study.user.usecase

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
        if (userName == null && userProfileImage == null && !removeUserProfileImage) {
            return Result.failure(IllegalArgumentException("No changes to update"))
        }

        return userRepository.updateUserInfo(
            nickname = userName,
            userProfileImage = userProfileImage,
            removeUserProfileImage = removeUserProfileImage,
        )
    }
}
