package com.example.devicepopulation.ui.devices

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.devicepopulation.R
import com.example.devicepopulation.data.models.DeviceDetailsModel

import com.example.devicepopulation.data.models.DeviceModel
import com.example.devicepopulation.ui.components.*
import com.example.devicepopulation.ui.theme.DeviceAppTheme
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import kotlinx.coroutines.launch

@Composable
fun DeviceListView(
    viewModel: DeviceListViewModel,
    onDeviceClick: (Long) -> Unit
){
    val isLoading = remember{viewModel.loading}

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

    val devices by remember {mutableStateOf(viewModel.devices)}
    val searchResults by remember {mutableStateOf(viewModel.devicesSearch)}

    DevicePopulationScaffold(
        modifier = Modifier.statusBarsPadding(),
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = DeviceAppTheme.colors.uiBackground,
                contentColor = DeviceAppTheme.colors.textPrimary,
                elevation = 4.dp
            ) {
                Icon(
                    modifier = Modifier
                        .padding(top = 2.dp)
                        .clickable(onClick = { scope.launch { scaffoldState.drawerState.open() } }),
                    imageVector = Icons.Outlined.Menu,
                    tint = DeviceAppTheme.colors.textHelp,
                    contentDescription = stringResource(id = R.string.cd_menu),
                )
                SearchBar(
                    query = state.query,
                    onQueryChange = {state.query = it},
                    searchFocused = state.focused,
                    onSearchFocusChange = {state.focused = it},
                    onClearQuery = {state.query = TextFieldValue("") },
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
            indicator = {state, refreshTrigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = refreshTrigger,
                    backgroundColor = DeviceAppTheme.colors.iconInteractive,
                    contentColor = DeviceAppTheme.colors.textHelp )
            }
        )
        {
            if (isLoading) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .semantics { contentDescription = "Device Screen List" }
                ) {
                    LazyColumn {
                        items(30) {
                            Placeholder(
                                painter = rememberImagePainter(R.drawable.ic_launcher_foreground),
                                // We're using the modifier provided by placeholder-material which
                                // uses good default values for the color
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
                SearchDisplay.Results ->  DeviceCollectionList(
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
){
    DevicePopulationSurface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .semantics { contentDescription = "Device Screen List" }
        ) {
            LazyColumn {
                itemsIndexed(devices) { index, device ->
                    DeviceCardComponent(device, onDeviceClick, index != 0)
                }
            }
        }
    }
}

@Preview
@Composable
private fun DeviceListViewPreview(){
    val detailsModel = listOf(
        DeviceDetailsModel(
            name = "name",
            current_price = "34",
            device_image_url = "https://dummyimage.com/200x200/000/fff.jpg",
            is_favourite = true,
            description = "Description with a long text"
        )
    )
    val device = DeviceModel(
        id = 1,
        type = "Type",
        name = "Name",
        status = "status",
        image = "https://dummyimage.com/100x100/000/fff.jpg",
        details = detailsModel
    )
    val collection = listOf(device)
    DeviceCollectionList(
        devices = collection,
        onDeviceClick = {}
    )
}
