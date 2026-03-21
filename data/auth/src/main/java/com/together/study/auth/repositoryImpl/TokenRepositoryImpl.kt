package com.together.study.auth.repositoryImpl

import com.together.study.auth.repository.TokenRepository
import com.together.study.local.TokenDataStore
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenDataStore: TokenDataStore,
) : TokenRepository {
    override suspend fun getAccessToken(): String? = tokenDataStore.getAccessToken()
    override suspend fun getRefreshToken(): String? = tokenDataStore.getRefreshToken()
}
