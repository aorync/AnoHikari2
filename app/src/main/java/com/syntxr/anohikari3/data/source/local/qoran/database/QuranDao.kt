package com.syntxr.anohikari3.data.source.local.qoran.database

import androidx.room.Dao
import androidx.room.Query
import com.syntxr.anohikari3.data.source.local.qoran.entity.Jozz
import com.syntxr.anohikari3.data.source.local.qoran.entity.Qoran
import com.syntxr.anohikari3.data.source.local.qoran.entity.Sora
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

    @Query("SELECT * FROM quran WHERE translation_id LIKE '%'||:search||'%' OR aya_text_emlaey LIKE :search OR aya_text LIKE '%'||:search||'%' ")
    fun searchAyaId(search: String) : Flow<List<Qoran>>

    @Query("SELECT * FROM quran WHERE translation_en LIKE '%'||:search||'%' OR aya_text_emlaey LIKE :search OR aya_text LIKE '%'||:search||'%' ")
    fun searchAyaEn(search: String) : Flow<List<Qoran>>

    @Query("SELECT * FROM quran WHERE sora_name_emlaey LIKE '%'||:search||'%' OR sora_name_ar LIKE '%'||:search||'%' ")
    fun searchSora(search: String) : Flow<List<Qoran>>

}