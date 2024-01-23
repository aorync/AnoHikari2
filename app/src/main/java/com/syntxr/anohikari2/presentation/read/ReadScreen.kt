package com.syntxr.anohikari2.presentation.read

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.syntxr.anohikari2.AnoHikariSharedViewModel
import com.syntxr.anohikari2.domain.model.Bookmark
import com.syntxr.anohikari2.presentation.read.component.AyaReadItem
import com.syntxr.anohikari2.presentation.read.component.AyaSoraCard
import com.syntxr.anohikari2.presentation.read.component.ReadTopBar
import kotlinx.coroutines.delay

data class ReadScreenNavArgs(
    val soraNumber: Int?,
    val jozzNumber: Int?,
    val indexType: Int,
    val scrollPosition: Int?,
)

@Destination(
    navArgsDelegate = ReadScreenNavArgs::class
)
@Composable
fun ReadScreen(
    viewModel: ReadViewModel = hiltViewModel(),
    sharedViewModel: AnoHikariSharedViewModel,
    navigator: DestinationsNavigator,
) {
    val state = viewModel.state.value
    val lazyColumnState = rememberLazyListState()
    val totalAyas = remember {
        sharedViewModel.getAyahs()
    }

    val context = LocalContext.current
    val isPlaying by viewModel.isAudioPlay.collectAsState()
    val currentAyaPlay by viewModel.currentAyaPlay.collectAsState()


    LaunchedEffect(Unit) {
        delay(300)
        lazyColumnState.scrollToItem(viewModel.scrollPosition, viewModel.scrollPosition)
    }

    LaunchedEffect(key1 = true) {
        viewModel.findBookmark()
    }

    Scaffold(
        topBar = {
            ReadTopBar(
                lazyListState = lazyColumnState,
                title = state.title,
                backToHome = {
                    navigator.navigateUp()
                }
            )
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
                                translation = qoran.translationId ?: "",
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
                                            soraNo = qoran.soraNo ?: 0
                                        )
                                    )
                                },
                                onPlayPauseClick = { viewModel.onPlayEvent(PlayEvent.PlayPause) },
                                isAudioPlay = isPlaying,
                                playMode = viewModel.playMode.value,
                                currentPlayAya = currentAyaPlay
                            )
                        }
                    }
                }
            )
        }
    }
}

const val AYA_BY_SORA = 0
const val AYA_BY_JOZZ = 1
const val AYA_BY_JOZZ_SORA = 2