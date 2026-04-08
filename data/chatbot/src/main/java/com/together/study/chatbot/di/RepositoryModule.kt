package com.together.study.chatbot.di

import com.together.study.chatbot.repository.ChatBotRepository
import com.together.study.chatbot.repositoryimpl.ChatBotRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindChatBotRepository(
        chatBotRepositoryImpl: ChatBotRepositoryImpl,
    ): ChatBotRepository
}