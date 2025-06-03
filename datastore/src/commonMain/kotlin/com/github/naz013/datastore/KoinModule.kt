package com.github.naz013.datastore

import com.github.naz013.datastore.example.ExampleSettings
import com.github.naz013.datastore.example.ExampleSettingsImpl
import org.koin.core.module.Module
import org.koin.dsl.module

internal expect fun factoryModule(): Module

internal fun datastoreModule() = module {
    factory { ExampleSettingsImpl(get()) as ExampleSettings }
}

fun datastoreKoinModules(): List<Module> {
    return listOf(
        factoryModule(),
        datastoreModule()
    )
}
