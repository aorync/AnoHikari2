package com.syntxr.anohikari2.presentation.read

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.syntxr.anohikari2.AnoHikariSharedViewModel
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
    navigator: DestinationsNavigator,
    viewModel: ReadViewModel = hiltViewModel(),
    sharedViewModel: AnoHikariSharedViewModel,
) {
    val state = viewModel.state.value
    val lazyColumnState = rememberLazyListState()
    val totalAyas = remember {
        sharedViewModel.getAyahs()
    }


    LaunchedEffect(Unit) {
        delay(300)
        lazyColumnState.scrollToItem(viewModel.scrollPosition - 1, viewModel.scrollPosition - 1)
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
                    items(state.ayas?.size ?: 0){index ->
                        if (!state.ayas.isNullOrEmpty()){
                            val qoran = state.ayas[index]
                            if (qoran.ayaNo == 1){
                                AyaSoraCard(
                                    soraArName = qoran.soraAr ?: "",
                                    soraEnName = qoran.soraEn ?: "",
                                    soraIdName = qoran.soraId ?: "",
                                    soraDescend = qoran.soraPlace ?: "",
                                    ayas = totalAyas?.get(index) ?: 0
                                )
                            }
                            AyaReadItem(
                                ayaText = qoran.ayaText ?: "",
                                translation = qoran.translationId ?: "",
                                soraNo = qoran.soraNo ?: 0,
                                ayaNo = qoran.ayaNo ?: 0
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