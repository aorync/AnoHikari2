package com.syntxr.anohikari3.presentation.read.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Repeat
import androidx.compose.material.icons.rounded.RepeatOne
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.syntxr.anohikari3.data.kotpref.UserPreferences
import com.syntxr.anohikari3.presentation.read.PlayType
import com.syntxr.anohikari3.ui.theme.novaMonoFontFamily
import snow.player.PlayMode

@Composable
fun ReadAudioControl(
    isPlaying: Boolean,
    playMode: PlayMode,
    playType: PlayType,
    currentAyaPlayName: String,
    onPrevious: () -> Unit,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onStop: () -> Unit,
    onChangePlayMode: () -> Unit,
    modifier: Modifier = Modifier,
    qori: String = UserPreferences.currentQori.qoriName,
) {
    BottomAppBar(
        modifier = modifier.height(128.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = currentAyaPlayName,
                    style = typography.titleMedium,
                    color = colorScheme.onTertiaryContainer,
                    fontFamily = novaMonoFontFamily,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Text(
                    text = qori,
                    style = typography.titleMedium,
                    color = colorScheme.onTertiaryContainer,
                    fontWeight = FontWeight.Normal,
                    fontFamily = novaMonoFontFamily,
                    textAlign = TextAlign.End,
                    modifier = Modifier.padding(horizontal = 8.dp),
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                IconButton(
                    onClick = onPrevious
                ) {
                    Icon(
                        imageVector = Icons.Rounded.SkipPrevious,
                        tint = colorScheme.onTertiaryContainer,
                        contentDescription = "Previous Skip"
                    )
                }
                IconButton(
                    onClick = onPlayPause
                ) {
                    Icon(
                        imageVector = if (!isPlaying) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
                        tint = colorScheme.onTertiaryContainer,
                        contentDescription = "Play Pause"
                    )
                }

                IconButton(
                    onClick = onNext
                ) {
                    Icon(
                        imageVector = Icons.Rounded.SkipNext,
                        tint = colorScheme.onTertiaryContainer,
                        contentDescription = "Next Skip"
                    )
                }

                IconButton(
                    onClick = onChangePlayMode
                ) {
                    Icon(
                        imageVector = if (playMode == PlayMode.SINGLE_ONCE) Icons.Rounded.Repeat else Icons.Rounded.RepeatOne,
                        tint = colorScheme.onTertiaryContainer,
                        contentDescription = "Change Play Mode"
                    )
                }

                IconButton(
                    onClick = onStop
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        tint = colorScheme.onTertiaryContainer,
                        contentDescription = "Close"
                    )
                }


            }
        }
    }
}
