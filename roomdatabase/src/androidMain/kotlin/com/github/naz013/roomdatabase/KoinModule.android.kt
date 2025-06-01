package com.github.naz013.roomdatabase

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun databaseModule(): Module = module {
    single { getRoomDatabase(getDatabaseBuilder(androidContext())) }
}
