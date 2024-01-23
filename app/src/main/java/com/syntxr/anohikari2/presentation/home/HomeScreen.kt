package com.syntxr.anohikari2.presentation.home

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigate
import com.syntxr.anohikari2.AnoHikariSharedViewModel
import com.syntxr.anohikari2.presentation.destinations.ReadScreenDestination
import com.syntxr.anohikari2.presentation.home.bookmark.BookmarkPage
import com.syntxr.anohikari2.presentation.home.component.HomeHeader
import com.syntxr.anohikari2.presentation.home.jozz.JozzPage
import com.syntxr.anohikari2.presentation.home.sora.SoraPage
import com.syntxr.anohikari2.presentation.read.ReadScreenNavArgs
import kotlinx.coroutines.launch

@RootNavGraph(start = true)
@Destination
@OptIn(
    ExperimentalFoundationApi::class,
)
@Composable
fun HomeScreen(
    openDrawer: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    sharedViewModel: AnoHikariSharedViewModel,
//    navController: NavHostController,
    navigator: DestinationsNavigator
) {

    val state = viewModel.state.value
    val lazyColumnState = rememberLazyListState()

    var isDefault by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = state){
        state.soras?.let { sharedViewModel.setAyahs(it) }
    }


    Log.d("SORRRRRRRRRRRRRA", "HomeScreen: ${state.soras}")


    val tabItems = listOf(
        TabItem(
            title = "Surah",
            content = {
                SoraPage(
                    lazyState = lazyColumnState,
                    soras = state.soras ?: emptyList(),
                    modifier = Modifier.fillMaxSize(),
                    navigation = { soraNo, jozzNo, indexType, scrollPos ->
                        navigator.navigate(
                            ReadScreenDestination(
                                ReadScreenNavArgs(
                                    soraNumber = soraNo,
                                    jozzNumber = jozzNo,
                                    indexType = indexType,
                                    scrollPosition = scrollPos,
                                )
                            )
                        )
                    }
                )
            }
        ),
        TabItem(
            title = "Juz",
            content = {
                JozzPage(
                    lazyState = lazyColumnState,
                    jozzes = state.jozzes ?: emptyList(),
                    modifier = Modifier.fillMaxSize(),
                    navigation = {soraNo, jozzNo, indexType, scrollPos ->
                        navigator.navigate(
                            ReadScreenDestination(
                                ReadScreenNavArgs(
                                    soraNumber = soraNo,
                                    jozzNumber = jozzNo,
                                    indexType = indexType,
                                    scrollPosition = scrollPos,
                                )
                            )
                        )
                    }
                )
            }
        ),
        TabItem(
            title = "Bookmark",
            content = {
                BookmarkPage(
                    lazyState = lazyColumnState,
                    bookmarks = state.bookmarks ?: emptyList(),
                    navigation = {soraNo, jozzNo, indexType, scrollPos ->
                        navigator.navigate(
                            ReadScreenDestination(
                                ReadScreenNavArgs(
                                    soraNumber = soraNo,
                                    jozzNumber = jozzNo,
                                    indexType = indexType,
                                    scrollPosition = scrollPos,
                                )
                            )
                        )
                    }
                )
            }
        )
    )
    val pagerState = rememberPagerState(pageCount = { tabItems.size })
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize(),
        ) {

            HomeHeader(
                lazyListState = lazyColumnState,
                time = viewModel.getTime(),
                openDrawer = openDrawer
            )

            Card(
                modifier = Modifier.fillMaxSize(),
                colors = CardDefaults
                    .cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                shape = RoundedCornerShape(
                    topStart = 8.dp,
                    topEnd = 8.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 0.dp
                )
            ) {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                ) {
                    tabItems.forEachIndexed { index, tabItem ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = {
                                Text(
                                    text = tabItem.title,
                                )
                            }
                        )
                    }
                }

                Row(
                    Modifier
                        .align(alignment = Alignment.End)
                        .padding(vertical = 2.dp, horizontal = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Sort By: ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    TextButton(
                        onClick = {
                            isDefault = !isDefault
                            viewModel.getSort(isDefault)
                        }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isDefault) "Ascending" else "Descending",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(2.dp)
                            )
                            Icon(
                                imageVector = if (isDefault) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown,
                                contentDescription = "sort_arrow",
                                tint = MaterialTheme.colorScheme.onSurface
                            )


                        }
                    }
                }

                HorizontalPager(
                    state = pagerState,
                    userScrollEnabled = false
                ) {
                    tabItems[pagerState.currentPage].content()
                }
            }


        }
    }
}


data class TabItem(
    val title: String,
    val content: @Composable () -> Unit,
)
