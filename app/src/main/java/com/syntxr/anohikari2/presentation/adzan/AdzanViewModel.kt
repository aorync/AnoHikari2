package com.syntxr.anohikari2.presentation.adzan

import android.content.Context
import android.location.Geocoder
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syntxr.anohikari2.data.kotpref.UserPreferences
import com.syntxr.anohikari2.domain.model.Adzan
import com.syntxr.anohikari2.domain.usecase.AppUseCase
import com.syntxr.anohikari2.service.location.LocationClient
import com.syntxr.anohikari2.service.location.LocationClientTracker
import com.syntxr.anohikari2.utils.AppGlobalState
import com.syntxr.anohikari2.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AdzanViewModel @Inject constructor(
    appUseCase: AppUseCase,
    private val locationClient: LocationClient,
) : ViewModel() {

    private val adzanUseCase = appUseCase.adzanUseCase

    private val _state = MutableStateFlow(AdzanState())
    val state = _state.asStateFlow()

    private val _uiEvent = MutableStateFlow<AdzanUiEvent>(AdzanUiEvent.Idle)
    val uiEvent = _uiEvent.asStateFlow()

    private val _currentLocation = mutableStateOf("Oopss, can't get location")
    val currentLocation = _currentLocation.value

    private val _locality = mutableStateOf("Unknown")
    val locality = _locality.value

    private var adzanJob: Job? = null

    private val errorLocation =
        if (AppGlobalState.currentLanguage == UserPreferences.Language.ID.tag)
            "Error While Getting Location"
        else
            "Error Ketika Mendapat Lokasi"

    private val errorGPS = if (AppGlobalState.currentLanguage == UserPreferences.Language.ID.tag)
        "Error No GPS"
    else
        "Error Tidak Ada GPS"

    private val errorPermission =
        if (AppGlobalState.currentLanguage == UserPreferences.Language.ID.tag)
            "Error Missing Permission"
        else
            "Error Tidak Ada Izin"


    fun getLocation(context: Context) {
        viewModelScope.launch {
            _uiEvent.emit(AdzanUiEvent.Idle)
            locationClient.requestLocationUpdate()
                .onEach { tracker ->
                    when (tracker) {
                        is LocationClientTracker.Error -> {
                            _uiEvent.emit(AdzanUiEvent.ShowErrorMessage(errorLocation))
                        }

                        is LocationClientTracker.MissingPermission -> {
                            _uiEvent.emit(AdzanUiEvent.ShowErrorMessage(errorPermission))
                        }

                        is LocationClientTracker.NoGps -> {
                            _uiEvent.emit(AdzanUiEvent.ShowErrorMessage(errorGPS))
                        }

                        is LocationClientTracker.Success -> {
                            val latitude = tracker.location?.latitude
                            val longitude = tracker.location?.longitude
                            if (latitude == null || longitude == null) {
                                _uiEvent.emit(AdzanUiEvent.ShowErrorMessage(errorLocation))
                                return@onEach
                            }

                            getCurrentLocal(context, latitude, longitude)
                            onGetLocalCache(latitude, longitude)
                            cancel()
                        }
                    }
                }.launchIn(this)
        }
    }

    private fun onGetLocalCache(latitude: Double, longitude: Double) {
        adzanJob?.cancel()
        adzanJob = CoroutineScope(Dispatchers.IO).launch {
            delay(500L)
            adzanUseCase.getAdzans(latitude, longitude)
                .onEach { resource ->
                    when(resource){
                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                adzans = resource.data ?: emptyList(),
                                isLoading = false
                            )
                            _uiEvent.emit(AdzanUiEvent.ShowErrorMessage(resource.message ?: ""))
                        }
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(
                                adzans = resource.data ?: emptyList(),
                                isLoading = true
                            )
                        }
                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                adzans = resource.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

    private fun getCurrentLocal(context: Context, latitude: Double?, longitude: Double?) {
        val geocoder = Geocoder(
            context,
            Locale.getDefault()
        )

        if (latitude != null && longitude != null) {
            @Suppress("DEPRECATION") val addressess = geocoder.getFromLocation(latitude, longitude, 1)
            if (!addressess.isNullOrEmpty()) {
                val address = addressess.first()
                _locality.value = address.locality
                _currentLocation.value =
                    "${address.locality}, ${address.subLocality}, ${address.subAdminArea}, ${address.countryName}"
            }
        }
    }

}

data class AdzanState(
    val adzans: List<Adzan> = emptyList(),
    val isLoading: Boolean = false,
)

sealed class AdzanUiEvent {
    data object Idle : AdzanUiEvent()
    data class ShowErrorMessage(val message: String) : AdzanUiEvent()
}

