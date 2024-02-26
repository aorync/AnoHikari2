package com.syntxr.anohikari2.data.repository

import com.syntxr.anohikari2.data.source.local.adzan.database.AdzanDao
import com.syntxr.anohikari2.data.source.local.adzan.database.AdzanDatabase
import com.syntxr.anohikari2.data.source.remote.service.AdzanApi
import com.syntxr.anohikari2.domain.model.Adzan
import com.syntxr.anohikari2.domain.repository.AdzanRepository
import com.syntxr.anohikari2.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class AdzanRepositoryImpl(
    val api: AdzanApi,
    val dao: AdzanDao,
) : AdzanRepository {
    override fun getAdzan(latitude: String, longitude: String): Flow<Resource<List<Adzan>>> = flow {

        emit(Resource.Loading())

        val adzans = dao.getDataCache().map { it.toAdzan() }
        emit(Resource.Loading(data = adzans))

        try {
            val remoteAdzans = api.getAdzans(latitude, longitude)
            dao.clear()
            dao.upsertAll(remoteAdzans.map { it.toAdzanEntity() })
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    data = adzans
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error(
                data = adzans
            ))
        }

        val newAdzans = dao.getDataCache().map { it.toAdzan() }
        emit(Resource.Success(newAdzans))
    }
}