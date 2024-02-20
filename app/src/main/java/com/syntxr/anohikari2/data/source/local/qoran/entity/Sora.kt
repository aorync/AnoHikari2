package com.syntxr.anohikari2.data.source.local.qoran.entity

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Ignore
import androidx.room.PrimaryKey

@DatabaseView("SELECT id, sora, sora_name_en, sora_name_ar, sora_name_id, sora_descend_place, COUNT(aya_no) as total_aya FROM quran GROUP BY sora")
data class Sora(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo("sora") val soraNo: Int? = 0,
    @ColumnInfo("sora_name_en") val soraEn: String? = "",
    @ColumnInfo("sora_name_ar") val soraAr: String? = "",
    @ColumnInfo("sora_name_id") val soraId: String? = "",
    @ColumnInfo("sora_descend_place") val soraPlace: String? = "",
    @ColumnInfo("total_aya") val ayas: Int? = 0,
    )