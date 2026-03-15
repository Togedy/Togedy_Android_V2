package com.together.study.user.service

import com.together.study.remote.model.BaseResponse
import com.together.study.remote.model.EmptyDataResponse
import com.together.study.user.dto.MarketingConsentedRequest
import com.together.study.user.dto.NotificationSettingsRequest
import com.together.study.user.dto.UserInfoResponse
import com.together.study.user.dto.UserSettingInfoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part

interface UserService {
    @GET("users/me")
    suspend fun getUserInfo(): BaseResponse<UserInfoResponse>

    @GET("users/me/settings")
    suspend fun getUserSettingInfo(): BaseResponse<UserSettingInfoResponse>

    @Multipart
    @PATCH("users/me")
    suspend fun patchUserProfileSettings(
        @Part("nickname") nickname: RequestBody?,
        @Part userProfileImage: MultipartBody.Part?,
        @Part("removeUserProfileImage") removeUserProfileImage: RequestBody,
    ): EmptyDataResponse

    @PATCH("users/me/settings/push")
    suspend fun patchNotificationSettings(
        @Body request: NotificationSettingsRequest,
    ): EmptyDataResponse

    @PATCH("users/me/settings/marketing")
    suspend fun patchMarketingConsented(
        @Body request: MarketingConsentedRequest,
    ): EmptyDataResponse
}
