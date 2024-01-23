package com.syntxr.anohikari2.data.source.bookmark

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.syntxr.anohikari2.domain.model.Bookmark

@Database(
    version = 1,
    entities = [Bookmark::class],
    exportSchema = true,
)
abstract class BookmarkDatabase : RoomDatabase() {
    abstract val bookmarkDao: BookmarkDao

    companion object{
        const val DB_NAME = "bookmark.db"
    }
}