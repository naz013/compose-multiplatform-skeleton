package com.github.naz013.roomdatabase

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

internal fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<MainDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("main.db") // TODO Change to a proper database name
    return Room.databaseBuilder<MainDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}
