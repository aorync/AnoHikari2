package com.syntxr.anohikari2.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.syntxr.anohikari2.presentation.read.AYA_BY_SORA

@Entity(tableName = "bookmark")
class Bookmark (
    @PrimaryKey val id: Int? = null,
    @ColumnInfo(name = "sora_name_en") val soraEn: String? = "",
    @ColumnInfo(name = "aya_text") val ayaText: String? = "",
    @ColumnInfo(name = "aya_no") val ayaNo: Int? = 0,
    @ColumnInfo(name = "sora") val soraNo: Int? = 0,
    @ColumnInfo(name = "jozz") val jozzNo: Int? = 0,
    @ColumnInfo(name = "position") val scrollPosition: Int? = 0,
    val indexType: Int? = AYA_BY_SORA,
    val timeStamp: Long = System.currentTimeMillis()
)