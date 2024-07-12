package com.syntxr.anohikari3.data.source.local.adzan.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.syntxr.anohikari3.data.source.local.adzan.entity.AdzanEntity

@Database(
    entities = [AdzanEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AdzanDatabase : RoomDatabase() {
    abstract val dao: AdzanDao

    companion object {
        const val DB_NAME = "adzan.db"
    }
}