package com.github.naz013.roomdatabase.example

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
internal interface ExampleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ExampleEntity)

    @Query("SELECT * FROM ExampleEntity ORDER BY id ASC LIMIT 1")
    suspend fun getFirst(): ExampleEntity?

    @Query("SELECT * FROM ExampleEntity ORDER BY id DESC LIMIT 1")
    suspend fun getLast(): ExampleEntity?
}
