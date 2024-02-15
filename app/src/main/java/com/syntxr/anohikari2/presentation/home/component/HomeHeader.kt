package com.syntxr.anohikari2.presentation.home.component

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.syntxr.anohikari2.R
import com.syntxr.anohikari2.data.kotpref.LastReadPreferences
import com.syntxr.anohikari2.data.kotpref.LastReadPreferences.ayaNumber
import com.syntxr.anohikari2.data.kotpref.LastReadPreferences.indexType
import com.syntxr.anohikari2.data.kotpref.LastReadPreferences.jozzNumber
import com.syntxr.anohikari2.data.kotpref.LastReadPreferences.scrollPosition
import com.syntxr.anohikari2.data.kotpref.LastReadPreferences.soraName
import com.syntxr.anohikari2.data.kotpref.LastReadPreferences.soraNumber
import com.syntxr.anohikari2.presentation.home.Time
import com.syntxr.anohikari2.ui.theme.montserratFontFamily
import com.syntxr.anohikari2.ui.theme.ubuntuMonoFontFamily

@SuppressLint("FrequentlyChangedStateReadInComposition", "SimpleDateFormat")
@OptIn(ExperimentalMotionApi::class)
@Composable
fun HomeHeader(
    lazyListState: LazyListState,
    time: Time,
    navigateLastReadToRead: (soraNo: Int?, jozzNo: Int?, indexType: Int, scrollPos: Int?) -> Unit,
    openDrawer: () -> Unit,
) {
    val context = LocalContext.current
    val motionScene = remember {
        context.resources.openRawResource(R.raw.home_header).readBytes().decodeToString()
    }

    val progress by animateFloatAsState(
        targetValue = if (lazyListState.firstVisibleItemIndex in 0..1) 0f else 1f,
        tween(500),
        label = "progress animation"
    )

    val motionHeight by animateDpAsState(
        targetValue = if (lazyListState.firstVisibleItemIndex in 0..1) 230.dp else 56.dp,
        tween(500),
        label = "motion height"
    )

    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        progress = progress,
        modifier = Modifier
            .fillMaxWidth()
            .height(motionHeight)
    ) {
        Box(
            modifier = Modifier
                .layoutId("box")
                .paint(
                    painterResource(
                        id =

                        if (time.midnight) {
                            R.drawable.midnight_sky
                        } else if (time.dusk) {
                            R.drawable.dusk_sky
                        } else if (time.day) {
                            R.drawable.day_sky
                        } else if (time.noon) {
                            R.drawable.noon_sky
                        } else if (time.afternoon) {
                            R.drawable.afternoon_sky
                        } else if (time.dawn) {
                            R.drawable.dawn_sky
                        } else if (time.night) {
                            R.drawable.night_sky
                        } else {
                            R.drawable.midnight_sky
                        }
                    ),
                    contentScale = ContentScale.Crop
                )
                .background(Color(0x5C121212))
        )

        Row(
            modifier = Modifier.layoutId("app_name"),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_quran_compose),
                contentDescription = "appbar_ic",
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )

            Text(
                text = "oran",
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }

        IconButton(
            modifier = Modifier.layoutId("hamburger_btn"),
            onClick = openDrawer
        ) {
            Icon(
                imageVector = Icons.Rounded.Menu,
                contentDescription = "Hamburger",
                tint = Color.White
            )
        }


        IconButton(
            modifier = Modifier.layoutId("search_btn"),
            onClick = { }
        ) {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "btn search",
                tint = Color.White
            )
        }


        Card(
            modifier = Modifier
                .layoutId("recent_read_card")
                .clickable {
                    navigateLastReadToRead(
                        soraNumber,
                        jozzNumber,
                        indexType,
                        scrollPosition
                    )
                },
            colors = CardDefaults
                .cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                Modifier.padding(10.dp),
                horizontalAlignment = Alignment.End
            ) {
                if (soraName == null) {
                    Text(
                        text = stringResource(id = R.string.txt_no_recent_aya),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = montserratFontFamily,
                        color = MaterialTheme.colorScheme.onSurface,
                        letterSpacing = 2.sp,
                    )
                } else {
                    Text(
                        text = stringResource(id = R.string.txt_recent_aya),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = montserratFontFamily,
                        color = MaterialTheme.colorScheme.onSurface,
                        letterSpacing = 2.sp,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$soraName - $ayaNumber",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Normal,
                        fontFamily = ubuntuMonoFontFamily,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }
    }

}