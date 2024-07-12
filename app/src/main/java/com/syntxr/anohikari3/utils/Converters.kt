package com.syntxr.anohikari3.utils

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.style.CharacterStyle
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.syntxr.anohikari3.BuildConfig
import com.syntxr.anohikari3.data.kotpref.UserPreferences
import com.syntxr.anohikari3.data.source.local.qoran.entity.Jozz
import snow.player.audio.MusicItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern

object Converters {
    fun convertMillisToActualDate(
        date: Long,
    ): String {
        return SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(date))
    }

    fun applyTajweed(
        context: Context,
        ayaText: String,
    ): Spannable {
        return TajweedHelper.getTajweed(
            context,
            ayaText
        )
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

    fun replaceTranslation(translation: String): String {
        val pattern = Pattern.compile("\\(([^)]+)\\)")
        return translation.let { pattern.matcher(it).replaceAll("") }
    }

}

fun intToUrlThreeDigits(int: Int): String {
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

fun Spannable.toAnnotatedString(primaryColor: Color): AnnotatedString {
    val builder = AnnotatedString.Builder(this.toString())
    val copierContext = CopierContext(primaryColor)
    SpanCopier.entries.forEach { copier ->
        getSpans(0, length, copier.spanClass).forEach { span ->
            copier.copySpan(span, getSpanStart(span), getSpanEnd(span), builder, copierContext)
        }
    }
    return builder.toAnnotatedString()
}

private data class CopierContext(
    val primaryColor: Color,
)

private enum class SpanCopier {
    URL {
        override val spanClass = URLSpan::class.java
        override fun copySpan(
            span: Any,
            start: Int,
            end: Int,
            destination: AnnotatedString.Builder,
            context: CopierContext,
        ) {
            val urlSpan = span as URLSpan
            destination.addStringAnnotation(
                tag = name,
                annotation = urlSpan.url,
                start = start,
                end = end,
            )
            destination.addStyle(
                style = SpanStyle(
                    color = context.primaryColor,
                    textDecoration = TextDecoration.Underline
                ),
                start = start,
                end = end,
            )
        }
    },
    FOREGROUND_COLOR {
        override val spanClass = ForegroundColorSpan::class.java
        override fun copySpan(
            span: Any,
            start: Int,
            end: Int,
            destination: AnnotatedString.Builder,
            context: CopierContext,
        ) {
            val colorSpan = span as ForegroundColorSpan
            destination.addStyle(
                style = SpanStyle(color = Color(colorSpan.foregroundColor)),
                start = start,
                end = end,
            )
        }
    },
    UNDERLINE {
        override val spanClass = UnderlineSpan::class.java
        override fun copySpan(
            span: Any,
            start: Int,
            end: Int,
            destination: AnnotatedString.Builder,
            context: CopierContext,
        ) {
            destination.addStyle(
                style = SpanStyle(textDecoration = TextDecoration.Underline),
                start = start,
                end = end,
            )
        }
    },
    STYLE {
        override val spanClass = StyleSpan::class.java
        override fun copySpan(
            span: Any,
            start: Int,
            end: Int,
            destination: AnnotatedString.Builder,
            context: CopierContext,
        ) {
            val styleSpan = span as StyleSpan

            destination.addStyle(
                style = when (styleSpan.style) {
                    Typeface.ITALIC -> SpanStyle(fontStyle = FontStyle.Italic)
                    Typeface.BOLD -> SpanStyle(fontWeight = FontWeight.Bold)
                    Typeface.BOLD_ITALIC -> SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    )

                    else -> SpanStyle()
                },
                start = start,
                end = end,
            )
        }
    };

    abstract val spanClass: Class<out CharacterStyle>
    abstract fun copySpan(
        span: Any,
        start: Int,
        end: Int,
        destination: AnnotatedString.Builder,
        context: CopierContext,
    )
}

fun String.reverseAyatNumber() : String{
    val digit = mutableListOf<Char>()
    this.forEach {
        if (it.isDigit()){
            digit.add(it)
        }
    }
    val reverseDigit = digit.reversed()
    return this.replace(digit.joinToString(""), reverseDigit.joinToString(""))
}