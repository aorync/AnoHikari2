package com.syntxr.anohikari2.presentation.read.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material.icons.rounded.CopyAll
import androidx.compose.material.icons.rounded.IosShare
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.syntxr.anohikari2.R
import com.syntxr.anohikari2.ui.theme.AnoHikariTheme

@Composable
fun AyaReadItem(
    ayaText: String,
    translation: String,
    soraNo: Int,
    ayaNo: Int,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
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
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = translation,
                modifier = modifier.align(Alignment.Start),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal,
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
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Rounded.BookmarkBorder,
                        contentDescription = "btn_bookmark"
                    )
                }

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Rounded.CopyAll, contentDescription = "btn_copy"
                    )
                }

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Rounded.IosShare, contentDescription = "btn_share"
                    )
                }

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Rounded.PlayArrow, contentDescription = "btn_play"
                    )
                }

                Text(
                    text = "$soraNo-$ayaNo", fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Divider(
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun AyaSoraCard(
    soraArName: String,
    soraEnName: String,
    soraIdName: String,
    soraDescend: String,
    ayas: Int,
    modifier: Modifier = Modifier
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

@OptIn(ExperimentalMotionApi::class)
@Composable
fun AyaSoraHeader(lazyScrollState: LazyListState) {
    val context = LocalContext.current
    val motionScene = remember {
        context.resources.openRawResource(R.raw.aya_sora_header)
            .readBytes()
            .decodeToString()
    }

    val stateIndex = remember {
        derivedStateOf {
            lazyScrollState.firstVisibleItemIndex
        }
    }

    val progress by animateFloatAsState(
        targetValue = if (stateIndex.value in 0..1) 0f else 1f, tween(500),
        label = "animation progress",
    )

    val motionHeight by animateDpAsState(
        targetValue = if (stateIndex.value in 0 .. 1) 230.dp else 56.dp,
        label = "motion height"
    )
    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        progress = progress,
        modifier = Modifier
            .fillMaxWidth()
//            .height(motionHeight)
    ) {
        Box(
            modifier = Modifier
                .layoutId("box")
                .fillMaxWidth()
        )

        IconButton(
            modifier = Modifier.layoutId("back_btn"),
            onClick = { /*TODO*/ }
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBackIos,
                contentDescription = "back_btn_icon"
            )
        }

        Text(
            modifier = Modifier.layoutId("sora_ar_txt"),
            text = "Arabic Name",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
        )

    }

    TextButton(modifier = Modifier.layoutId("play_sora_btn"),
        onClick = { /*TODO*/ }
    ) {
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

    Text(
        modifier = Modifier.layoutId("sora_en_txt"),
        text = "Al-Fatihah",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurface,
    )

    Text(
        modifier = Modifier.layoutId("sora_descend_txt"),
        text = "Makkiyah",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurface,
    )

    Text(
        modifier = Modifier.layoutId("sora_id_txt"),
        text = "Pembuka",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.secondary,
    )

    Text(
        modifier = Modifier.layoutId("ayahs_txt"),
        text = "7 Ayahs",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.secondary,
    )
}

@Preview(showBackground = true)
@Composable
fun ReadItemPreview() {
    AnoHikariTheme {
//        AyaReadItem()
    }
}

@Preview(showBackground = true)
@Composable
fun AyaSoracCardPreview() {
    AnoHikariTheme(darkTheme = false) {
//        AyaSoraCard()
    }
}