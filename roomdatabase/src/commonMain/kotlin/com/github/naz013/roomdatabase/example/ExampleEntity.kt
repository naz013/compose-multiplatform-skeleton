package com.github.naz013.roomdatabase.example

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class ExampleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
)
