package com.syntxr.anohikari3.data.source.local.qoran.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.syntxr.anohikari3.data.source.local.qoran.entity.Jozz
import com.syntxr.anohikari3.data.source.local.qoran.entity.Qoran
import com.syntxr.anohikari3.data.source.local.qoran.entity.Sora

@Database(
    entities = [Qoran::class],
    views = [Sora::class, Jozz::class],
    version = 1,
    exportSchema = true,
)
abstract class QoranDatabase: RoomDatabase() {
    abstract val qoranDao: QuranDao

    companion object{
        const val DB_NAME = "qoran.db"
    }
}