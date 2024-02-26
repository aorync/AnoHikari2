package com.syntxr.anohikari2.presentation.adzan

import android.Manifest
import android.location.Geocoder
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.syntxr.anohikari2.R
import com.syntxr.anohikari2.presentation.adzan.component.AdzanCard
import com.syntxr.anohikari2.presentation.adzan.component.AdzanLocationCard
import java.util.Locale

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Destination
@Composable
fun AdzanScreen(
    navigator: DestinationsNavigator,
    viewModel: AdzanViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    val locationPermission = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    if (!locationPermission.allPermissionsGranted) {
        LaunchedEffect(key1 = true) {
            locationPermission.launchMultiplePermissionRequest()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.txt_adzan_title),
                    fontWeight = FontWeight.Medium
                )
            }, navigationIcon = {
                IconButton(onClick = { navigator.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBackIosNew, contentDescription = "Back"
                    )
                }
            })
        }) { paddingValue ->

        Column(
            modifier = Modifier
                .padding(paddingValue)
                .fillMaxSize(),
            content = {

                Spacer(modifier = Modifier.height(16.dp))

                viewModel.currentLocation.collectAsState().let {
                    val geocoder = Geocoder(
                        context,
                        Locale.getDefault()
                    )
                    @Suppress("DEPRECATION") val addresses =
                        geocoder.getFromLocation(it.value.latitude, it.value.longitude, 1)
                    if (!addresses.isNullOrEmpty()) {
                        val address = addresses.first()
                        val locality = address.locality
                        val currentLocation =
                            "${address.locality}, ${address.subLocality}, ${address.subAdminArea}, ${address.countryName}"
                        AdzanLocationCard(locality = locality, currentLocation = currentLocation)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                viewModel.uiState.collectAsState(initial = null).let { uiState ->
                    when (uiState.value) {
                        is AdzanUIState.Error -> {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                content = {
                                    Text(
                                        text = when ((uiState.value as AdzanUIState.Error).errorType) {
                                            ErrorType.NO_GPS -> stringResource(R.string.txt_err_no_gps)
                                            ErrorType.PERMISSION_ERROR -> stringResource(R.string.txt_err_no_permission)
                                            ErrorType.ELSE -> stringResource(R.string.txt_err_else)
                                        },
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Button(onClick = { viewModel.getUpdateLocation() }) {
                                        Text(text = "Retry?")
                                    }
                                }
                            )
                        }

                        AdzanUIState.Success -> {
                            val state = viewModel.state.collectAsState()
                            val shalatTime = state.value.adzans.first()

                            AdzanCard(shalatName = "Shubuh", shalatTime = shalatTime.fajr)
                            Spacer(modifier = Modifier.height(8.dp))

                            AdzanCard(shalatName = "Dhuhur", shalatTime = shalatTime.dhuhr)
                            Spacer(modifier = Modifier.height(8.dp))

                            AdzanCard(shalatName = "Ashar", shalatTime = shalatTime.asr)
                            Spacer(modifier = Modifier.height(8.dp))

                            AdzanCard(shalatName = "Maghrib", shalatTime = shalatTime.maghrib)
                            Spacer(modifier = Modifier.height(8.dp))

                            AdzanCard(shalatName = "Isha", shalatTime = shalatTime.isha)
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        null -> {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                IconButton(onClick = { viewModel.getUpdateLocation() }) {
                                    Icon(
                                        imageVector =Icons.Rounded.LocationOn,
                                        contentDescription = "get_location_icon",
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                                Spacer(modifier =Modifier.height(8.dp))
                                Text(
                                    text = stringResource(id = R.string.txt_msg_location_to_usr),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }
                    }
                }

            }
        )
    }
}