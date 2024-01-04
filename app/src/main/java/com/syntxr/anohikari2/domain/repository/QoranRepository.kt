package com.syntxr.anohikari2.domain.repository

import com.syntxr.anohikari2.domain.model.Jozz
import com.syntxr.anohikari2.domain.model.Qoran
import com.syntxr.anohikari2.domain.model.Sora
import kotlinx.coroutines.flow.Flow

interface QoranRepository {

    fun getSora(): Flow<List<Sora>>
    fun getJozz(): Flow<List<Jozz>>
    fun getSoraAya(soraNo: Int): Flow<List<Qoran>>
    fun getJozzAya(jozzNo: Int): Flow<List<Qoran>>

}