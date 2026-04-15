package com.together.study.remote

import com.together.study.common.event.TogedyUiEvent
import com.together.study.common.event.TogedyUiEventBus
import com.together.study.local.TokenDataStore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import okhttp3.Authenticator
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val tokenDataStore: TokenDataStore,
    private val json: Json,
    private val client: OkHttpClient,
) : Authenticator {

    private val mutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.request.header(RETRY_HEADER) != null) {
            return null
        }

        val failedAccessToken = response.request.header("Authorization")
            ?.removePrefix("Bearer ")

        return runBlocking {
            mutex.withLock {
                val currentAccessToken = tokenDataStore.getAccessToken()

                if (currentAccessToken != null && currentAccessToken != failedAccessToken) {
                    return@withLock response.request.newBuilder()
                        .header("Authorization", "Bearer $currentAccessToken")
                        .header(RETRY_HEADER, "true")
                        .build()
                }

                val refreshToken = tokenDataStore.getRefreshToken()

                if (refreshToken.isNullOrBlank()) {
                    handleForceLogout()
                    return@withLock null
                }

                val newAccessToken = reissueToken(refreshToken)

                if (newAccessToken != null) {
                    response.request.newBuilder()
                        .header("Authorization", "Bearer $newAccessToken")
                        .header(RETRY_HEADER, "true")
                        .build()
                } else {
                    handleForceLogout()
                    null
                }
            }
        }
    }

    private suspend fun reissueToken(refreshToken: String): String? {
        return try {
            val request = Request.Builder()
                .url("${data.remote.BuildConfig.BASE_URL}auth/reissue")
                .post("".toRequestBody("application/json".toMediaType()))
                .header("Refresh-Token", refreshToken)
                .build()

            client.newCall(request).execute().use { reissueResponse ->
                if (!reissueResponse.isSuccessful) {
                    Timber.e("토큰 재발급을 실패하였습니다. : ${reissueResponse.code}")
                    return@use null
                }

                val body = reissueResponse.body?.string() ?: return@use null
                val jsonElement = json.parseToJsonElement(body)
                val responseObj =
                    jsonElement.jsonObject["response"]?.jsonObject ?: return@use null

                val accessToken = responseObj["accessToken"]?.jsonPrimitive?.content
                    ?.removePrefix("Bearer ") ?: return@use null
                val newRefreshToken = responseObj["refreshToken"]?.jsonPrimitive?.content
                    ?.removePrefix("Bearer ") ?: return@use null

                tokenDataStore.setTokens(accessToken, newRefreshToken)

                Timber.d("토큰 재발급 성공")
                accessToken
            }
        } catch (e: Exception) {
            Timber.e(e, "토큰 재발급 실패")
            null
        }
    }

    private suspend fun handleForceLogout() {
        tokenDataStore.clearTokens()
        TogedyUiEventBus.send(TogedyUiEvent.ForceLogout)
        Timber.d("토큰 재발급 실패로 인한 로그아웃")
    }

    companion object {
        private const val RETRY_HEADER = "X-Token-Retry"
    }
}
