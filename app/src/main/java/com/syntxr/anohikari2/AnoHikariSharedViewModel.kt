package com.syntxr.anohikari2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.syntxr.anohikari2.data.source.local.qoran.entity.Sora

class AnoHikariSharedViewModel : ViewModel() {
    private val _totalAya = MutableLiveData<List<Int>>()

    fun setAyahs(soras: List<Sora>){
        val ayas = mutableListOf<Int>()
        soras.forEach {
            it.ayas?.let { it1 -> ayas.add(it1) }
        }
        _totalAya.value = ayas
    }

    fun getAyahs() = _totalAya.value
}