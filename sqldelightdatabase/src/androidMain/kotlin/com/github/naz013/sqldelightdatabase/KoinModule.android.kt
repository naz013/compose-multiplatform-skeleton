package com.github.naz013.sqldelightdatabase

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun delightDriverModule(): Module = module {
    single { DriverFactory(androidContext()) }
}
