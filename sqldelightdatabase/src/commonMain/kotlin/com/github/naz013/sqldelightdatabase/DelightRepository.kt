package com.github.naz013.sqldelightdatabase

interface DelightRepository {
    fun getLast(): DelightModel?
    fun getFirst(): DelightModel?
    fun insert(delight: DelightModel)
}