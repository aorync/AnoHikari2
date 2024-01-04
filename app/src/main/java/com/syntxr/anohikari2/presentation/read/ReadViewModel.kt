package com.syntxr.anohikari2.presentation.read

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syntxr.anohikari2.domain.model.Qoran
import com.syntxr.anohikari2.domain.usecase.AppUseCase
import com.syntxr.anohikari2.presentation.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ReadViewModel @Inject constructor(
    private val useCase: AppUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val qoranUseCase = useCase.qoranUseCase

    private val soraNumber = savedStateHandle.navArgs<ReadScreenNavArgs>().soraNumber ?: 1
    private val jozzNumber = savedStateHandle.navArgs<ReadScreenNavArgs>().jozzNumber  ?: 1
    val indexType = savedStateHandle.navArgs<ReadScreenNavArgs>().indexType
    val scrollPosition = savedStateHandle.navArgs<ReadScreenNavArgs>().scrollPosition ?: 1

    private val _state = mutableStateOf(ReadAyaState())
    val state : State<ReadAyaState> = _state

    private var getAyaJob: Job? = null

    init {
        getAyaQoran(indexType)
    }

    private fun getAyaQoran(indexType: Int){
        getAyaJob?.cancel()
        when(indexType){
            AYA_BY_SORA -> {
                getAyaJob = qoranUseCase.getSoraAya(soraNumber)
                    .onEach { ayas ->
                        _state.value = _state.value.copy(
                            ayas = ayas,
                            title = ayas.first().soraEn
                        )
                    }.launchIn(viewModelScope)
            }
            AYA_BY_JOZZ -> {
                getAyaJob = qoranUseCase.getJozzAya(jozzNumber)
                    .onEach { ayas ->
                        _state.value = _state.value.copy(
                            ayas = ayas,
                            title = "Juz ${ayas.first().jozz}"
                        )
                    }.launchIn(viewModelScope)
            }
            AYA_BY_JOZZ_SORA -> {
                getAyaJob = qoranUseCase.getSoraAya(soraNumber)
                    .onEach { ayas ->
                        _state.value = _state.value.copy(
                            ayas = ayas,
                            title = ayas.first().soraEn
                        )
                    }.launchIn(viewModelScope)
            }
        }
    }
}

data class ReadAyaState(
    val ayas: List<Qoran>? = emptyList(),
    val title: String? = ""
)