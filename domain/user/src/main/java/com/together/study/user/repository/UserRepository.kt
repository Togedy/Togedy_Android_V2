package com.together.study.user.repository

import com.together.study.user.model.UserInfo
import com.together.study.user.model.UserProfileSettings
import com.together.study.user.model.UserSettingInfo

interface UserRepository {
    suspend fun getUserInfo(): Result<UserInfo>
    suspend fun getUserSettingInfo(): Result<UserSettingInfo>
    suspend fun updateUserInfo(request: UserProfileSettings): Result<Unit>

    /* 추후 스프린트 */
    suspend fun updatePushAlarmSetting(pushNotificationEnabled: Boolean): Result<Unit>
    suspend fun updateMarketingConsent(marketingConsented: Boolean): Result<Unit>
}
