package com.syntxr.anohikari2.presentation.read.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.syntxr.anohikari2.utils.isScrolled

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadTopBar(
    lazyListState: LazyListState,
    title: String?,
    backToHome: () -> Unit
){
    val density = LocalDensity.current
    AnimatedVisibility(
        visible = !lazyListState.isScrollInProgress,
        enter = slideInVertically {
            with(density){ -150.dp.roundToPx() }
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