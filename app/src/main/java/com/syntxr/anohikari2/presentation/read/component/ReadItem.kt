package com.syntxr.anohikari2.presentation.read.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material.icons.rounded.CopyAll
import androidx.compose.material.icons.rounded.IosShare
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.syntxr.anohikari2.ui.theme.montserratFontFamily
import com.syntxr.anohikari2.ui.theme.rubikFontFamily
import snow.player.PlayMode

@Composable
fun AyaSoraCard(
    soraArName: String,
    soraEnName: String,
    soraIdName: String,
    soraDescend: String,
    ayas: Int,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = modifier.fillMaxWidth()
        ) {

            Spacer(modifier = Modifier.heightIn(24.dp))

            Text(
                text = soraArName,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(4.dp))

            TextButton(modifier = modifier.align(Alignment.CenterHorizontally),
                onClick = { /*TODO*/ }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Rounded.PlayArrow, contentDescription = "icon_play"
                    )

                    Spacer(modifier = Modifier.width(2.dp))

                    Text(
                        text = "Play Surah",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                }
            }

            Spacer(modifier = Modifier.heightIn(24.dp))

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = soraEnName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = modifier.align(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = soraIdName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = modifier.align(Alignment.Start)
                    )
                }

                Column {
                    Text(
                        text = soraDescend,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = modifier.align(Alignment.End)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "$ayas Ayahs",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = modifier.align(Alignment.End)
                    )
                }
            }

        }
    }
}

//


@Composable
fun AyaReadItem(
    id: Int,
    ayaText: String,
    translation: String,
    soraNo: Int,
    ayaNo: Int,
    bookmarks: List<Int>?,
    isAudioPlay: Boolean,
    playMode: PlayMode,
    currentPlayAya: Int,
    onInsertBookmarkClick: () -> Unit,
    onDeleteBookmarkClick: () -> Unit,
    onShareClick: () -> Unit,
    onCopyClick: () -> Unit,
    onPlayClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    var bookmarkState by remember {
        mutableStateOf(false)
    }
    bookmarkState = when (bookmarks?.contains(id)) {
        true -> true
        false -> false
        null -> false
    }


    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = ayaText,
                modifier = modifier.align(Alignment.End),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Normal,
                fontFamily = rubikFontFamily,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = translation,
                modifier = modifier.align(Alignment.Start),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal,
                fontFamily = montserratFontFamily,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = modifier
                    .padding(8.dp)
                    .align(Alignment.End),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                IconToggleButton(
                    checked = bookmarkState,
                    onCheckedChange = {
                        bookmarkState = if (bookmarkState) {
                            onDeleteBookmarkClick()
                            false
                        } else {
                            onInsertBookmarkClick()
                            true
                        }
                    },
                ) {
                    Icon(
                        imageVector = when (bookmarkState) {
                            true -> {
                                Icons.Rounded.Bookmark
                            }

                            false -> {
                                Icons.Rounded.BookmarkBorder
                            }
                        },
                        contentDescription = "btn_bookmark"
                    )
                }

                IconButton(onClick = onCopyClick) {
                    Icon(
                        imageVector = Icons.Rounded.CopyAll,
                        contentDescription = "btn_copy"
                    )
                }

                IconButton(onClick = onShareClick) {
                    Icon(
                        imageVector = Icons.Rounded.IosShare,
                        contentDescription = "btn_share"
                    )
                }

                IconButton(
                    onClick = {
                        onPlayClick(); onPlayPauseClick()
                    }
                ) {
                    if (currentPlayAya == id) {
                        Icon(
                            imageVector = if (isAudioPlay) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
                            contentDescription = "btn_play"
                        )
                    }else{
                        Icon(
                            imageVector = Icons.Rounded.PlayArrow,
                            contentDescription = "btn_play"
                        )
                    }
                }

                Text(
                    text = "$ayaNo-$soraNo", fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Divider(
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}