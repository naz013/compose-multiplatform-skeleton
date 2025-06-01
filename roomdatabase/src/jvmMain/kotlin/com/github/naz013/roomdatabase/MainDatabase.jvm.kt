package com.github.naz013.roomdatabase

import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.naz013.logging.Logger
import java.io.File

internal fun getDatabaseBuilder(): RoomDatabase.Builder<MainDatabase> {
    val tmpDir = System.getProperty("java.io.tmpdir")
    Logger.d("MainDatabase", "Temporary directory: $tmpDir")
    val dbFile = File(tmpDir, "main.db") // TODO Change to a proper database name
    return Room.databaseBuilder<MainDatabase>(
        name = dbFile.absolutePath,
    )
}
