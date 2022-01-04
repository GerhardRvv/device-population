package com.example.devicepopulation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType.Companion.LongType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.devicepopulation.ui.devicedetails.DeviceDetailsScreen
import com.example.devicepopulation.ui.devicedetails.DeviceDetailsViewModel
import com.example.devicepopulation.ui.devices.DeviceListView
import com.example.devicepopulation.ui.devices.DeviceListViewModel
import com.example.devicepopulation.ui.navigation.AppScreens.Details
import com.example.devicepopulation.ui.navigation.AppScreens.DeviceList

private const val DEVICE_ID_KEY = "deviceId"

@Composable
fun DevicePopulationNavHost(
    navController: NavHostController = rememberNavController(),
    deviceListViewModel: DeviceListViewModel,
    deviceDetailsViewModel: DeviceDetailsViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DeviceList.name,
        modifier = modifier
    ) {
        composable(route = DeviceList.name) {
            DeviceListView(
                viewModel = deviceListViewModel,
                onDeviceClick = { device: Long ->
                    navigateToDeviceDetails(navController = navController, deviceId = device)
                }
            )
        }
        composable(
            route = "${Details.name}/{" + DEVICE_ID_KEY + "}",
            arguments = listOf(
                navArgument(DEVICE_ID_KEY) {
                    type = LongType
                }
            )
        ) { backStackEntry ->
            DeviceDetailsScreen(
                viewModel = deviceDetailsViewModel,
                deviceId = backStackEntry.arguments?.getLong(DEVICE_ID_KEY),
                upPress = { navController.popBackStack() }
            )
        }
    }
}

private fun navigateToDeviceDetails(navController: NavHostController, deviceId: Long) {
    navController.navigate("${Details.name}/$deviceId")
}
