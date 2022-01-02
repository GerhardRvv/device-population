package com.example.devicepopulation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.example.devicepopulation.ui.navigation.DevicePopulationNavHost
import com.example.devicepopulation.ui.devices.DeviceListViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.example.devicepopulation.ui.devicedetails.DeviceDetailsViewModel
import com.example.devicepopulation.ui.theme.DeviceAppTheme
import com.google.accompanist.insets.ProvideWindowInsets


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val deviceListViewModel by viewModels<DeviceListViewModel>()
    private val deviceDetailsViewModel by viewModels<DeviceDetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deviceListViewModel.fetchDevices(false)
        updateUI()
    }

    private fun updateUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProvideWindowInsets {
                DeviceAppTheme {
                    DevicePopulationNavHost(
                        deviceListViewModel = deviceListViewModel,
                        deviceDetailsViewModel = deviceDetailsViewModel
                    )
                }
            }
        }
    }
}
