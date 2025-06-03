package com.github.naz013.roomdatabase

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

internal fun getDatabaseBuilder(): RoomDatabase.Builder<MainDatabase> {
    val tmpDir = System.getProperty("java.io.tmpdir") // TODO Change directory
    val dbFile = File(tmpDir, Config.DATABASE_NAME)
    return Room.databaseBuilder<MainDatabase>(
        name = dbFile.absolutePath,
    )
}
