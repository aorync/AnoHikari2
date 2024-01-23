package com.syntxr.anohikari2.presentation.home.bookmark

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.syntxr.anohikari2.domain.model.Bookmark
import com.syntxr.anohikari2.presentation.home.bookmark.component.BookmarkItem
import com.syntxr.anohikari2.presentation.read.AYA_BY_SORA
import com.syntxr.anohikari2.ui.theme.novaMonoFontFamily

@Composable
fun BookmarkPage(
    bookmarks: List<Bookmark>,
    lazyState: LazyListState,
    navigation: (soraNo: Int?, jozzNo: Int?, indexType: Int, scrollPos: Int?) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (bookmarks.isNotEmpty()) {
        LazyColumn(
            state = lazyState,
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            content = {
                items(bookmarks.size) { index ->
                    BookmarkItem(
                        modifier = modifier.clickable {
                            navigation(
                                bookmarks[index].soraNo,
                                bookmarks[index].jozzNo,
                                bookmarks[index].indexType ?: AYA_BY_SORA,
                                bookmarks[index].scrollPosition
                            )
                        },
                        no = index.plus(1),
                        soraEn = bookmarks[index].soraEn ?: "",
                        ayaNo = bookmarks[index].ayaNo ?: 1,
                        date = bookmarks[index].timeStamp,
                        ayaText = bookmarks[index].ayaText ?: "",
                    )
                }
            }
        )
    }else{
        Box(
            modifier = modifier.fillMaxSize()
        ){
            Text(
                text = "Belum ada penanda yang ditambahkan",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold,
                fontFamily = novaMonoFontFamily,
                color = MaterialTheme.colorScheme.secondary,
                modifier = modifier.align(Alignment.Center)
            )
        }
    }


}