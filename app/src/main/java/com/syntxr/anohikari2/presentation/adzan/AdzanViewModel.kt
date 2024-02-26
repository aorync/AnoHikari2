package com.syntxr.anohikari2.presentation.adzan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syntxr.anohikari2.domain.model.Adzan
import com.syntxr.anohikari2.domain.usecase.AppUseCase
import com.syntxr.anohikari2.service.location.LocationClient
import com.syntxr.anohikari2.service.location.LocationClientTracker
import com.syntxr.anohikari2.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdzanViewModel @Inject constructor(
    appUseCase: AppUseCase,
    private val locationClient: LocationClient,
) : ViewModel() {

    private val useCase = appUseCase.adzanUseCase

    private val _state = MutableStateFlow(AdzanState())
    val state = _state.asStateFlow()

    private val _uiState = MutableStateFlow<AdzanUIState?>(null)
    val uiState = _uiState.asStateFlow()

    private val _currentLocation = MutableStateFlow(CurrentLocation(0.0, 0.0))
    val currentLocation = _currentLocation.asStateFlow()

    private var adzanJob: Job? = null
    fun getUpdateLocation() {
        adzanJob?.cancel()
        viewModelScope.launch {
            locationClient.requestLocationUpdate()
                .onEach { tracker ->
                    when(tracker){
                        is LocationClientTracker.Error -> {
                            _uiState.emit(AdzanUIState.Error(ErrorType.ELSE))
                        }
                        is LocationClientTracker.MissingPermission -> {
                            _uiState.emit(AdzanUIState.Error(ErrorType.PERMISSION_ERROR))
                        }
                        is LocationClientTracker.NoGps -> {
                            _uiState.emit(AdzanUIState.Error(ErrorType.NO_GPS))
                        }
                        is LocationClientTracker.Success -> {
                            val latitude = tracker.location?.latitude
                            val longitude = tracker.location?.longitude
                            if (latitude == null || longitude == null){
                                _uiState.emit(AdzanUIState.Error(ErrorType.ELSE))
                                return@onEach
                            }
                            _currentLocation.emit(CurrentLocation(latitude, longitude))
                            useCase.getAdzans(
                                latitude.toString(),
                                longitude.toString()
                            )
                                .onEach { result ->
                                    when(result){
                                        is Resource.Error -> {
                                            _uiState.emit(AdzanUIState.Error(ErrorType.ELSE))
                                            _state.value = _state.value.copy(
                                                adzans = result.data ?: emptyList(),
                                                isLoading = false
                                            )
                                        }
                                        is Resource.Loading -> {
                                            _state.value = _state.value.copy(
                                                adzans = result.data ?: emptyList(),
                                                isLoading = true
                                            )
                                        }
                                        is Resource.Success -> {
                                            _uiState.emit(AdzanUIState.Success)
                                            _state.value = _state.value.copy(
                                                adzans = result.data ?: emptyList(),
                                                isLoading = false
                                            )
                                        }
                                    }
                                }
                            cancel()
                        }
                    }
                }

        }
    }

    data class CurrentLocation(
        val longitude: Double,
        val latitude: Double,
    )
}

data class AdzanState(
    val adzans: List<Adzan> = emptyList(),
    val isLoading: Boolean = false,
)

sealed class AdzanUIState {
    data object Success : AdzanUIState()
    data class Error(val errorType : ErrorType) : AdzanUIState()
}

enum class ErrorType {
    NO_GPS,
    PERMISSION_ERROR,
    ELSE
}
