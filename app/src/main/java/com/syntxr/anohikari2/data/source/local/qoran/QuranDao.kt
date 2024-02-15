package com.syntxr.anohikari2.data.source.local.qoran

import androidx.room.Dao
import androidx.room.Query
import com.syntxr.anohikari2.domain.model.Jozz
import com.syntxr.anohikari2.domain.model.Qoran
import com.syntxr.anohikari2.domain.model.Sora
import kotlinx.coroutines.flow.Flow

@Dao
interface QuranDao {

    @Query("SELECT * FROM sora")
    fun getSora(): Flow<List<Sora>>

    @Query("SELECT * FROM jozz")
    fun getJozz(): Flow<List<Jozz>>

    @Query("SELECT * FROM quran WHERE sora = :soraNo")
    fun getSoraAya(soraNo: Int): Flow<List<Qoran>>

    @Query("SELECT * FROM quran WHERE jozz = :jozzNo")
    fun getJozzAya(jozzNo: Int): Flow<List<Qoran>>
}