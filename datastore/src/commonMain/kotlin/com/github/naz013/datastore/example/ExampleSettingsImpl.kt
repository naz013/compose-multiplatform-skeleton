package com.github.naz013.datastore.example

import androidx.datastore.preferences.core.intPreferencesKey
import com.github.naz013.datastore.DataStoreFactory
import com.github.naz013.datastore.example.ExampleSettingsImpl.PreferencesKeys.INCREMENT
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal class ExampleSettingsImpl(
    private val dataStoreFactory: DataStoreFactory
) : ExampleSettings {

    private val dataStore = dataStoreFactory.createDataStore("example_settings.preferences_pb")
    private val flow: Flow<Content> = dataStore.data.map { preferences ->
        val increment = preferences[INCREMENT] ?: 0
        Content(increment)
    }

    override suspend fun getIncrement(): Int {
        return flow.first().increment
    }

    override suspend fun setIncrement(value: Int) {
        dataStore.updateData { preferences ->
            preferences.toMutablePreferences().apply {
                this[INCREMENT] = value
            }
        }
    }

    private data class Content(
        val increment: Int
    )

    private object PreferencesKeys {
        val INCREMENT = intPreferencesKey("increment")
    }
}
