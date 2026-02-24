package com.together.study.chatbot.di

import com.together.study.chatbot.service.ChatBotService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideChatBotService(retrofit: Retrofit): ChatBotService =
        retrofit.create()
}