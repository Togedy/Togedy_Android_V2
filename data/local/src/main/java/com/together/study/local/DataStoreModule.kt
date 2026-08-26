package com.together.study.local

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    private const val TOKEN_PREFERENCE_NAME = "token_preference"
    private const val CHAT_BOT_PREFERENCE_NAME = "chat_bot_preference"

    private val Context.provideDataStore by preferencesDataStore(TOKEN_PREFERENCE_NAME)
    private val Context.provideChatBotDataStore by preferencesDataStore(CHAT_BOT_PREFERENCE_NAME)

    @Provides
    @Singleton
    fun provideTokenDataStore(
        @ApplicationContext context: Context
    ) = TokenDataStore(context.provideDataStore)

    @Provides
    @Singleton
    fun provideChatBotDataStore(
        @ApplicationContext context: Context
    ) = ChatBotDataStore(context.provideChatBotDataStore)
}
