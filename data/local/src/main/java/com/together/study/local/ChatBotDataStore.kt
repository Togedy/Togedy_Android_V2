package com.together.study.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import javax.inject.Inject

/**
 * 챗봇의 하루 질문 횟수를 저장 (10회)
 */
class ChatBotDataStore @Inject constructor(
    private val preferenceDataStore: DataStore<Preferences>
) {
    suspend fun getTodayQuestionCount(): Int {
        val preferences = preferenceDataStore.data.first()

        return preferences.todayQuestionCount()
    }

    suspend fun incrementTodayQuestionCount(): Int {
        var incrementedCount = 0

        preferenceDataStore.edit { preferences ->
            incrementedCount = preferences.todayQuestionCount() + 1
            preferences[preferencesQuestionDateKey] = today()
            preferences[preferencesQuestionCountKey] = incrementedCount
        }

        return incrementedCount
    }

    private fun Preferences.todayQuestionCount(): Int =
        if (this[preferencesQuestionDateKey] == today()) this[preferencesQuestionCountKey] ?: 0
        else 0

    private fun today(): String = LocalDate.now().toString()

    companion object {
        private const val QUESTION_DATE_KEY = "chat_bot_question_date"
        private const val QUESTION_COUNT_KEY = "chat_bot_question_count"
        private val preferencesQuestionDateKey = stringPreferencesKey(QUESTION_DATE_KEY)
        private val preferencesQuestionCountKey = intPreferencesKey(QUESTION_COUNT_KEY)
    }
}
