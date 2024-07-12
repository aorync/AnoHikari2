package com.syntxr.anohikari3.service.location

import android.location.Location

sealed class LocationClientTracker <T>{
    class NoGps<Nothing> : LocationClientTracker<Nothing>()
    class MissingPermission<Nothing> : LocationClientTracker<Nothing>()
    data class Success<T>(val location: Location?) : LocationClientTracker <T>()
    class Error<T> : LocationClientTracker<T>()
}