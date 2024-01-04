package com.syntxr.anohikari2.domain.usecase

import com.syntxr.anohikari2.domain.repository.QoranRepository

class UseCaseInteractor(
    private val repository: QoranRepository
) : AppUseCase{

    override val qoranUseCase: QoranUseCase
        get() = QoranUseCase(repository)
}