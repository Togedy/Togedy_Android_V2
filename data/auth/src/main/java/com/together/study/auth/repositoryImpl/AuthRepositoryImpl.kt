package com.together.study.auth.repositoryImpl

import com.together.study.auth.datasource.AuthDataSource
import com.together.study.auth.mapper.toDomain
import com.together.study.auth.model.KakaoLogin
import com.together.study.auth.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
) : AuthRepository {
    override suspend fun loginWithKakao(accessToken: String): Result<KakaoLogin> =
        runCatching {
            authDataSource.loginWithKakao(accessToken).toDomain()
        }

}
