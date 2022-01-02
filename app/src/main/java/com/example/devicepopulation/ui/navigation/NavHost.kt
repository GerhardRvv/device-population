package com.example.devicepopulation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType.Companion.LongType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.devicepopulation.ui.navigation.AppScreens.DeviceList
import com.example.devicepopulation.ui.navigation.AppScreens.Details
import com.example.devicepopulation.ui.devices.DeviceListViewModel

import androidx.navigation.compose.rememberNavController
import com.example.devicepopulation.ui.devicedetails.DeviceDetailsViewModel
import com.example.devicepopulation.ui.devices.DeviceListView
import com.example.devicepopulation.ui.devicedetails.DeviceDetailsScreen

@Composable
fun DevicePopulationNavHost(
    deviceListViewModel: DeviceListViewModel,
    deviceDetailsViewModel: DeviceDetailsViewModel,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = DeviceList.name,
        modifier = modifier
    ) {
        composable(route= DeviceList.name) {
            DeviceListView(
                viewModel = deviceListViewModel,
                onDeviceClick = { device: Long ->
                    navigateToDeviceDetails(navController = navController, deviceId = device)
                }
            )
        }
        composable(
            route= "${Details.name}/{deviceId}",
            arguments = listOf(
                navArgument("deviceId") {
                    type = LongType
                }
            )
        ) { backStackEntry ->
            DeviceDetailsScreen(
                viewModel = deviceDetailsViewModel,
                deviceId = backStackEntry.arguments?.getLong("deviceId"),
                upPress = {navController.popBackStack()}
            )
        }
    }
}

private fun navigateToDeviceDetails(navController: NavHostController, deviceId: Long) {
    navController.navigate("${Details.name}/$deviceId")
}
