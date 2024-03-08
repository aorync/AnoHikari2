package com.syntxr.anohikari2.presentation.adzan

import android.content.Context
import android.util.Log
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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
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

    private val _currentLocation: MutableStateFlow<CurrentLocation> = MutableStateFlow(CurrentLocation(0.0, 0.0))
    val currentLocation: StateFlow<CurrentLocation> = _currentLocation.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading  = _isLoading.asStateFlow()

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
                            onGetLocalCache(0.0, 0.0)
                        }

                        is LocationClientTracker.MissingPermission -> {
                            _uiEvent.emit(AdzanUiEvent.ShowErrorMessage(errorPermission))
                            onGetLocalCache(0.0, 0.0)
                        }

                        is LocationClientTracker.NoGps -> {
                            _uiEvent.emit(AdzanUiEvent.ShowErrorMessage(errorGPS))
                            onGetLocalCache(0.0, 0.0)
                        }

                        is LocationClientTracker.Success -> {
                            val latitude = tracker.location?.latitude
                            val longitude = tracker.location?.longitude
                            if (latitude == null || longitude == null) {
                                _uiEvent.emit(AdzanUiEvent.ShowErrorMessage(errorLocation))
                                return@onEach
                            }
                            _currentLocation.emit(CurrentLocation(longitude, latitude))

                            _isLoading.emit(true)
                            onGetLocalCache(latitude, longitude)
                            cancel()
                        }

                        else -> {

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
                            _isLoading.emit(false)
                            _state.value = _state.value.copy(
                                adzans = resource.data,
                            )
                            _uiEvent.emit(AdzanUiEvent.GetData)
                            _uiEvent.emit(AdzanUiEvent.ShowErrorMessage(resource.message ?: ""))
                            Log.d("GATET", "onGetLocalCache: ${resource.message}")
                            Log.d("GATET2", "onGetLocalCache: ${resource.data}")
                        }
                        is Resource.Success -> {
                            _isLoading.emit(false)
                            _state.value = _state.value.copy(
                                adzans = resource.data,
                            )
                            _uiEvent.emit(AdzanUiEvent.GetData)
                        }

                        else -> {}
                    }
                }.launchIn(this)
        }
    }


}

data class CurrentLocation(
    val longitude: Double,
    val latitude: Double
)

data class AdzanState(
    val adzans: Adzan? = null,
)

sealed class AdzanUiEvent {
    data object Idle : AdzanUiEvent()
    data object GetData : AdzanUiEvent()
    data class ShowErrorMessage(val message: String) : AdzanUiEvent()
}

