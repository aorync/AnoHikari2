package com.syntxr.anohikari2.data.source.remote.response


import com.google.gson.annotations.SerializedName
import com.syntxr.anohikari2.domain.model.Adzan

data class AdzanResponse(
    @SerializedName("city")
    val city: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("lng")
    val lng: Double,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("province")
    val province: String,
    @SerializedName("times")
    val times: List<Time>
) {
    fun  AdzanResponse.toAdzan() : Adzan {
        return Adzan(
            city = city,
            lat = lat,
            latitude = latitude,
            lng = lng,
            longitude = longitude,
            province = province,
            asr = times.first().asr,
            dhuha = times.first().dhuha,
            dhuhr = times.first().dhuhr,
            fajr = times.first().fajr,
            gregorian = times.first().gregorian,
            hijri = times.first().hijri,
            imsak = times.first().imsak,
            isha = times.first().isha,
            maghrib = times.first().maghrib,
            sunset = times.first().sunset
        )
    }
}