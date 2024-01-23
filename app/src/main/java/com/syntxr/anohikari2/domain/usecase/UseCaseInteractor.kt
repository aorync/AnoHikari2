package com.syntxr.anohikari2.domain.usecase

import com.syntxr.anohikari2.domain.repository.BookmarkRepository
import com.syntxr.anohikari2.domain.repository.QoranRepository

class UseCaseInteractor(
    private val qoranRepository: QoranRepository,
    private val bookmarkRepository: BookmarkRepository
) : AppUseCase{

    override val qoranUseCase: QoranUseCase
        get() = QoranUseCase(qoranRepository)
    override val bookmarkUseCase: BookmarkUseCase
        get() = BookmarkUseCase(bookmarkRepository)
}