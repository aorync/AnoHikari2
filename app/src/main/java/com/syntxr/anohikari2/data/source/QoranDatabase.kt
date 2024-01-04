package com.syntxr.anohikari2.data.source

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.syntxr.anohikari2.domain.model.Jozz
import com.syntxr.anohikari2.domain.model.Qoran
import com.syntxr.anohikari2.domain.model.Sora

@Database(
    version = 3,
    entities = [Qoran::class],
    views = [Sora::class, Jozz::class],
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 2, to = 3)
    ]
)
abstract class QoranDatabase: RoomDatabase() {
    abstract val qoranDao: QuranDao

    companion object{
        const val DB_NAME = "qoran.db"
    }
}