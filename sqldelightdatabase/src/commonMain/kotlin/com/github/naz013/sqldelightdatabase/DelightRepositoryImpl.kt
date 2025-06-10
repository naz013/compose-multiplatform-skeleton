package com.github.naz013.sqldelightdatabase

import app.cash.sqldelight.db.SqlDriver

internal class DelightRepositoryImpl(
    sqlDriver: SqlDriver
) : DelightRepository {

    private val database = Database(sqlDriver)

    override fun getLast(): DelightModel? {
        return database.delightQueries.selectLast().executeAsOneOrNull()?.toDomain()
    }

    override fun getFirst(): DelightModel? {
        return database.delightQueries.selectFirst().executeAsOneOrNull()?.toDomain()
    }

    override fun insert(delight: DelightModel) {
        database.delightQueries.insert(delight.name)
    }

    private fun Delight.toDomain() = DelightModel(
        id = id,
        name = name
    )
}
