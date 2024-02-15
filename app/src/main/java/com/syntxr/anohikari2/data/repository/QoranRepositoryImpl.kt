package com.syntxr.anohikari2.data.repository

import com.syntxr.anohikari2.data.source.local.qoran.QuranDao
import com.syntxr.anohikari2.domain.model.Jozz
import com.syntxr.anohikari2.domain.model.Qoran
import com.syntxr.anohikari2.domain.model.Sora
import com.syntxr.anohikari2.domain.repository.QoranRepository
import kotlinx.coroutines.flow.Flow

class QoranRepositoryImpl(
    private val dao: QuranDao
) : QoranRepository {
    override fun getSora(): Flow<List<Sora>> {
        return dao.getSora()
    }

    override fun getJozz(): Flow<List<Jozz>> {
        return dao.getJozz()
    }

    override fun getSoraAya(soraNo: Int): Flow<List<Qoran>> {
        return dao.getSoraAya(soraNo)
    }

    override fun getJozzAya(jozzNo: Int): Flow<List<Qoran>> {
        return dao.getJozzAya(jozzNo)
    }
}