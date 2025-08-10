package com.github.naz013.localization

import org.koin.core.module.Module
import org.koin.dsl.module

internal fun localizationModule() = module {
    factory { LocalizationViewModel(get()) }
    single { LocalizationManager(get()) }
}

fun localizationKoinModules(): List<Module> {
    return listOf(localizationModule())
}
