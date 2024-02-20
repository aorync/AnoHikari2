package com.syntxr.anohikari2.service.location

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun requestLocationUpdate(): Flow<LocationClientTracker<Location?>>
}