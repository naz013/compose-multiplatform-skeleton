package com.github.naz013.roomdatabase.example

interface ExampleRepository {
    suspend fun insert(example: Example)
    suspend fun getFirst(): Example?
    suspend fun getLast(): Example?
}
