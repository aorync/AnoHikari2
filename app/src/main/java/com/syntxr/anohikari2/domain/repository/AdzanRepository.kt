package com.syntxr.anohikari2.domain.repository

import com.syntxr.anohikari2.domain.model.Adzan
import com.syntxr.anohikari2.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AdzanRepository {

    fun getAdzan(
        fetchFromRemote: Boolean,
        latitude: String,
        longitude: String
    ): Flow<Resource<List<Adzan>>>
}