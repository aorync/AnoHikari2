package com.syntxr.anohikari2.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Translate
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.ramcosta.composedestinations.annotation.Destination
import com.syntxr.anohikari2.data.kotpref.UserPreferences
import com.syntxr.anohikari2.data.kotpref.UserPreferences.Language
import com.syntxr.anohikari2.data.kotpref.UserPreferences.Qori
import com.syntxr.anohikari2.data.kotpref.UserPreferences.currentLanguage
import com.syntxr.anohikari2.data.kotpref.UserPreferences.currentQori
import com.syntxr.anohikari2.presentation.settings.component.Action
import com.syntxr.anohikari2.presentation.settings.component.ClickableCardSettings
import com.syntxr.anohikari2.presentation.settings.component.SettingsAlertDialog
import com.syntxr.anohikari2.presentation.settings.component.SwitchableCardSettings
import com.syntxr.anohikari2.utils.AppGlobalState

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun SettingsScreen(
    openDrawer: () -> Unit,
) {

    var isLanguageDialogShow by remember {
        mutableStateOf(false)
    }

    var isQariDialogShow by remember {
        mutableStateOf(false)
    }

    var selectedLanguage by remember {
        mutableStateOf(currentLanguage)
    }

    var selectedQari by remember {
        mutableStateOf(currentQori)
    }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = openDrawer
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Menu,
                            contentDescription = "Hamburger"
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Text(text = "Settings")

            SwitchableCardSettings(
                label = if (AppGlobalState.isDarkTheme) "Dark Theme" else "Light Theme",
                description = "Change your theme. dark or light ?",
                icon = if (AppGlobalState.isDarkTheme) Icons.Rounded.DarkMode else Icons.Rounded.LightMode,
                isSwitched = AppGlobalState.isDarkTheme,
                onSwitch = { isChecked ->
                    AppGlobalState.isDarkTheme = isChecked
                    UserPreferences.isDarkTheme = isChecked
                }
            )

            ClickableCardSettings(
                label = "Language",
                description = "Select the language that prefer to use",
                icon = Icons.Rounded.Translate,
                currentValue = selectedLanguage.language,
                onClick = { isLanguageDialogShow = true }
            )

            ClickableCardSettings(
                label = "Qari",
                description = "Select reciter for audio quran",
                icon = Icons.Rounded.Person,
                currentValue = selectedQari.qoriName,
                onClick = { isQariDialogShow = true }
            )

            if (isLanguageDialogShow){
                val actions = listOf(
                    Action(
                        text = Language.ID.language,
                        onClick = {
                            isLanguageDialogShow = false
                            currentLanguage = Language.ID
                            selectedLanguage = Language.ID
                        }
                    ),
                    Action(
                        text = Language.EN.language,
                        onClick = {
                            isLanguageDialogShow = false
                            currentLanguage = Language.EN
                            selectedLanguage = Language.EN
                        }
                    )
                )
                SettingsAlertDialog(
                    icon = Icons.Rounded.Translate,
                    title = "Language",
                    currentSelected = currentLanguage.language,
                    actions = actions,
                    onDismissClick = { isLanguageDialogShow = false },
                    onConfirmClick = { isLanguageDialogShow = false },
                    dismissButtonText = "Cancel"
                )
            }

            if (isQariDialogShow){
                val actions = listOf(
                    Action(
                        text = Qori.ABD_SUDAIS.qoriName,
                        onClick = {
                            isQariDialogShow = false
                            currentQori = Qori.ABD_SUDAIS
                            selectedQari = Qori.ABD_SUDAIS
                        }
                    ),
                    Action(
                        text = Qori.ALAFASY.qoriName,
                        onClick = {
                            isQariDialogShow = false
                            currentQori = Qori.ALAFASY
                            selectedQari = Qori.ALAFASY
                        }
                    ),
                    Action(
                        text = Qori.HANI_RIFAI.qoriName,
                        onClick = {
                            isQariDialogShow = false
                            currentQori = Qori.HANI_RIFAI
                            selectedQari = Qori.HANI_RIFAI
                        }
                    ),
                    Action(
                        text = Qori.HUDHAIFY.qoriName,
                        onClick = {
                            isQariDialogShow = false
                            currentQori = Qori.HUDHAIFY
                            selectedQari = Qori.HUDHAIFY
                        }
                    ),
                    Action(
                        text = Qori.IBRAHIM_AKHDAR.qoriName,
                        onClick = {
                            isQariDialogShow = false
                            currentQori = Qori.IBRAHIM_AKHDAR
                            selectedQari = Qori.IBRAHIM_AKHDAR
                        }
                    )
                )
                SettingsAlertDialog(
                    icon = Icons.Rounded.Person,
                    title = "Qari",
                    currentSelected = selectedQari.qoriName,
                    actions = actions,
                    onDismissClick = { isQariDialogShow = false },
                    onConfirmClick = { isQariDialogShow = false },
                    dismissButtonText = "Cancel"
                )
            }
        }
    }
}