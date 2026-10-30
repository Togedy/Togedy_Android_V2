package com.together.study.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * 뱃지 탭에서 마지막으로 확인한 최근 획득 뱃지 ID를 저장
 */
class StudyBadgeDataStore @Inject constructor(
    private val preferenceDataStore: DataStore<Preferences>
) {
    suspend fun getLastCheckedBadgeId(): Long? =
        preferenceDataStore.data.first()[preferencesLastCheckedBadgeIdKey]

    suspend fun updateLastCheckedBadgeId(badgeId: Long) {
        preferenceDataStore.edit { preferences ->
            preferences[preferencesLastCheckedBadgeIdKey] = badgeId
        }
    }

    companion object {
        private const val LAST_CHECKED_BADGE_ID_KEY = "study_badge_last_checked_id"
        private val preferencesLastCheckedBadgeIdKey = longPreferencesKey(LAST_CHECKED_BADGE_ID_KEY)
    }
}
