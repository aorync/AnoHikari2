package com.syntxr.anohikari2.data.source.local.adzan.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.syntxr.anohikari2.domain.model.Adzan

@Entity(tableName = "adzan")
data class AdzanEntity(
    val city : String,
    val lat: Double,
    val latitude: String,
    val lng: Double,
    val longitude: String,
    val province: String,
    val asr: String,
    val dhuha: String,
    val dhuhr: String,
    val fajr: String,
    val gregorian: String,
    val hijri: String,
    val imsak: String,
    val isha: String,
    val maghrib: String,
    val sunset: String,
    @PrimaryKey(true)
    val id: Int? = null,
) {
    fun AdzanEntity.toAdzan() : Adzan {
        return Adzan(
            city = city,
            lat = lat,
            latitude = latitude,
            lng = lng,
            longitude = longitude,
            province = province,
            asr = asr,
            dhuha = dhuha,
            dhuhr = dhuhr,
            fajr = fajr,
            gregorian = gregorian,
            hijri = hijri,
            imsak = imsak,
            isha = isha,
            maghrib = maghrib,
            sunset = sunset
        )
    }
}
