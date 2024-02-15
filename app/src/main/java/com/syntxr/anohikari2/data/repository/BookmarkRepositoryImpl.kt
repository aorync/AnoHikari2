package com.syntxr.anohikari2.data.repository

import com.syntxr.anohikari2.data.source.local.bookmark.BookmarkDao
import com.syntxr.anohikari2.domain.model.Bookmark
import com.syntxr.anohikari2.domain.repository.BookmarkRepository
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