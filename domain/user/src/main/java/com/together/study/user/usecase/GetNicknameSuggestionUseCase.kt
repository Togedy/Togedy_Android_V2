package com.together.study.user.usecase

import com.together.study.user.repository.UserRepository
import javax.inject.Inject

class GetNicknameSuggestionUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): Result<String> {
        return userRepository.getNicknameSuggestion()
    }
}
