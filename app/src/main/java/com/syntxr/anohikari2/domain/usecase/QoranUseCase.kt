package com.syntxr.anohikari2.domain.usecase

import com.syntxr.anohikari2.data.source.local.qoran.entity.Jozz
import com.syntxr.anohikari2.data.source.local.qoran.entity.Qoran
import com.syntxr.anohikari2.data.source.local.qoran.entity.Sora
import com.syntxr.anohikari2.domain.repository.QoranRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QoranUseCase(
    private val repository: QoranRepository,
) {

    fun getSora(isAscend: Boolean): Flow<List<Sora>> {
        return repository.getSora().map { sora ->
            when (isAscend) {
                true -> sora.sortedBy { it.soraNo }
                false -> sora.sortedByDescending { it.soraNo }
            }
        }
    }

    fun getJozz(isAscend: Boolean): Flow<List<Jozz>> {
        return repository.getJozz().map { jozz ->
            when (isAscend) {
                true -> jozz.sortedBy { it.jozzNo }
                false -> jozz.sortedByDescending { it.jozzNo }
            }
        }
    }

    fun getSoraAya(soraNo: Int): Flow<List<Qoran>> {
        return repository.getSoraAya(soraNo)
    }

    fun getJozzAya(jozzNo: Int): Flow<List<Qoran>> {
        return repository.getJozzAya(jozzNo)
    }

    fun searchAyaId(search: String): Flow<List<Qoran>> {
        return repository.searchAyaId(search)
    }

    fun searchAyaEn(search: String): Flow<List<Qoran>> {
        return repository.searchAyaEn(search)
    }

    fun searchSora(search: String): Flow<List<Qoran>> {
        return repository.searchSora(search)
    }

}