package com.syntxr.anohikari2.utils

import androidx.compose.foundation.lazy.LazyListState
import com.syntxr.anohikari2.BuildConfig
import com.syntxr.anohikari2.data.kotpref.UserPreferences
import com.syntxr.anohikari2.data.source.local.qoran.entity.Jozz
import snow.player.audio.MusicItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Converters {
    fun convertMillisToActualDate(
        date: Long,
    ): String {
        return SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(date))
    }

    fun createMusicItem(
        title: String,
        ayaNo: String,
        soraNo: String,
    ): MusicItem {
        return MusicItem.Builder()
            .setMusicId("$ayaNo$")
            .autoDuration()
            .setTitle(title)
            .setUri("${BuildConfig.AUDIO_URL}/${UserPreferences.currentQori.url}/$soraNo$ayaNo.mp3")
            .setArtist(UserPreferences.currentQori.qoriName)
            .build()
    }
}

fun IntToUrlThreeDigits (int: Int) : String {
    return String.format("%03d", int)
}

fun List<Jozz>.getJozzSoras(): List<JozzSoras> {
    val mappedList = this.groupBy { it.jozzNo }
    return mappedList.map { (jozzNo, jozzes) ->
        JozzSoras(
            jozzNo,
            jozzes.map { "${it.soraEn} | ${it.soraAr} " },
            jozzes.map { it.soraNo },
            jozzes.map { it.ayaNo }
        )
    }
}

val LazyListState.isScrolled: Boolean
    get() = firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0

data class JozzSoras(
    val jozzNo: Int?,
    val soras: List<String?>,
    val sorasNo: List<Int?>,
    val ayasNo: List<Int?>,
)