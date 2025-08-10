package com.github.naz013.datastore

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.github.naz013.localization.LocalizationSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal class LocalizationSettingsImpl(
    dataStoreFactory: DataStoreFactory
) : LocalizationSettings {

    private val dataStore = dataStoreFactory.createDataStore("app_preferences.preferences_pb")

    override val localeFlow: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[PREFERENCE_KEY_LOCALE] ?: ""
        }

    override suspend fun setLocale(locale: String) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_KEY_LOCALE] = locale
        }
    }

    override suspend fun getLocale(): String {
        return localeFlow.first()
    }

    companion object {
        private val PREFERENCE_KEY_LOCALE = stringPreferencesKey("locale")
    }
}
