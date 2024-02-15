package com.syntxr.anohikari2.data.source.local.qoran

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.syntxr.anohikari2.domain.model.Jozz
import com.syntxr.anohikari2.domain.model.Qoran
import com.syntxr.anohikari2.domain.model.Sora

@Database(
    version = 1,
    entities = [Qoran::class],
    views = [Sora::class, Jozz::class],
    exportSchema = true,
)
abstract class QoranDatabase: RoomDatabase() {
    abstract val qoranDao: QuranDao

    companion object{
        const val DB_NAME = "qoran.db"
    }
}