package com.github.naz013.roomdatabase

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.github.naz013.roomdatabase.example.ExampleDao
import com.github.naz013.roomdatabase.example.ExampleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(
    entities = [
        ExampleEntity::class // TODO Add your entities here
    ],
    version = 1,
    exportSchema = false
)
@ConstructedBy(AppDatabaseConstructor::class)
internal abstract class MainDatabase : RoomDatabase() {
    // TODO Add your DAOs here
    abstract fun exampleDao(): ExampleDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
internal expect object AppDatabaseConstructor : RoomDatabaseConstructor<MainDatabase>

internal fun getRoomDatabase(
    builder: RoomDatabase.Builder<MainDatabase>
): MainDatabase {
    return builder
//        .addMigrations(MIGRATIONS)
        .fallbackToDestructiveMigrationOnDowngrade(dropAllTables = false)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
