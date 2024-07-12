package com.syntxr.anohikari3.domain.repository

import com.syntxr.anohikari3.data.source.local.bookmark.entity.Bookmark
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {

    fun getBookmark() : Flow<List<Bookmark>>
    suspend fun insertBookmark(bookmark: Bookmark)

    suspend fun findById(id: Int) : List<Bookmark>
    suspend fun deleteBookmark(bookmark: Bookmark)
    suspend fun deleteAllBookmark()
}