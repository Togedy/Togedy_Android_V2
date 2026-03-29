package com.together.study.auth.repository

interface TokenRepository {
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
}
