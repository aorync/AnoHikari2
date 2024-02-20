package com.syntxr.anohikari2.domain.usecase

import com.syntxr.anohikari2.data.source.local.bookmark.entity.Bookmark
import com.syntxr.anohikari2.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookmarkUseCase(
    private val repository: BookmarkRepository,
) {

    fun getBookmark(isAscend: Boolean): Flow<List<Bookmark>> {
        return repository.getBookmark().map { bookmark ->
            when(isAscend){
                true -> bookmark.sortedBy { it.timeStamp }
                false -> bookmark.sortedByDescending { it.timeStamp }
            }
        }
    }

    suspend fun findById(id: Int) : List<Bookmark> {
        return repository.findById(id)
    }

    suspend fun insertBookmark(bookmark: Bookmark){
        return repository.insertBookmark(bookmark)
    }

    suspend fun deleteBookmark(bookmark: Bookmark){
        return repository.deleteBookmark(bookmark)
    }

    suspend fun deleteAllBookmark(){
        return repository.deleteAllBookmark()
    }
}