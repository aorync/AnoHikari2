package com.syntxr.anohikari2.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.syntxr.anohikari2.R
import com.syntxr.anohikari2.data.kotpref.UserPreferences
import com.syntxr.anohikari2.presentation.destinations.ReadScreenDestination
import com.syntxr.anohikari2.presentation.read.AYA_BY_SORA
import com.syntxr.anohikari2.presentation.read.ReadScreenNavArgs
import com.syntxr.anohikari2.presentation.search.component.SearchItem
import com.syntxr.anohikari2.utils.AppGlobalState
import com.syntxr.anohikari2.utils.Converters

@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    AppGlobalState.drawerGesture = false
    val uiState by viewModel.uiState.collectAsState()
    val ayaMatched by viewModel.ayaMatched.collectAsState()

    var queryState by remember {
        mutableStateOf("")
    }

    var activeState by remember {
        mutableStateOf(false)
    }

    val snackBarHostState = SnackbarHostState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.txt_search_title),
                    fontWeight = FontWeight.Medium
                )
            }, navigationIcon = {
                IconButton(onClick = { navigator.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBackIosNew, contentDescription = "Back"
                    )
                }
            })
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            SearchBar(
                query = queryState,
                onQueryChange = { query ->
                    queryState = query
                },
                onSearch = { query ->
                    activeState = true
                    viewModel.onEvent(SearchEvent.OnSearch(query))
                },
                active = activeState,
                onActiveChange = { active ->
                    activeState = active
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.txt_search_aya_idle))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "search lead icon"
                    )
                },
                trailingIcon = {
                    if (activeState) {
                        IconButton(onClick = {
                            if (queryState.isNotEmpty()) {
                                queryState = ""
                            } else {
                                activeState = false
                                viewModel.onEvent(SearchEvent.Clear)
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "btn_close"
                            )
                        }
                    }
                },
            ) {
                when (val state = uiState) {
                    is SearchUiState.Error -> {
                        LaunchedEffect(key1 = true) {
                            snackBarHostState.showSnackbar(state.message)
                        }
                    }

                    SearchUiState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    SearchUiState.Success -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(ayaMatched) { qoran ->
                                SearchItem(
                                    ayaText = qoran.ayaText ?: ""
                                    ,
                                    translation =
                                    AnnotatedString.Builder().run {
                                        val translations = if (AppGlobalState.currentLanguage == UserPreferences.Language.ID.tag)
                                            qoran.translationId?.split("")
                                        else
                                            Converters.replaceTranslation(qoran.translationEn ?: "").split("")

                                        if (translations != null) {
                                            for (translation in translations){
                                                val trimmedTranslation = translation.trim{it <= ' '}
                                                val highlighted = trimmedTranslation.contentEquals(queryState, ignoreCase = true)
                                                if (highlighted){
                                                    pushStyle(SpanStyle(background = MaterialTheme.colorScheme.primaryContainer))
                                                }

                                                append("$translation")
                                                if (highlighted){
                                                    pop()
                                                }
                                            }
                                        }

                                        toAnnotatedString()
                                    },
                                    modifier = Modifier.clickable {
                                        navigator.navigate(
                                            ReadScreenDestination(
                                                ReadScreenNavArgs(
                                                    soraNumber = qoran.soraNo,
                                                    jozzNumber = qoran.jozz,
                                                    indexType = AYA_BY_SORA,
                                                    scrollPosition = qoran.ayaNo?.minus(1)
                                                )
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }

                    null -> {}
                }

            }
        }
    }
}