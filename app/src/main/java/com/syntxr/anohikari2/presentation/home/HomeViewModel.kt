package com.syntxr.anohikari2.presentation.home

import android.os.Build
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syntxr.anohikari2.domain.model.Bookmark
import com.syntxr.anohikari2.domain.model.Jozz
import com.syntxr.anohikari2.domain.model.Sora
import com.syntxr.anohikari2.domain.usecase.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalTime
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    useCase: AppUseCase,
) : ViewModel() {

    private val qoranUseCase = useCase.qoranUseCase
    private val bookmarkUseCase = useCase.bookmarkUseCase

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val localTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalTime.now().hour
    } else {
        Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    }

    private val _hourTime = mutableStateOf(Time())
    val hourTime: State<Time> = _hourTime

    private val time = MutableLiveData<Time>()

    private var getSurahJob: Job? = null
    private var getJozzJob: Job? = null
    private var getBookmarkJob: Job? = null

    init {
        setCurrentTime(localTime)
        getQoran(true)
    }

    private fun getQoran(isDefault: Boolean) {
        getSurahJob?.cancel()
        getJozzJob?.cancel()
        getBookmarkJob?.cancel()

        getSurahJob = qoranUseCase.getSora(isDefault)
            .onEach { soras ->
                _state.value = _state.value.copy(
                    soras = soras
                )
            }.launchIn(viewModelScope)

        getJozzJob = qoranUseCase.getJozz(isDefault)
            .onEach { jozzes ->
                _state.value = _state.value.copy(
                    jozzes = jozzes
                )
            }.launchIn(viewModelScope)

        getBookmarkJob = bookmarkUseCase.getBookmark(isDefault)
            .onEach { bookmarks ->
                _state.value = _state.value.copy(
                    bookmarks = bookmarks
                )
            }.launchIn(viewModelScope)
    }

    fun getSort(isDefault: Boolean) {
        getQoran(isDefault)
    }

    private fun setCurrentTime(localTime: Int){
        _hourTime.value = _hourTime.value.copy(
            midnight = localTime in 23..24 || localTime in 0..3,
            dusk = localTime in 4..7,
            day = localTime in 8..10,
            noon = localTime in 11..14,
            afternoon = localTime in 15..17,
            dawn = localTime in 18..19,
            night = localTime in 20..22
        )
        time.value = Time(
            midnight = localTime in 23..24 || localTime in 0..3,
            dusk = localTime in 4..7,
            day = localTime in 8..10,
            noon = localTime in 11..14,
            afternoon = localTime in 15..17,
            dawn = localTime in 18..19,
            night = localTime in 20..22
        )
    }

    fun getTime() = time.value ?: Time()
}

data class HomeState(
    val soras: List<Sora>? = emptyList(),
    val jozzes: List<Jozz>? = emptyList(),
    val bookmarks: List<Bookmark>? = emptyList()
)

data class Time(
    val midnight: Boolean = false,
    val dusk: Boolean = false,
    val day: Boolean = false,
    val noon: Boolean = false,
    val afternoon: Boolean = false,
    val dawn: Boolean = false,
    val night: Boolean = false,
)