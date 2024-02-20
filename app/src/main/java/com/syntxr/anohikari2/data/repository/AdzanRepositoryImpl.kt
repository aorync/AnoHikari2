package com.syntxr.anohikari2.data.repository

import com.syntxr.anohikari2.data.source.local.adzan.database.AdzanDatabase
import com.syntxr.anohikari2.data.source.remote.service.AdzanApi

class AdzanRepositoryImpl(
    val api: AdzanApi,
    val db: AdzanDatabase,
) {
}