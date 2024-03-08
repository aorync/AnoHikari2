package com.syntxr.anohikari2.data.source.remote.service

import com.syntxr.anohikari2.data.source.remote.response.AdzanResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AdzanApi {
    @GET("day")
    suspend fun getAdzans(
        @Query("latitude") latitude : String,
        @Query("longitude") longitude : String
    ): AdzanResponse
}