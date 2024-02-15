package com.syntxr.anohikari2.presentation.read.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadTopBar(
    lazyListState: LazyListState,
    title: String?,
    backToHome: () -> Unit,
) {
    val density = LocalDensity.current
    AnimatedVisibility(
        visible = !lazyListState.isScrollInProgress,
        enter = slideInVertically {
            with(density) { -150.dp.roundToPx() }
        } + expandVertically(expandFrom = Alignment.Top)
                + fadeIn(),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = title ?: "",
                    fontWeight = FontWeight.Medium,
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = {
                        backToHome()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBackIos,
                        contentDescription = "btn_back"
                    )
                }
            },
        )
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ReadPlayTopBar(
    lazyListState: LazyListState,
    title: String?,
    backToHome: () -> Unit,
    onPlayAll: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    AnimatedVisibility(
        visible = !lazyListState.isScrollInProgress,
        enter = slideInVertically {
            with(density) { -150.dp.roundToPx() }
        } + expandVertically(expandFrom = Alignment.Top)
                + fadeIn(),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        CenterAlignedTopAppBar(
            title = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title ?: "",
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Row(
                        modifier = modifier
                            .clickable { onPlayAll() }
                            .clip(CircleShape),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.PlayArrow,
                            contentDescription = "Play All"
                        )
                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "Play Surah",
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
            },
            navigationIcon = {
                IconButton(
                    onClick = {
                        backToHome()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBackIos,
                        contentDescription = "btn_back"
                    )
                }
            },
        )
    }

}