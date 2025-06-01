package com.github.naz013.roomdatabase

import com.github.naz013.roomdatabase.example.ExampleRepository
import com.github.naz013.roomdatabase.example.ExampleRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

internal expect fun databaseModule(): Module

internal fun repositoryModule() = module {
    single { get<MainDatabase>().exampleDao() }

    factory { ExampleRepositoryImpl(get()) as ExampleRepository }
}

fun repositoryKoinModules(): List<Module> {
    return listOf(
        databaseModule(),
        repositoryModule()
    )
}
