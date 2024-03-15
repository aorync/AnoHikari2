package com.syntxr.anohikari2.presentation.read

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.syntxr.anohikari2.presentation.AnoHikariSharedViewModel
import com.syntxr.anohikari2.data.kotpref.LastReadPreferences
import com.syntxr.anohikari2.data.kotpref.UserPreferences
import com.syntxr.anohikari2.data.source.local.bookmark.entity.Bookmark
import com.syntxr.anohikari2.presentation.read.component.AyaReadItem
import com.syntxr.anohikari2.presentation.read.component.AyaSoraCard
import com.syntxr.anohikari2.presentation.read.component.FootNotesBottomSheet
import com.syntxr.anohikari2.presentation.read.component.ReadAudioControl
import com.syntxr.anohikari2.presentation.read.component.ReadPlayTopBar
import com.syntxr.anohikari2.utils.AppGlobalState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class ReadScreenNavArgs(
    val soraNumber: Int?,
    val jozzNumber: Int?,
    val indexType: Int,
    val scrollPosition: Int?,
)

@OptIn(ExperimentalMaterial3Api::class)
@Destination(
    navArgsDelegate = ReadScreenNavArgs::class
)
@Composable
fun ReadScreen(
    viewModel: ReadViewModel = hiltViewModel(),
    sharedViewModel: AnoHikariSharedViewModel,
    navigator: DestinationsNavigator,
) {
    AppGlobalState.drawerGesture = false
    val state = viewModel.state.value
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val lazyColumnState = rememberLazyListState()
    val totalAyas = remember {
        sharedViewModel.getAyahs()
    }
    val bottomSheetState = rememberModalBottomSheetState()
    var isBottomSheetShowed by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val footNotesState = remember { mutableStateOf("") }

    val context = LocalContext.current
    val isPlaying by viewModel.isAudioPlay.collectAsState()
    val currentAyaPlayId by viewModel.currentAyaPlayId.collectAsState()
    val currentAyaPlayName by viewModel.currentPlayAyaName.collectAsState()
    val playType = viewModel.playType
    val playMode = viewModel.playMode


    LaunchedEffect(Unit) {
        delay(300)
        lazyColumnState.scrollToItem(viewModel.scrollPosition, viewModel.scrollPosition)
    }

    LaunchedEffect(key1 = true) {
        viewModel.findBookmark()
    }

    viewModel.uiEvent.collectAsState().let { event ->
        when (val uiEvent = event.value) {
            is ReadUiEvent.AyaCopied -> Toast.makeText(context, uiEvent.message, Toast.LENGTH_SHORT)
                .show()

            is ReadUiEvent.AyaShared -> Toast.makeText(context, uiEvent.message, Toast.LENGTH_SHORT)
                .show()

            is ReadUiEvent.BookmarkAdded -> Toast.makeText(
                context,
                uiEvent.message,
                Toast.LENGTH_SHORT
            ).show()

            is ReadUiEvent.BookmarkDeleted -> Toast.makeText(
                context,
                uiEvent.message,
                Toast.LENGTH_SHORT
            ).show()

            is ReadUiEvent.PlayerError -> Toast.makeText(
                context,
                uiEvent.message,
                Toast.LENGTH_SHORT
            ).show()

            else -> {}
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ReadPlayTopBar(
                lazyListState = lazyColumnState,
                title = state.title,
                onPlayAll = {
                    state.ayas?.let {
                        viewModel.onEvent(ReadEvent.PlayAllAudio(it, context))
                    }
                },
                backToHome = {
                    navigator.navigateUp()
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            if (playType.value == PlayType.SINGLE || playType.value == PlayType.ALL) {
                ReadAudioControl(
                    playType = playType.value,
                    playMode = playMode.value,
                    isPlaying = isPlaying,
                    currentAyaPlayName = currentAyaPlayName,
                    onNext = { viewModel.onPlayEvent(PlayEvent.Next) },
                    onPrevious = { viewModel.onPlayEvent(PlayEvent.Previous) },
                    onPlayPause = { viewModel.onPlayEvent(PlayEvent.PlayPause) },
                    onStop = { viewModel.onPlayEvent(PlayEvent.Stop) },
                    onChangePlayMode = { viewModel.onPlayEvent(PlayEvent.PlayModeChange) }
                )
            }
        }
    ) { values ->
        Column(
            Modifier.padding(values)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyColumnState,
                content = {
                    items(state.ayas?.size ?: 0) { index ->

                        if (!state.ayas.isNullOrEmpty()) {
                            val qoran = state.ayas[index]

                            val bookmark = Bookmark(
                                id = qoran.id,
                                soraEn = qoran.soraEn,
                                ayaText = qoran.ayaText,
                                ayaNo = qoran.ayaNo,
                                soraNo = qoran.soraNo,
                                jozzNo = qoran.jozz,
                                scrollPosition = index,
                                indexType = viewModel.indexType
                            )
                            if (qoran.ayaNo == 1) {
                                AyaSoraCard(
                                    soraArName = qoran.soraAr ?: "",
                                    soraEnName = qoran.soraEn ?: "",
                                    soraIdName = qoran.soraId ?: "",
                                    soraDescend = qoran.soraPlace ?: "",
                                    ayas = totalAyas?.get(qoran.soraNo?.minus(1) ?: 0) ?: 0
                                )
                            }

                            AyaReadItem(
                                id = qoran.id,
                                bookmarks = viewModel.isBookmark(),
                                ayaText = qoran.ayaText ?: "",
                                translation = if (AppGlobalState.currentLanguage == UserPreferences.Language.ID.tag)
                                    qoran.translationId ?: ""
                                else
                                    qoran.translationEn ?: "",
                                footnotes = if (AppGlobalState.currentLanguage == UserPreferences.Language.ID.tag)
                                    qoran.footnotesId ?: ""
                                else
                                    qoran.footnotesEn ?: "",
                                soraNo = qoran.soraNo ?: 0,
                                ayaNo = qoran.ayaNo ?: 0,
                                onInsertBookmarkClick = {
                                    viewModel.onEvent(
                                        ReadEvent.InsertBookmark(bookmark)
                                    )
                                },
                                onDeleteBookmarkClick = {
                                    viewModel.onEvent(
                                        ReadEvent.DeleteBookmark(bookmark)
                                    )
                                },
                                onCopyClick = {
                                    viewModel.onEvent(
                                        ReadEvent.CopyAyaContent(
                                            context,
                                            qoran.ayaNo ?: 0,
                                            qoran.soraEn ?: "",
                                            qoran.ayaText ?: "",
                                            qoran.translationId ?: ""
                                        )
                                    )
                                },
                                onShareClick = {
                                    viewModel.onEvent(
                                        ReadEvent.ShareAyaContent(
                                            context,
                                            qoran.ayaNo ?: 0,
                                            qoran.soraEn ?: "",
                                            qoran.ayaText ?: "",
                                            qoran.translationId ?: ""
                                        )
                                    )
                                },
                                onPlayClick = {
                                    viewModel.onEvent(
                                        ReadEvent.PlayAyaAudio(
                                            id = qoran.id,
                                            soraEn = qoran.soraEn ?: "",
                                            ayaNo = qoran.ayaNo ?: 0,
                                            soraNo = qoran.soraNo ?: 0,
                                            context = context
                                        )
                                    )
                                },
                                currentPlayAya = currentAyaPlayId,
                                onTranslateClick = { footnote ->
                                    footNotesState.value = footnote
                                    scope.launch {
                                        bottomSheetState.show()
                                        isBottomSheetShowed = true
                                    }
                                }
                            )
                            with(LastReadPreferences) {
                                soraName = qoran.soraEn ?: ""
                                soraNumber = qoran.soraNo ?: 1
                                jozzNumber = qoran.jozz ?: 1
                                ayaNumber = qoran.ayaNo ?: 1
                                indexType = viewModel.indexType
                                scrollPosition = index
                            }
                        }
                    }
                }
            )
        }

        if (isBottomSheetShowed)
            ModalBottomSheet(
                sheetState = bottomSheetState,
                onDismissRequest = {
                    scope.launch {
                        bottomSheetState.hide()
                        isBottomSheetShowed = false
                    }
                },
                content = {
                    FootNotesBottomSheet(
                        footNotesContent = footNotesState.value,
                        hideBottomSheet = {
                            scope.launch {
                                bottomSheetState.hide()
                                isBottomSheetShowed = false
                            }
                        }
                    )
                }
            )
    }
}

const val AYA_BY_SORA = 0
const val AYA_BY_JOZZ = 1
const val AYA_BY_JOZZ_SORA = 2