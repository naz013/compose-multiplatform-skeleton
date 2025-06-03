package com.github.naz013.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

internal actual class DataStoreFactory(
    private val context: Context
) {
    actual fun createDataStore(filename: String): DataStore<Preferences> {
        return createDataStore { context.filesDir.resolve(filename).absolutePath } // TODO Change directory
    }
}
