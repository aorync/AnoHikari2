package com.syntxr.anohikari2.domain.usecase

import com.syntxr.anohikari2.domain.model.Adzan
import com.syntxr.anohikari2.domain.repository.AdzanRepository
import com.syntxr.anohikari2.utils.Resource
import kotlinx.coroutines.flow.Flow

class AdzanUseCase(
    private val repository: AdzanRepository
) {
    fun getAdzans(latitude: Double, longitude: Double) : Flow<Resource<List<Adzan>>> {
        return repository.getAdzan(latitude, longitude)
    }
}