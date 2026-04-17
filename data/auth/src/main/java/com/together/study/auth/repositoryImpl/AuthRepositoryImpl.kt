package com.together.study.auth.repositoryImpl

import com.together.study.auth.datasource.AuthDataSource
import com.together.study.auth.mapper.toDomain
import com.together.study.auth.model.KakaoLogin
import com.together.study.auth.repository.AuthRepository
import com.together.study.local.TokenDataStore
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val tokenDataStore: TokenDataStore,
) : AuthRepository {
    override suspend fun postLoginKakao(accessToken: String): Result<KakaoLogin> =
        runCatching {
            val response = authDataSource.postLoginKakao(accessToken)
            val normalizedAccessToken = response.jwtTokenInfo.accessToken.removeBearerPrefix()
            val normalizedRefreshToken = response.jwtTokenInfo.refreshToken.removeBearerPrefix()

            tokenDataStore.setTokens(
                accessToken = normalizedAccessToken,
                refreshToken = normalizedRefreshToken,
            )

            response.toDomain()
        }

    private fun String.removeBearerPrefix(): String = removePrefix(BEARER_PREFIX).trim()

    override suspend fun postLogout(): Result<Unit> =
        runCatching {
            authDataSource.postLogout()
            tokenDataStore.clearTokens()
        }

    companion object {
        private const val BEARER_PREFIX = "Bearer "
    }
}
