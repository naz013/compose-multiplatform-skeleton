package com.github.naz013.datastore

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun factoryModule(): Module = module {
    single { DataStoreFactory(androidContext()) }
}
