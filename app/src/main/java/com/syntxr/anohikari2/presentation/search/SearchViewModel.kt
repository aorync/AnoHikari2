package com.syntxr.anohikari2.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syntxr.anohikari2.data.kotpref.UserPreferences
import com.syntxr.anohikari2.data.source.local.qoran.entity.Qoran
import com.syntxr.anohikari2.domain.usecase.AppUseCase
import com.syntxr.anohikari2.utils.AppGlobalState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    appUseCase: AppUseCase,
) : ViewModel() {

    private val quranUseCase = appUseCase.qoranUseCase

    private val _uiState = MutableStateFlow<SearchUiState?>(null)
    val uiState = _uiState.asStateFlow()

    private val _ayaMatched = MutableStateFlow(emptyList<Qoran>())
    val ayaMatched = _ayaMatched.asStateFlow()
    fun onEvent(event: SearchEvent) {
        when (event) {
            SearchEvent.Clear -> {
                viewModelScope.launch {
                    _ayaMatched.emit(emptyList())
                    _uiState.emit(null)
                }
            }

            is SearchEvent.OnSearch -> {
                viewModelScope.launch {
                    try {
                        _uiState.emit(SearchUiState.Loading)
                        val data =
                            if (AppGlobalState.currentLanguage == UserPreferences.Language.ID.tag)
                                quranUseCase.searchAyaId(event.query).stateIn(this).value
                            else quranUseCase.searchAyaEn(event.query).stateIn(this).value
                        _ayaMatched.emit(data)
                        _uiState.emit(SearchUiState.Success)
                    } catch (e: Exception) {
                        _uiState.emit(SearchUiState.Error(e.message ?: ""))
                    }
                }
            }
        }
    }
}

sealed class SearchEvent {
    data class OnSearch(val query: String) : SearchEvent()
    data object Clear : SearchEvent()
}

sealed class SearchUiState {
    data object Loading : SearchUiState()
    data object Success : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}