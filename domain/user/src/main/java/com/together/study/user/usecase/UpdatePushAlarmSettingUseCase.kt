package com.together.study.user.usecase

import com.together.study.user.repository.UserRepository
import javax.inject.Inject

class UpdatePushAlarmSettingUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(pushNotificationEnabled: Boolean): Result<Unit> {
        return userRepository.updatePushAlarmSetting(pushNotificationEnabled)
    }
}
