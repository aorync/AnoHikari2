package com.syntxr.anohikari2.data.repository

import com.syntxr.anohikari2.data.source.local.adzan.database.AdzanDao
import com.syntxr.anohikari2.data.source.remote.service.AdzanApi
import com.syntxr.anohikari2.domain.model.Adzan
import com.syntxr.anohikari2.domain.repository.AdzanRepository
import com.syntxr.anohikari2.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AdzanRepositoryImpl(
    private val api: AdzanApi,
    private val dao: AdzanDao,
) : AdzanRepository {
    override fun getAdzan(latitude: Double, longitude: Double): Flow<Resource<List<Adzan>>> = flow {
        emit(Resource.Loading())

        val cachedData = dao.getDataCache().map { it.toAdzan() }
        emit(Resource.Success(data = cachedData))

        try {
            val remoteAdzans = api.getAdzans(latitude.toString(), longitude.toString())
            dao.clear()
            dao.upsertAll(remoteAdzans.map { it.toAdzanEntity() })
        } catch (e: Exception) {
            emit(Resource.Error(data = cachedData, message = e.message ?: ""))
        }
    }
}