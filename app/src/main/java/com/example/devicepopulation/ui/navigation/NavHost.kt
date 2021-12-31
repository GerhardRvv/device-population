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
import com.example.devicepopulation.viewmodel.MainActivityViewModel

import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.example.devicepopulation.ui.home.list.DeviceListView
import com.example.devicepopulation.ui.main.DeviceDetailsScreen

@Composable
fun DevicePopulationNavHost(
    viewModel: MainActivityViewModel,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
//    upPress: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = DeviceList.name,
        modifier = modifier
    ) {
        composable(route= DeviceList.name) { from ->
            DeviceListView(
                viewModel = viewModel,
                onDeviceClick = { device: Long ->
                    navigateToDeviceDetails(navController = navController, deviceId = device)
                },
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
                viewModel = viewModel,
                deviceId = backStackEntry.arguments?.getLong("deviceId"),
//                upPress = upPress
            )
        }
    }
}

private fun navigateToDeviceDetails(navController: NavHostController, deviceId: Long) {
    navController.navigate("${Details.name}/$deviceId")
}