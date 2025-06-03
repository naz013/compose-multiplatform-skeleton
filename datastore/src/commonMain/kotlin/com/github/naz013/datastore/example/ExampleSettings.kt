package com.github.naz013.datastore.example

interface ExampleSettings {
    suspend fun getIncrement(): Int
    suspend fun setIncrement(value: Int)
}
