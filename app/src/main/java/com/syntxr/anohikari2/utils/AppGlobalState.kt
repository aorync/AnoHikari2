package com.syntxr.anohikari2.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.syntxr.anohikari2.data.kotpref.UserPreferences

object AppGlobalState {
    var isDarkTheme by mutableStateOf(UserPreferences.isDarkTheme)
    var isTajweed by mutableStateOf(UserPreferences.isTajweed)
    var currentLanguage by mutableStateOf(UserPreferences.currentLanguage.tag)
    var drawerGesture by mutableStateOf(UserPreferences.drawerGesture)
}