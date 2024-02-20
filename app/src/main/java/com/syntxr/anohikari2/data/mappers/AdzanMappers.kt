package com.syntxr.anohikari2.data.mappers

import com.syntxr.anohikari2.data.source.local.adzan.entity.AdzanEntity
import com.syntxr.anohikari2.data.source.remote.response.AdzanResponse
import com.syntxr.anohikari2.domain.model.Adzan

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

fun Adzan.toAdzanEntity() : AdzanEntity {
    return AdzanEntity(
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