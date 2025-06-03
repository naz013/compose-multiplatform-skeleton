package com.github.naz013.roomdatabase

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

internal fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<MainDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath(Config.DATABASE_NAME)
    return Room.databaseBuilder<MainDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}
