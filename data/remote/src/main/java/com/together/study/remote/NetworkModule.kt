package com.together.study.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.together.study.local.TokenDataStore
import com.together.study.remote.qualifier.JWT
import com.together.study.remote.qualifier.NoAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import data.remote.BuildConfig
import data.remote.BuildConfig.BASE_URL
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Converter
import retrofit2.Retrofit
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val APPLICATION_JSON = "application/json"

    @JWT
    @Provides
    @Singleton
    fun providerHeaderInterceptor(
        tokenDataStore: TokenDataStore,
    ): Interceptor = HeaderInterceptor(tokenDataStore)

    @Provides
    @Singleton
    fun provideTokenAuthenticator(
        tokenDataStore: TokenDataStore,
        json: Json,
    ): Authenticator = TokenAuthenticator(tokenDataStore, json)

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    @Provides
    @Singleton
    fun provideJsonConverter(json: Json): Converter.Factory =
        json.asConverterFactory(APPLICATION_JSON.toMediaType())

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): Interceptor = HttpLoggingInterceptor { message ->
        when {
            message.isJsonObject() ->
                Timber.tag("okhttp").d(JSONObject(message).toString(4))

            message.isJsonArray() ->
                Timber.tag("okhttp").d(JSONObject(message).toString(4))

            else -> {
                Timber.tag("okhttp").d("CONNECTION INFO -> $message")
            }
        }
    }.apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    @JWT
    fun provideOkHttpClient(
        loggingInterceptor: Interceptor,
        @JWT headerInterceptor: Interceptor,
        authenticator: Authenticator,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(headerInterceptor)
        .authenticator(authenticator)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    @NoAuth
    fun provideNoAuthOkHttpClient(
        loggingInterceptor: Interceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        @JWT client: OkHttpClient,
        factory: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(factory)
        .build()

    @Provides
    @NoAuth
    @Singleton
    fun provideNoAuthRetrofit(
        factory: Converter.Factory,
        @NoAuth client: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(factory)
        .build()

}
