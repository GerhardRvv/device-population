package com.example.devicepopulation.ui.devices

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.devicepopulation.R
import com.example.devicepopulation.data.models.DeviceDetailsModel
import com.example.devicepopulation.data.models.DeviceModel
import com.example.devicepopulation.ui.components.DeviceAppDrawer
import com.example.devicepopulation.ui.components.DeviceCardComponent
import com.example.devicepopulation.ui.components.DevicePopulationDivider
import com.example.devicepopulation.ui.components.DevicePopulationScaffold
import com.example.devicepopulation.ui.components.DevicePopulationSurface
import com.example.devicepopulation.ui.components.NoResults
import com.example.devicepopulation.ui.components.Placeholder
import com.example.devicepopulation.ui.components.SearchBar
import com.example.devicepopulation.ui.components.SearchDisplay
import com.example.devicepopulation.ui.components.SearchState
import com.example.devicepopulation.ui.components.rememberSearchState
import com.example.devicepopulation.ui.theme.DeviceAppTheme
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun DeviceListView(
    viewModel: DeviceListViewModel,
    onDeviceClick: (Long) -> Unit
) {
    val isLoading = remember { viewModel.loading }
    DeviceListView(
        viewModel = viewModel,
        onDeviceClick = onDeviceClick,
        isLoading = isLoading.value
    )
}
/**
 *  Device List Body
 */
@Composable
private fun DeviceListView(
    viewModel: DeviceListViewModel,
    onDeviceClick: (Long) -> Unit = {},
    isLoading: Boolean,
    state: SearchState = rememberSearchState()
) {

    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val devices by remember { mutableStateOf(viewModel.devices) }
    val searchResults by remember { mutableStateOf(viewModel.devicesSearch) }

    DevicePopulationScaffold(
        modifier = Modifier.statusBarsPadding(),
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = DeviceAppTheme.colors.uiFloated,
                contentColor = DeviceAppTheme.colors.textPrimary,
                elevation = 4.dp
            ) {
                Icon(
                    modifier = Modifier
                        .padding(top = 2.dp)
                        .clickable(onClick = { scope.launch { scaffoldState.drawerState.open() } }),
                    imageVector = Icons.Outlined.Menu,
                    tint = DeviceAppTheme.colors.textPrimary,
                    contentDescription = stringResource(id = R.string.drawer_option_button),
                )
                SearchBar(
                    query = state.query,
                    onQueryChange = { state.query = it },
                    searchFocused = state.focused,
                    onSearchFocusChange = { state.focused = it },
                    onClearQuery = { state.query = TextFieldValue("") },
                    searching = state.searching
                )
                DevicePopulationDivider()
            }
        },
        drawerContent = {
            DeviceAppDrawer(
                scaffoldState = scaffoldState,
                scope = scope,
                onDrawerOptionClicked = { option ->
                    Toast.makeText(context, "Option Selected $option", Toast.LENGTH_SHORT).show()
                }
            )
        },
        drawerGesturesEnabled = true
    ) {
        var refreshing = isLoading
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isLoading),
            onRefresh = {
                refreshing = true
                viewModel.fetchDevices(true)
            },
            indicator = { state, refreshTrigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = refreshTrigger,
                    backgroundColor = DeviceAppTheme.colors.iconInteractive,
                    contentColor = DeviceAppTheme.colors.textHelp
                )
            }
        ) {
            if (isLoading) {
                Column(
                    modifier = Modifier
                        .padding(top = 2.dp, start = 4.dp, end = 4.dp, bottom = 64.dp)
                ) {
                    LazyColumn {
                        items(30) {
                            Placeholder(
                                painter = rememberImagePainter(R.drawable.ic_launcher_foreground),
                                childModifier = Modifier.placeholder(
                                    visible = refreshing,
                                    color = DeviceAppTheme.colors.uiBorder,
                                    highlight = PlaceholderHighlight.shimmer(
                                        highlightColor = DeviceAppTheme.colors.uiBackground
                                    )
                                )
                            )
                        }
                    }
                }
            } else {
                if (state.query.text.isNotEmpty()) {
                    state.searching = true
                    viewModel.fetchDevicesByName(state.query.text)
                    state.searchResults = searchResults.value
                    state.searching = false
                } else {
                    state.searchResults = devices.value
                }
                when (state.searchDisplay) {
                    SearchDisplay.Results -> DeviceCollectionList(
                        state.searchResults,
                        onDeviceClick
                    )
                    SearchDisplay.NoResults -> NoResults(state.query.text)
                }
            }
        }
    }
}

@Composable
private fun DeviceCollectionList(
    devices: List<DeviceModel>,
    onDeviceClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    DevicePopulationSurface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(top = 2.dp, start = 4.dp, end = 4.dp, bottom = 64.dp)
        ) {
            LazyColumn {
                itemsIndexed(devices) { _, device ->
                    DeviceCardComponent(device, onDeviceClick)
                }
            }
        }
    }
}

@Preview
@Composable
private fun DeviceListViewPreview() {
    val detailsModel = listOf(
        DeviceDetailsModel(
            name = "name",
            current_price = "34",
            device_image_url = "https://dummyimage.com/200x200/000/fff.jpg",
            description = "Description with a long text",
            rating = 2
        )
    )
    val device = DeviceModel(
        id = 1,
        type = "Type",
        name = "Name",
        status = "status",
        image = "https://dummyimage.com/100x100/000/fff.jpg",
        is_favourite = true,
        details = detailsModel
    )
    val collection = listOf(device)
    DeviceCollectionList(
        devices = collection,
        onDeviceClick = {}
    )
}
