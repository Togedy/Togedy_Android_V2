package com.together.study.remote

import com.together.study.local.TokenDataStore
import data.remote.BuildConfig.ACCESS_TOKEN
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(
    private val tokenDataStore: TokenDataStore,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addAccessTokenHeader()
            .build()
        return chain.proceed(newRequest)
    }

    private fun Request.Builder.addAccessTokenHeader(): Request.Builder = runBlocking {
//        val accessToken = tokenDataStore.getAccessToken()
        addHeader("Authorization", "Bearer $ACCESS_TOKEN")
    }
}