package com.syntxr.anohikari2.utils

import androidx.compose.foundation.lazy.LazyListState
import com.syntxr.anohikari2.domain.model.Jozz

fun List<Jozz>.getJozzSoras() : List<JozzSoras> {
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
    val ayasNo: List<Int?>
)