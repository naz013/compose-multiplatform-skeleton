package com.github.naz013.roomdatabase.example

internal class ExampleRepositoryImpl(
    private val dao: ExampleDao
) : ExampleRepository {

    override suspend fun insert(example: Example) {
        dao.insert(example.toEntity())
    }

    override suspend fun getFirst(): Example? {
        return dao.getFirst()?.toDomain()
    }

    override suspend fun getLast(): Example? {
        return dao.getLast()?.toDomain()
    }

    private fun Example.toEntity(): ExampleEntity {
        return ExampleEntity(
            id = this.id,
            name = this.name
        )
    }

    private fun ExampleEntity.toDomain(): Example {
        return Example(
            id = this.id,
            name = this.name
        )
    }
}
