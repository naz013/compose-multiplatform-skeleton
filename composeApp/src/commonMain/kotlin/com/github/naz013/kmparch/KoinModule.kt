package com.github.naz013.kmparch

import com.github.naz013.datastore.datastoreKoinModules
import com.github.naz013.roomdatabase.repositoryKoinModules
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

fun allModules(): List<Module> {
    return listOf<Module>() +
            repositoryKoinModules() +
            datastoreKoinModules()
}

fun initKoin(config: KoinAppDeclaration? = null) = startKoin {
    config?.invoke(this)
    modules(allModules())
}
