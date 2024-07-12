package com.syntxr.anohikari3.domain.usecase

import com.syntxr.anohikari3.domain.repository.AdzanRepository
import com.syntxr.anohikari3.domain.repository.BookmarkRepository
import com.syntxr.anohikari3.domain.repository.QoranRepository

class UseCaseInteractor(
    private val qoranRepository: QoranRepository,
    private val bookmarkRepository: BookmarkRepository,
    private val adzanRepository: AdzanRepository
) : AppUseCase{

    override val qoranUseCase: QoranUseCase
        get() = QoranUseCase(qoranRepository)
    override val bookmarkUseCase: BookmarkUseCase
        get() = BookmarkUseCase(bookmarkRepository)
    override val adzanUseCase: AdzanUseCase
        get() = AdzanUseCase(adzanRepository)
}