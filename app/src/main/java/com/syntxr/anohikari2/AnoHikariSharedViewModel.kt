package com.syntxr.anohikari2

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.syntxr.anohikari2.domain.model.Sora
import kotlinx.coroutines.flow.MutableStateFlow

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