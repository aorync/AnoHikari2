package com.syntxr.anohikari2.presentation.home.bookmark

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.syntxr.anohikari2.R
import com.syntxr.anohikari2.domain.model.Bookmark
import com.syntxr.anohikari2.presentation.home.bookmark.component.BookmarkItem
import com.syntxr.anohikari2.presentation.home.bookmark.component.DeleteItemAction
import com.syntxr.anohikari2.presentation.read.AYA_BY_SORA
import com.syntxr.anohikari2.ui.theme.novaMonoFontFamily
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun BookmarkPage(
    bookmarks: List<Bookmark>,
    lazyState: LazyListState,
    deleteBookmark: (Bookmark) -> Unit,
    navigation: (soraNo: Int?, jozzNo: Int?, indexType: Int, scrollPos: Int?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val contentSize = 80.dp
    val endSizePx = with(density) { (contentSize * 2).toPx() }
    val startSizePx = with(density) { contentSize.toPx() }

    val dragstate = AnchoredDraggableState(initialValue = DragAnchors.Center,
        anchors = DraggableAnchors {
            DragAnchors.End at startSizePx
            DragAnchors.Center at 0f
        },
        positionalThreshold = { distance: Float -> distance * 0.5f },
        velocityThreshold = { with(density) { 100.dp.toPx() } },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMediumLow
        )
    )

    if (bookmarks.isNotEmpty()) {
        LazyColumn(state = lazyState,
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            content = {
                itemsIndexed(
                    items = bookmarks,
                    key = { _, item -> item.id ?: 1 }) { index, bookmark ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RectangleShape),
                        content = {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .align(Alignment.CenterEnd)
                                    .offset{
                                        IntOffset(
                                            (-dragstate.requireOffset() + startSizePx)
                                                .roundToInt(), 0
                                        )
                                    }
                            ) {
                                DeleteItemAction(
                                    modifier = Modifier
                                        .width(contentSize)
                                        .fillMaxHeight()
                                )
                            }

                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterStart)
                                .offset {
                                    IntOffset(
                                        x = -dragstate
                                            .requireOffset()
                                            .roundToInt(), y = 0
                                    )
                                }
                                .anchoredDraggable(
                                    dragstate, Orientation.Horizontal, reverseDirection = true
                                ), content = {
                                BookmarkItem(
                                    modifier = modifier.clickable {
                                        navigation(
                                            bookmark.soraNo,
                                            bookmark.jozzNo,
                                            bookmark.indexType ?: AYA_BY_SORA,
                                            bookmark.scrollPosition
                                        )
                                    },
                                    no = index.plus(1),
                                    soraEn = bookmark.soraEn ?: "",
                                    ayaNo = bookmark.ayaNo ?: 1,
                                    date = bookmark.timeStamp,
                                    ayaText = bookmark.ayaText ?: "",
                                )
                            })
                        }
                    )
                }
            }
        )
    } else {
        Box(
            modifier = modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.txt_no_bookmark),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                fontFamily = novaMonoFontFamily,
                color = MaterialTheme.colorScheme.secondary,
                modifier = modifier.align(Alignment.Center)
            )
        }
    }
}

enum class DragAnchors {
    Start, Center, End
}