package com.syntxr.anohikari2.data.source.local.adzan.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.syntxr.anohikari2.data.source.local.adzan.entity.AdzanEntity

@Dao
interface AdzanDao {
    @Upsert
    suspend fun upsertAll (adzans: List<AdzanEntity>)

    @Query("SELECT * FROM adzan")
    fun getDataCache() : List<AdzanEntity>

    @Query("DELETE FROM adzan")
    suspend fun clear()
}