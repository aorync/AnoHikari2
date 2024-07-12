package com.syntxr.anohikari3.data.kotpref

import com.chibatching.kotpref.KotprefModel
import com.syntxr.anohikari3.presentation.read.AYA_BY_SORA

object LastReadPreferences: KotprefModel() {
    var soraName by nullableStringPref(null)
    var soraNumber by intPref(1)
    var jozzNumber by intPref(1)
    var ayaNumber by intPref(1)
    var indexType by intPref(AYA_BY_SORA)
    var scrollPosition by intPref(0)
}