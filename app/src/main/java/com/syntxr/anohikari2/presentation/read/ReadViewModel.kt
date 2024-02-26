package com.syntxr.anohikari2.presentation.read

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syntxr.anohikari2.R
import com.syntxr.anohikari2.data.source.local.bookmark.entity.Bookmark
import com.syntxr.anohikari2.data.source.local.qoran.entity.Qoran
import com.syntxr.anohikari2.domain.usecase.AppUseCase
import com.syntxr.anohikari2.presentation.navArgs
import com.syntxr.anohikari2.utils.AppGlobalActions
import com.syntxr.anohikari2.utils.Converters
import com.syntxr.anohikari2.utils.IntToUrlThreeDigits
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import snow.player.PlayMode
import snow.player.PlayerClient
import snow.player.audio.MusicItem
import snow.player.playlist.Playlist
import javax.inject.Inject

@HiltViewModel
class ReadViewModel @Inject constructor(
    useCase: AppUseCase,
    savedStateHandle: SavedStateHandle,
    private val playerClient: PlayerClient,
) : ViewModel() {

    private val qoranUseCase = useCase.qoranUseCase
    private val bookmarkUseCase = useCase.bookmarkUseCase

    private val soraNumber = savedStateHandle.navArgs<ReadScreenNavArgs>().soraNumber ?: 1
    private val jozzNumber = savedStateHandle.navArgs<ReadScreenNavArgs>().jozzNumber ?: 1
    val indexType = savedStateHandle.navArgs<ReadScreenNavArgs>().indexType
    val scrollPosition = savedStateHandle.navArgs<ReadScreenNavArgs>().scrollPosition ?: 0

    private val bookmarks = mutableListOf<Int>()
    private val checkBookmark = MutableLiveData<List<Int>>()

    private val _isAudioPlay = MutableStateFlow(playerClient.isPlaying)
    val isAudioPlay = _isAudioPlay.asStateFlow()

    private val _currentAyaPlayId = MutableStateFlow(0)
    val currentAyaPlayId = _currentAyaPlayId.asStateFlow()

    private val _currentAyaPlayName = MutableStateFlow("")
    val currentPlayAyaName = _currentAyaPlayName.asStateFlow()

    private val _uiEvent = MutableStateFlow<ReadUiEvent?>(null)
    val uiEvent = _uiEvent.asStateFlow()

    val playMode = mutableStateOf(PlayMode.SINGLE_ONCE)
    val playType = mutableStateOf(PlayType.NONE)

    private val _state = mutableStateOf(ReadAyaState())
    val state: State<ReadAyaState> = _state

    private var getAyaJob: Job? = null

    init {
        getAyaQoran(indexType)
    }

    private fun getAyaQoran(indexType: Int) {
        getAyaJob?.cancel()
        when (indexType) {
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

    fun onEvent(event: ReadEvent) {
        when (event) {
            is ReadEvent.CopyAyaContent -> {
                AppGlobalActions.copyAction(
                    event.context, event.ayaNo, event.soraEn, event.ayaText, event.translation
                )
                viewModelScope.launch { _uiEvent.emit(ReadUiEvent.AyaCopied(R.string.txt_aya_copied)) }
            }

            is ReadEvent.DeleteBookmark -> {
                viewModelScope.launch {
                    bookmarks.remove(event.bookmark.id)
                    bookmarkUseCase.deleteBookmark(event.bookmark)
                    _uiEvent.emit(ReadUiEvent.BookmarkDeleted(R.string.txt_bookmark_deleted))
                }
            }

            is ReadEvent.InsertBookmark -> {
                viewModelScope.launch {
                    event.bookmark.id?.let { bookmarks.add(it) }
                    bookmarkUseCase.insertBookmark(event.bookmark)
                    _uiEvent.emit(ReadUiEvent.BookmarkAdded(R.string.txt_bookmark_added))
                }
            }

            is ReadEvent.PlayAyaAudio -> {
                playerClient.stop()
                val musicItem = Converters.createMusicItem(
                    title = "${event.soraEn} - ${event.ayaNo}",
                    ayaNo = IntToUrlThreeDigits(event.ayaNo),
                    soraNo = IntToUrlThreeDigits(event.soraNo)
                )
                val playlist = Playlist.Builder().append(musicItem).build()
                playerClient.connect {
                    playerClient.setPlaylist(playlist, true)
                    playerClient.playMode = PlayMode.SINGLE_ONCE
                    playMode.value = PlayMode.SINGLE_ONCE
                    _currentAyaPlayId.value = event.id
                    _currentAyaPlayName.value = "${event.soraEn} - ${event.ayaNo}"
                    playType.value = PlayType.SINGLE
                }
                if (playerClient.isError){
                    viewModelScope.launch { _uiEvent.emit(ReadUiEvent.PlayerError(playerClient.errorMessage)) }
                }
            }

            is ReadEvent.ShareAyaContent -> {
                AppGlobalActions.shareAction(
                    event.context, event.ayaNo, event.soraEn, event.ayaText, event.translation
                )
                viewModelScope.launch { _uiEvent.emit(ReadUiEvent.AyaShared(R.string.txt_aya_shared)) }
            }

            is ReadEvent.PlayAllAudio -> {
                playerClient.stop()
                val musicItems = mutableListOf<MusicItem>()
                event.ayas.forEach { qoran ->
                    val musicItem = Converters.createMusicItem(
                        title = "${qoran.soraEn} - ${qoran.ayaNo}",
                        ayaNo = IntToUrlThreeDigits(qoran.ayaNo ?: return@forEach),
                        soraNo = IntToUrlThreeDigits(qoran.soraNo ?: return@forEach)
                    )
                    musicItems.add(musicItem)
                }
                val playlist = Playlist.Builder().appendAll(musicItems).build()
                playerClient.connect {
                    playerClient.setPlaylist(playlist, true)
                    playerClient.playMode = PlayMode.PLAYLIST_LOOP
                    playMode.value = PlayMode.PLAYLIST_LOOP
                    playType.value = PlayType.ALL
                    playerClient.addOnPlayingMusicItemChangeListener { _, position, _ ->
                        _currentAyaPlayId.value = event.ayas[position].id
                        _currentAyaPlayName.value =
                            "${event.ayas[position].soraEn} - ${event.ayas[position].ayaNo}"
                    }
                }
                if (playerClient.isError){
                    viewModelScope.launch { _uiEvent.emit(ReadUiEvent.PlayerError(playerClient.errorMessage)) }
                }
            }

            else -> {}
        }
    }

    fun onPlayEvent(event: PlayEvent) {
        when (event) {
            PlayEvent.PlayModeChange -> {
                when (playerClient.playMode) {
                    PlayMode.SINGLE_ONCE -> {
                        playerClient.playMode = PlayMode.PLAYLIST_LOOP
                        playMode.value = PlayMode.PLAYLIST_LOOP
                    }

                    PlayMode.PLAYLIST_LOOP -> {
                        playerClient.playMode = PlayMode.SINGLE_ONCE
                        playMode.value = PlayMode.SINGLE_ONCE
                    }

                    else -> {}
                }
            }

            PlayEvent.PlayPause -> {
                playerClient.playPause(); _isAudioPlay.value = !_isAudioPlay.value
            }

            PlayEvent.Next -> playerClient.skipToNext()
            PlayEvent.Previous -> playerClient.skipToPrevious()
            PlayEvent.Stop -> {
                playerClient.stop(); playType.value = PlayType.NONE; playerClient.shutdown()
            }

            else -> {}
        }
    }


    fun findBookmark() {
        viewModelScope.launch {
            val datas = bookmarkUseCase.getBookmark(true).stateIn(viewModelScope).value
            datas.forEach {
                it.id?.let { it1 -> bookmarks.add(it1) }
            }
            checkBookmark.value = bookmarks
        }
    }

    fun isBookmark() = checkBookmark.value

}

data class ReadAyaState(
    val ayas: List<Qoran>? = emptyList(),
    val title: String? = "",
)

sealed class ReadEvent {
    data class CopyAyaContent(
        val context: Context,
        val ayaNo: Int,
        val soraEn: String,
        val ayaText: String,
        val translation: String,
    ) : ReadEvent()

    data class ShareAyaContent(
        val context: Context,
        val ayaNo: Int,
        val soraEn: String,
        val ayaText: String,
        val translation: String,
    ) : ReadEvent()

    data class PlayAyaAudio(
        val id: Int,
        val soraEn: String,
        val ayaNo: Int,
        val soraNo: Int,
    ) : ReadEvent()

    data class PlayAllAudio(
        val ayas: List<Qoran>,
    ) : ReadEvent()

    data class InsertBookmark(val bookmark: Bookmark) : ReadEvent()
    data class DeleteBookmark(val bookmark: Bookmark) : ReadEvent()
}

sealed class ReadUiEvent {
    data class BookmarkAdded(val message: Int) : ReadUiEvent()
    data class BookmarkDeleted(val message: Int) : ReadUiEvent()
    data class AyaCopied(val message: Int) : ReadUiEvent()
    data class AyaShared(val message: Int) : ReadUiEvent()
    data class PlayerError(val message: String) : ReadUiEvent()
}

sealed class PlayEvent {
    data object PlayPause : PlayEvent()
    data object PlayModeChange : PlayEvent()
    data object Previous : PlayEvent()
    data object Next : PlayEvent()
    data object Stop : PlayEvent()

}

enum class PlayType {
    NONE,
    SINGLE,
    ALL
}