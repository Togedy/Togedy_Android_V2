package com.together.study.user.usecase

import com.together.study.user.model.NicknameValidation
import com.together.study.user.repository.UserRepository
import javax.inject.Inject

class ValidateNicknameUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(nickname: String): Result<NicknameValidation> {
        return userRepository.validateNickname(nickname)
    }
}
