package com.syntxr.anohikari3.data.source.local.qoran.entity

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.PrimaryKey

@DatabaseView("SELECT id, jozz, sora, sora_name_en, sora_name_ar, MIN(aya_no) as aya_no FROM quran GROUP BY sora, jozz")
data class Jozz(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo("jozz") val jozzNo: Int? = 0,
    @ColumnInfo("sora") val soraNo: Int? = 0,
    @ColumnInfo("sora_name_en") val soraEn: String? = "",
    @ColumnInfo("sora_name_ar") val soraAr: String? = "",
    @ColumnInfo("aya_no") val ayaNo: Int? = 0,
)