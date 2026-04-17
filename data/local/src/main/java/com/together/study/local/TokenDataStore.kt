package com.together.study.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenDataStore @Inject constructor(
    private val preferenceDataStore: DataStore<Preferences>
) {
    suspend fun getAccessToken(): String? = preferenceDataStore.data.map { preferences ->
        preferences[preferencesAccessTokenKey]
    }.firstOrNull()

    suspend fun getRefreshToken(): String? = preferenceDataStore.data.map { preferences ->
        preferences[preferencesRefreshTokenKey]
    }.firstOrNull()

    suspend fun setTokens(accessToken: String, refreshToken: String) {
        preferenceDataStore.edit { preferences ->
            preferences[preferencesAccessTokenKey] = accessToken
            preferences[preferencesRefreshTokenKey] = refreshToken
        }
    }

    suspend fun updateAccessToken(token: String?) {
        preferenceDataStore.edit { preferences ->
            token?.let {
                preferences[preferencesAccessTokenKey] = it
            } ?: preferences.remove(preferencesAccessTokenKey)
        }
    }

    suspend fun updateRefreshToken(token: String?) {
        preferenceDataStore.edit { preferences ->
            token?.let {
                preferences[preferencesRefreshTokenKey] = it
            } ?: preferences.remove(preferencesRefreshTokenKey)
        }
    }

    suspend fun clearTokens() {
        preferenceDataStore.edit { preferences ->
            preferences.remove(preferencesAccessTokenKey)
            preferences.remove(preferencesRefreshTokenKey)
        }
    }

    companion object {
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val REFRESH_TOKEN_KEY = "refresh_token"
        private val preferencesAccessTokenKey = stringPreferencesKey(ACCESS_TOKEN_KEY)
        private val preferencesRefreshTokenKey = stringPreferencesKey(REFRESH_TOKEN_KEY)

    }
}
