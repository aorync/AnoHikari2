package com.syntxr.anohikari2.domain.repository

import com.syntxr.anohikari2.data.source.local.qoran.entity.Jozz
import com.syntxr.anohikari2.data.source.local.qoran.entity.Qoran
import com.syntxr.anohikari2.data.source.local.qoran.entity.Sora
import kotlinx.coroutines.flow.Flow

interface QoranRepository {

    fun getSora(): Flow<List<Sora>>
    fun getJozz(): Flow<List<Jozz>>
    fun getSoraAya(soraNo: Int): Flow<List<Qoran>>
    fun getJozzAya(jozzNo: Int): Flow<List<Qoran>>

    fun searchAyaId(search: String) : Flow<List<Qoran>>

    fun searchAyaEn(search: String) : Flow<List<Qoran>>

    fun searchSora(search: String) : Flow<List<Qoran>>
}