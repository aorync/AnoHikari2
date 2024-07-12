package com.syntxr.anohikari3.data.source.local.qoran.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quran")
data class Qoran(
    @PrimaryKey val id: Int,
    val jozz: Int? = 0,
    @ColumnInfo("sora") val soraNo: Int? = 0,
    @ColumnInfo("sora_name_en") val soraEn: String? = "",
    @ColumnInfo("sora_name_ar") val soraAr: String? = "",
    val page: Int? = 0,
    @ColumnInfo("aya_no") val ayaNo: Int? = 0,
    @ColumnInfo("aya_text") val ayaText: String? = "",
    @ColumnInfo("aya_text_emlaey") val ayaEmlaey: String? = "",
    @ColumnInfo("translation_id") val translationId: String? = "",
    @ColumnInfo("footnotes_id") val footnotesId: String? = "",
    @ColumnInfo("sora_name_id") val soraId: String? = "",
    @ColumnInfo("sora_descend_place") val soraPlace: String? = "",
    @ColumnInfo("sora_name_emlaey") val soraEm: String? = "",
    @ColumnInfo("translation_en") val translationEn: String? = "",
    @ColumnInfo("footnotes_en") val footnotesEn: String? = "",
)