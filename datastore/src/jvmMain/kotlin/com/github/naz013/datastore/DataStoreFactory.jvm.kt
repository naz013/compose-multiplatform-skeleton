package com.github.naz013.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import java.io.File

internal actual class DataStoreFactory {
    actual fun createDataStore(filename: String): DataStore<Preferences> {
        return createDataStore {
            val tmpDir = System.getProperty("java.io.tmpdir") // TODO Change directory
            val file = File(tmpDir, filename)
            file.absolutePath
        }
    }
}
