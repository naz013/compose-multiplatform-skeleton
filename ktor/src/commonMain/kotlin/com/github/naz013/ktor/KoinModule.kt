package com.github.naz013.ktor

import com.github.naz013.ktor.weather.CurrentWeatherApi
import com.github.naz013.ktor.weather.CurrentWeatherApiImpl
import org.koin.core.module.Module
import org.koin.dsl.module

internal fun ktorModule(): Module = module {
    factory { ClientFactory() }
    factory { CurrentWeatherApiImpl(get()) as CurrentWeatherApi }
}

fun ktorKoinModules(): List<Module> {
    return listOf(ktorModule())
}
