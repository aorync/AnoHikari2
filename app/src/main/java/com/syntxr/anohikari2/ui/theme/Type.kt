package com.syntxr.anohikari2.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.syntxr.anohikari2.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val rubilkFontName = GoogleFont("Rubik")
val montserratFontName = GoogleFont("Montserrat")
val novaMonoFontName = GoogleFont("Nova Mono")
val ubuntuMonoFontName = GoogleFont("Ubuntu Mono")

val rubikFontFamily = FontFamily(
    Font(
        googleFont = rubilkFontName,
        fontProvider = provider,
    )
)

val montserratFontFamily = FontFamily(
    Font(
        googleFont = montserratFontName,
        fontProvider = provider,
    )
)

val novaMonoFontFamily = FontFamily(
    Font(
        googleFont = novaMonoFontName,
        fontProvider = provider,
    )
)

val ubuntuMonoFontFamily = FontFamily(
    Font(
        googleFont = ubuntuMonoFontName,
        fontProvider = provider,
    )
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)