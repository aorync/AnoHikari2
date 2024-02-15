package com.syntxr.anohikari2

import android.app.LocaleManager
import android.os.Build
import android.os.LocaleList
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.syntxr.anohikari2.presentation.NavGraphs
import com.syntxr.anohikari2.presentation.component.AppDrawer
import com.syntxr.anohikari2.presentation.destinations.HomeScreenDestination
import com.syntxr.anohikari2.presentation.destinations.ReadScreenDestination
import com.syntxr.anohikari2.presentation.destinations.SettingsScreenDestination
import com.syntxr.anohikari2.presentation.home.HomeScreen
import com.syntxr.anohikari2.presentation.read.ReadScreen
import com.syntxr.anohikari2.presentation.settings.SettingsScreen
import com.syntxr.anohikari2.utils.AppGlobalState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AnoHikariApp(
    navController: NavHostController = rememberNavController(),
    scope: CoroutineScope = rememberCoroutineScope()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val sharedViewModel : AnoHikariSharedViewModel = viewModel(LocalContext.current as ComponentActivity)
    val localeLanguage = AppGlobalState.currentLanguage

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
        LocalContext.current.getSystemService(LocaleManager::class.java)
            .applicationLocales
    }else{
        AppCompatDelegate.getApplicationLocales()
    }

    ModalNavigationDrawer(
        drawerContent = {
            AppDrawer(
                navController = navController,
                closeDrawer = { scope.launch { drawerState.close() } }
            )
        },
        drawerState = drawerState,
        gesturesEnabled = false
    ) {
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            navController = navController
        ){
            composable(HomeScreenDestination){
                HomeScreen(
                    openDrawer = { scope.launch { drawerState.open() } },
                    navigator = destinationsNavigator,
                    sharedViewModel = sharedViewModel
                )
            }
            composable(ReadScreenDestination){
                ReadScreen(
                    navigator = destinationsNavigator,
                    sharedViewModel = sharedViewModel
                )
            }
            composable(SettingsScreenDestination){
                SettingsScreen(
                    navigator = destinationsNavigator
                )
            }
        }
    }
}