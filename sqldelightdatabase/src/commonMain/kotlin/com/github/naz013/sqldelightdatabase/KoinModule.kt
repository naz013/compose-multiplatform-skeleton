package com.github.naz013.sqldelightdatabase

import org.koin.core.module.Module
import org.koin.dsl.module

internal expect fun delightDriverModule(): Module

internal fun delightRepositoryModule() = module {
    single { get<DriverFactory>().createDriver() }
    factory { DelightRepositoryImpl(get()) as DelightRepository }
}

fun delightKoinModules(): List<Module> {
    return listOf(
        delightDriverModule(),
        delightRepositoryModule()
    )
}
