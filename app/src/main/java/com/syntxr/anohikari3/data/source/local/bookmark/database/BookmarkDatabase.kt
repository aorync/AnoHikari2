package com.syntxr.anohikari3.data.source.local.bookmark.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.syntxr.anohikari3.data.source.local.bookmark.entity.Bookmark

@Database(
    entities = [Bookmark::class],
    version = 1,
    exportSchema = true,
)
abstract class BookmarkDatabase : RoomDatabase() {
    abstract val bookmarkDao: BookmarkDao

    companion object{
        const val DB_NAME = "bookmark.db"
    }
}