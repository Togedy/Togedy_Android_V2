package com.together.study.user.repositoryimpl

import androidx.core.net.toUri
import com.together.study.user.datasource.UserDataSource
import com.together.study.user.mapper.toDomain
import com.together.study.user.model.NicknameValidation
import com.together.study.user.model.UserInfo
import com.together.study.user.model.UserSettingInfo
import com.together.study.user.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
) : UserRepository {
    override suspend fun getUserInfo(): Result<UserInfo> =
        runCatching {
            val response = userDataSource.getUserInfo().response
            response.toDomain()
        }

    override suspend fun getUserSettingInfo(): Result<UserSettingInfo> =
        runCatching {
            val response = userDataSource.getUserSettingInfo().response
            response.toDomain()
        }

    override suspend fun getNicknameSuggestion(): Result<String> =
        runCatching {
            val response = userDataSource.getNicknameSuggestion().response
            response.nickname
        }

    override suspend fun validateNickname(nickname: String): Result<NicknameValidation> =
        runCatching {
            val response = userDataSource.checkNicknameDuplication(nickname).response
            response.toDomain()
        }

    override suspend fun updateUserInfo(
        nickname: String?,
        userProfileImage: String?,
        removeUserProfileImage: Boolean,
    ): Result<Unit> =
        runCatching {
            val uri = userProfileImage?.toUri()
            userDataSource.patchUserProfileSettings(
                nickname = nickname,
                userProfileImage = uri,
                removeUserProfileImage = removeUserProfileImage,
            )
        }

    override suspend fun completeOnboarding(nickname: String, birthDate: String): Result<Unit> =
        runCatching {
            userDataSource.patchOnboarding(nickname, birthDate)
        }

    override suspend fun updatePushAlarmSetting(pushNotificationEnabled: Boolean): Result<Unit> =
        runCatching {
            userDataSource.patchNotificationSettings(pushNotificationEnabled)
        }

    override suspend fun updateMarketingConsent(marketingConsented: Boolean): Result<Unit> =
        runCatching {
            userDataSource.patchMarketingConsented(marketingConsented)
        }
}
