package com.syntxr.anohikari2.presentation.home.jozz

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
import com.syntxr.anohikari2.domain.model.Jozz
import com.syntxr.anohikari2.presentation.home.jozz.component.JozzItem
import com.syntxr.anohikari2.presentation.read.AYA_BY_JOZZ
import com.syntxr.anohikari2.ui.theme.AnoHikariTheme
import com.syntxr.anohikari2.utils.getJozzSoras

@Composable
fun JozzPage(
    jozzes: List<Jozz>,
    modifier: Modifier = Modifier,
    navigation: (soraNo: Int?, jozzNo: Int?, indexType: Int, scrollPos: Int?) -> Unit,
    lazyState: LazyListState,
) {
    LazyColumn(
        state = lazyState,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        content = {
            val jozzMapped = jozzes.getJozzSoras()
            items(jozzMapped) { jozz ->
                JozzItem(
                    modifier = modifier.clickable {
                        navigation(
                            null,
                            jozz.jozzNo,
                            AYA_BY_JOZZ,
                            null
                        )
                    },
                    jozzNo = jozz.jozzNo ?: 0,
                    soras = jozz.soras,
                    sorasNo = jozz.sorasNo,
                    ayasNo = jozz.ayasNo,
                    navigation = { soraNo, jozzNo, indexType, scrollPos ->
                        navigation(soraNo, jozzNo, indexType, scrollPos)
                    }
                )
            }
        }
    )
}

@Preview
@Composable
fun JozzPagePreview() {
    AnoHikariTheme {
//        JozzPage()
    }
}