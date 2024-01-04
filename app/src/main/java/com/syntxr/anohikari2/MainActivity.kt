package com.syntxr.anohikari2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.syntxr.anohikari2.presentation.NavGraphs
import com.syntxr.anohikari2.presentation.destinations.HomeScreenDestination
import com.syntxr.anohikari2.presentation.destinations.ReadScreenDestination
import com.syntxr.anohikari2.presentation.home.HomeScreen
import com.syntxr.anohikari2.presentation.read.ReadScreen


import com.syntxr.anohikari2.ui.theme.AnoHikariTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            AnoHikariTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val sharedViewModel : AnoHikariSharedViewModel = viewModel(LocalContext.current as ComponentActivity)

                    Scaffold {
                        DestinationsNavHost(
                            navGraph = NavGraphs.root,
                            modifier = Modifier.padding(it),
                        ){
                            composable(HomeScreenDestination){
                                HomeScreen(
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
                        }
                    }
                }
            }
        }
    }
}
