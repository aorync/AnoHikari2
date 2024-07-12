package com.syntxr.anohikari3.data.repository

import com.syntxr.anohikari3.data.source.local.bookmark.database.BookmarkDao
import com.syntxr.anohikari3.data.source.local.bookmark.entity.Bookmark
import com.syntxr.anohikari3.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow

class BookmarkRepositoryImpl(
    private val dao: BookmarkDao
) : BookmarkRepository {
    override fun getBookmark(): Flow<List<Bookmark>> {
        return dao.getBookmark()
    }

    override suspend fun insertBookmark(bookmark: Bookmark) {
        return dao.insertBookmark(bookmark)
    }

    override suspend fun findById(id: Int): List<Bookmark> {
        return dao.findById(id)
    }

    override suspend fun deleteBookmark(bookmark: Bookmark) {
        return dao.deleteBookmark(bookmark)
    }

    override suspend fun deleteAllBookmark() {
        return dao.deleteAllBookmark()
    }
}