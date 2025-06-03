@file:OptIn(ExperimentalForeignApi::class)

package com.github.naz013.roomdatabase

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

internal fun getDatabaseBuilder(): RoomDatabase.Builder<MainDatabase> {
    val dbFilePath = documentDirectory() + "/${Config.DATABASE_NAME}"
    return Room.databaseBuilder<MainDatabase>(
        name = dbFilePath,
    )
}

private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    ) // TODO Change directory
    return requireNotNull(documentDirectory?.path)
}
