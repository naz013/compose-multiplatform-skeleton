package com.github.naz013.datastore

import com.github.naz013.datastore.example.ExampleSettings
import com.github.naz013.datastore.example.ExampleSettingsImpl
import com.github.naz013.localization.LocalizationSettings
import org.koin.core.module.Module
import org.koin.dsl.module

internal expect fun factoryModule(): Module

internal fun datastoreModule() = module {
    factory { ExampleSettingsImpl(get()) as ExampleSettings }
    factory { LocalizationSettingsImpl(get()) as LocalizationSettings }
}

fun datastoreKoinModules(): List<Module> {
    return listOf(
        factoryModule(),
        datastoreModule()
    )
}
