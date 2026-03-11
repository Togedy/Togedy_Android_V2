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
    override suspend fun loginWithKakao(accessToken: String): Result<KakaoLogin> =
        runCatching {
            val response = authDataSource.loginWithKakao(accessToken)

            tokenDataStore.setTokens(
                accessToken = response.jwtTokenInfo.accessToken,
                refreshToken = response.jwtTokenInfo.refreshToken,
            )

            response.toDomain()
        }

}
