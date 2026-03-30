package com.together.study.user.datasource

import android.content.Context
import android.net.Uri
import com.together.study.user.dto.MarketingConsentedRequest
import com.together.study.user.dto.NotificationSettingsRequest
import com.together.study.user.service.UserService
import com.together.study.util.ImageConverter
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val userService: UserService,
    @ApplicationContext private val context: Context,
) {
    suspend fun getUserInfo() = userService.getUserInfo()

    suspend fun getUserSettingInfo() = userService.getUserSettingInfo()

    suspend fun checkNicknameDuplication(nickname: String) =
        userService.checkNicknameDuplication(nickname)

    suspend fun patchUserProfileSettings(
        nickname: String?,
        userProfileImage: Uri?,
        removeUserProfileImage: Boolean,
    ) = userService.patchUserProfileSettings(
        nickname = nickname?.toRequestBody("text/plain".toMediaType()),
        userProfileImage = userProfileImage?.let { uri ->
            ImageConverter.uriToFile(context, uri)?.let { file ->
                val requestFile = file.asRequestBody("image/jpeg".toMediaType())
                MultipartBody.Part.createFormData("userProfileImage", file.name, requestFile)
            }
        },
        removeUserProfileImage = removeUserProfileImage
            .toString()
            .toRequestBody("text/plain".toMediaType()),
    )

    /* 추후 스프린트 */
    suspend fun patchNotificationSettings(request: Boolean) = runCatching {
        val request = NotificationSettingsRequest(request)
        userService.patchNotificationSettings(request)
    }

    suspend fun patchMarketingConsented(request: Boolean) = runCatching {
        val request = MarketingConsentedRequest(request)
        userService.patchMarketingConsented(request)
    }
}
