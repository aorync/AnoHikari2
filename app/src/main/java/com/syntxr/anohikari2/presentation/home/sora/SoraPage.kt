package com.syntxr.anohikari2.presentation.home.sora

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.syntxr.anohikari2.domain.model.Sora
import com.syntxr.anohikari2.presentation.home.sora.component.SoraItem
import com.syntxr.anohikari2.presentation.read.AYA_BY_SORA
import com.syntxr.anohikari2.ui.theme.AnoHikariTheme

@Composable
fun SoraPage(
    soras: List<Sora>,
    modifier: Modifier = Modifier,
    navigation: (soraNo: Int?, jozzNo: Int?, indexType: Int, scrollPos: Int?) -> Unit,
    lazyState: LazyListState,
) {
    LazyColumn(
        state = lazyState,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        content = {

            items(soras) { sora ->
                SoraItem(
                    modifier = modifier.clickable {
                        navigation(
                            sora.soraNo, null, AYA_BY_SORA, null
                        )
                    },
                    sora = sora
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SoraPagePreview() {
    AnoHikariTheme {

    }
}