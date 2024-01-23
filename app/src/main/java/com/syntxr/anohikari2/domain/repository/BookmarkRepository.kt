package com.syntxr.anohikari2.domain.repository

import com.syntxr.anohikari2.domain.model.Bookmark
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {

    fun getBookmark() : Flow<List<Bookmark>>
    suspend fun insertBookmark(bookmark: Bookmark)

    suspend fun findById(id: Int) : List<Bookmark>
    suspend fun deleteBookmark(bookmark: Bookmark)
    suspend fun deleteAllBookmark()
}