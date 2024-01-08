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
import com.ramcosta.composedestinations.navigation.dependency
import com.syntxr.anohikari2.presentation.NavGraphs


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
                    val sharedViewModel: AnoHikariSharedViewModel =
                        viewModel(LocalContext.current as ComponentActivity)

                    Scaffold {
                        DestinationsNavHost(
                            navGraph = NavGraphs.root,
                            dependenciesContainerBuilder = {
                                dependency(viewModel<AnoHikariSharedViewModel>(LocalContext.current as ComponentActivity))
                            },
                            modifier = Modifier.padding(it),
                        )
                    }
                }
            }
        }
    }
}
