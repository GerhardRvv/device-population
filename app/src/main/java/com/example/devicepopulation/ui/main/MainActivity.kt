package com.example.devicepopulation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.devicepopulation.ui.components.DevicePopulationScaffold
import com.example.devicepopulation.ui.components.Placeholder
import com.example.devicepopulation.ui.navigation.AppScreens
import com.example.devicepopulation.ui.navigation.DevicePopulationNavHost
import com.example.devicepopulation.viewmodel.MainActivityViewModel
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainActivityViewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityViewModel.fetchDevices(false)
        updateUI()
    }

    private fun updateUI() {
        setContent {
            DevicePopulationApp(
                viewModel = mainActivityViewModel
            )
        }
    }

    @Composable
    fun DevicePopulationApp(
        viewModel: MainActivityViewModel
    ) {
        val allScreens = AppScreens.values().toList()
        val navController = rememberNavController()
        val backStackEntry = navController.currentBackStackEntryAsState()
        val currentScreen = AppScreens.fromRoute(backStackEntry.value?.destination?.route)

        DevicePopulationScaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = MaterialTheme.colors.surface,
                    contentColor = MaterialTheme.colors.background,
                    elevation = 4.dp
                ) {
                    Text(
                        text = currentScreen.name,
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.secondary,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f)
                            .align(CenterVertically)
                    )
                }
            }
        )
        { innerPadding ->
            var refreshing by remember{mainActivityViewModel.loading}
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = refreshing),
                onRefresh = {
                    refreshing = true
                    mainActivityViewModel.fetchDevices(true)
                },
            )
            {
                if (refreshing) {
                    LazyColumn {
                        items(30) { index ->
                            Placeholder(
                                painter = rememberImagePainter("https://dummyimage.com/200x200/000/fff.jpg"),
                                // We're using the modifier provided by placeholder-material which
                                // uses good default values for the color
                                childModifier = Modifier.placeholder(
                                    visible = refreshing,
                                    color = MaterialTheme.colors.secondary,
                                    highlight = PlaceholderHighlight.shimmer(highlightColor = MaterialTheme.colors.primary)
                                )
                            )
                        }
                    }
                } else {
                    DevicePopulationNavHost(
                        viewModel = viewModel,
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
//                        upPress = navController.navigateUp()
                    )
                }
            }
        }
    }
}
